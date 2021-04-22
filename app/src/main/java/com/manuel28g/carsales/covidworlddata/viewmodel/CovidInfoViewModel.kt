package com.manuel28g.carsales.covidworlddata.viewmodel

import android.app.Application
import androidx.lifecycle.*

import com.manuel28g.carsales.covidworlddata.helpers.RetrofitHelper
import com.manuel28g.carsales.covidworlddata.model.CovidInfo
import com.manuel28g.carsales.covidworlddata.repository.CovidData
import com.manuel28g.carsales.covidworlddata.repository.CovidDataImpl
import com.manuel28g.carsales.covidworlddata.repository.api.CovidDataAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

import kotlinx.coroutines.launch

import java.text.SimpleDateFormat
import java.util.*

class CovidInfoViewModel: ViewModel() {
    private var mApi : CovidDataAPI = RetrofitHelper().getInstance()
    private var mRepository:CovidData = CovidDataImpl(mApi)
    private var mFormatter = SimpleDateFormat("yyyy-MM-dd")
    private var monthNameFormat = SimpleDateFormat("MMMM")
    private var mDayConsulted: MutableLiveData<Int> = MutableLiveData()
    private var mMonthConsulted: MutableLiveData<String> = MutableLiveData()
    private var mYearConsulted: MutableLiveData<Int> = MutableLiveData()
    private var mConfirmedCases: MutableLiveData<Long> = MutableLiveData()
    private var mDeathPeople: MutableLiveData<Long> = MutableLiveData()
    private var mIsApiResponse: MutableLiveData<Boolean> = MutableLiveData()
    private var mMinDate:Long? = null
    private var mMaxDate:Long? = null
    private var mError = MutableLiveData<Boolean>()


    fun getMaxDate():Long{
        if(mMaxDate == null) {
            var previousDay: Calendar = Calendar.getInstance().clone() as Calendar
            previousDay.add(Calendar.DAY_OF_MONTH, -1)
            mMaxDate = previousDay.timeInMillis
        }
        return mMaxDate!!
    }

    fun getMinDate():Long{
        if(mMinDate == null) {
            var minDate: Calendar = Calendar.getInstance().clone() as Calendar
            minDate.add(Calendar.DAY_OF_MONTH, -1)
            minDate.set(Calendar.MONTH, 2)
            minDate.set(Calendar.YEAR, 2020)
            mMinDate = minDate.timeInMillis
        }
        return mMinDate!!
    }

    fun getActualDate(){
        mIsApiResponse.value = false
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.getCurrentData().map {
                mapData(it)
            }.catch {
                mError.postValue(true)
            }.collect()

        }
    }

    private fun mapData(info: CovidInfo?){
        val date:Calendar = Calendar.getInstance().clone() as Calendar
        date.time = mFormatter.parse(info?.data?.date)
        viewModelScope.launch(Dispatchers.Main) {
            mDayConsulted.value =  date.get(Calendar.DAY_OF_MONTH)
            mYearConsulted.value = date.get(Calendar.YEAR)
            mMonthConsulted.value = monthNameFormat.format(date.time)
            mConfirmedCases.value = info?.data?.confirmed
            mDeathPeople.value = info?.data?.deaths
            mIsApiResponse.value = true
        }
    }

    fun isApiResponse():LiveData<Boolean>{
        return mIsApiResponse
    }

    fun resetError(){
        mError.value = false
    }

    fun getDay():LiveData<Int>{
        return mDayConsulted
    }

    fun getMonth():LiveData<String>{
        return mMonthConsulted
    }

    fun getYear():LiveData<Int>{
        return mYearConsulted
    }

    fun getConfirmedCases():LiveData<Long>{
        return mConfirmedCases
    }

    fun getTotalDeaths():LiveData<Long>{
        return mDeathPeople
    }

    fun andErrorOccurs():LiveData<Boolean>{
        return mError
    }

    fun getData(body: String){
        mIsApiResponse.value = false
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.getData(body).map {
                mapData(it)
            }.catch {
                mError.postValue(true)
            }.collect()
        }
    }

}