package com.manuel28g.carsales.covidworlddata.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.manuel28g.carsales.covidworlddata.model.CovidInfo
import com.manuel28g.carsales.covidworlddata.model.CovidInfoStatus
import com.manuel28g.carsales.covidworlddata.repository.CovidData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

import java.text.SimpleDateFormat

import java.util.*

@RunWith(JUnit4::class)
class CovidInfoViewModelTest {

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var viewModel:CovidInfoViewModel
    private val mDayInMillis = 1000*60*60*24L

    @Mock
    lateinit var repository: CovidData

    private fun getBaseResponse():CovidInfo{
        return CovidInfo(
            CovidInfoStatus(
                date = "2021-03-18",
                lastUpdate = "2021-03-17",
                confirmed = 1L,
                confirmedDiff = 2L,
                deaths = 3L,
                deathsDiff = 4L,
                recovered = 5L,
                recoveredDiff = 6L,
                active = 7L,
                activeDiff = 8L,
                fatalityRate = 9.0
            )
        )
    }

    @Before
    fun setup(){
        repository = mock()

        Dispatchers.setMain(TestCoroutineDispatcher())

        whenever(repository.getCurrentData()).thenReturn(flow { emit(getBaseResponse()) })
        whenever(repository.getData("2021-03-18")).thenReturn(flow{emit(getBaseResponse())})

        viewModel = CovidInfoViewModel(repository,Dispatchers.Main)

    }

    /**
     * Method to Validate if the Max range date is today
     */
    @Test
    fun getMaxDate() = runBlockingTest {
        assertTrue(
            Date().after(
                Date(viewModel.getMaxDate())
            )
        )

        assertEquals(Date().time - viewModel.getMaxDate(), mDayInMillis)
    }

    /**
     * Test if the minor date is today at May 2020 without hours
     */
    @Test
    fun getMinDate(){
        var actualDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        var minDate =  SimpleDateFormat("dd-MM-yyyy").parse("$actualDay-03-2020")
        assertTrue(minDate.time - viewModel.getMinDate() < mDayInMillis)
    }

    /**
     * Test the min date range is major than previous day to actual at May 2020
     */
    @Test
    fun getMinDateMajorThanYesterday() {
        var actualDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1
        var minDate =  SimpleDateFormat("dd-MM-yyyy").parse("$actualDay-03-2020")
        assertTrue(viewModel.getMinDate() > minDate.time)
    }

    /**
     * Test the min date range is minor than post day to actual day at May 2020
     */
    @Test
    fun getMinDateMinorThanTomorrow(){
        var actualDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1
        actualDay = actualDay.plus(1)
        var minDate = SimpleDateFormat("dd-MM-yyyy").parse("$actualDay-03-2020")
        assertFalse(viewModel.getMinDate() > minDate.time)
    }

    /**
     * Validate if the LiveData variables are setter after call getActualDate
     */
    @Test
    fun getActualDate() = runBlockingTest {
        viewModel.getActualDate()
        assertEquals(1L,viewModel.getConfirmedCases().value)
        assertEquals(3L,viewModel.getTotalDeaths().value)
        assertEquals(18,viewModel.getDay().value)
        assertEquals("March",viewModel.getMonth().value)
        assertEquals(2021,viewModel.getYear().value)
    }

    /**
     * Validate if LiveData isApiResponse is setter after calling api Data
     */
    @Test
    fun isApiResponse() = runBlockingTest {
        assertNull(viewModel.isApiResponse().value)
        viewModel.getActualDate()
        assertNotNull(viewModel.isApiResponse().value)
        assertTrue(viewModel.isApiResponse().value!!)
    }

    /**
     * Test if the getData with a correct format set all LiveData when return data
     */
    @Test
    fun getData()= runBlockingTest {
        viewModel.getData("2021-03-18")
        assertEquals(1L,viewModel.getConfirmedCases().value)
        assertEquals(3L,viewModel.getTotalDeaths().value)
        assertEquals(18,viewModel.getDay().value)
        assertEquals("March",viewModel.getMonth().value)
        assertEquals(2021,viewModel.getYear().value)
    }
}