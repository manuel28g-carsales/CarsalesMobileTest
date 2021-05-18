package com.manuel28g.carsales.covidworlddata.viewmodel

import com.manuel28g.carsales.covidworlddata.model.CovidInfo
import com.manuel28g.carsales.covidworlddata.model.CovidInfoStatus
import com.manuel28g.carsales.covidworlddata.repository.CovidData

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

import java.text.SimpleDateFormat

import java.util.*

class CovidInfoViewModelTest {
    private lateinit var viewModel:CovidInfoViewModel
    @Mock
    lateinit var repository: CovidData

    @Before
    fun setup(){
       repository = mock()
        whenever(repository.getCurrentData()).thenReturn(flow {
            emit(
                CovidInfo(
                    CovidInfoStatus(
                        date = "String",
                        lastUpdate = "String",
                        confirmed = 0L,
                        confirmedDiff = 0L,
                        deaths = 0L,
                        deathsDiff = 0L,
                        recovered = 0L,
                        recoveredDiff = 0L,
                        active = 0L,
                        activeDiff = 0L,
                        fatalityRate = 0.0
                    )
                )
            )
        })
        viewModel = CovidInfoViewModel(repository)

    }

    @Test
    fun getMaxDate() = runBlocking {
        assertTrue(
            Date().after(
                Date(viewModel.getMinDate())
            )
        )
    }

    @Test
    fun getMinDate() {
        //Test the min date range is major than previous day to actual at May 2020
        var actualDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1
        var minDate =  SimpleDateFormat("dd-MM-yyyy").parse("$actualDay-03-2020")
        assertTrue(viewModel.getMinDate() > minDate.time)

        //Test the min date range is minor than two day to actual at May 2020
        actualDay = actualDay.plus(2)
        minDate = SimpleDateFormat("dd-MM-yyyy").parse("$actualDay-03-2020")
        assertFalse(viewModel.getMinDate() > minDate.time)
    }

    @Test
    fun getActualDate() {
        viewModel.getActualDate()
    }

    @Test
    fun isApiResponse() = runBlocking {
        assertFalse(viewModel.isApiResponse().value!!)
    }

    @Test
    fun getData() {
        viewModel.getData("")
    }
}