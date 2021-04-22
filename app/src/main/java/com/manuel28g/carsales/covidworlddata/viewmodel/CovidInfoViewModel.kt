package com.manuel28g.carsales.covidworlddata.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.manuel28g.carsales.covidworlddata.helpers.RetrofitHelper
import com.manuel28g.carsales.covidworlddata.model.CovidInfo
import com.manuel28g.carsales.covidworlddata.model.RequestBody
import com.manuel28g.carsales.covidworlddata.repository.CovidData
import com.manuel28g.carsales.covidworlddata.repository.CovidDataImpl
import com.manuel28g.carsales.covidworlddata.repository.api.CovidDataAPI

import kotlinx.coroutines.launch

import java.text.SimpleDateFormat
import java.util.*

class CovidInfoViewModel: ViewModel(), CovidInfoCallback{
    private var api : CovidDataAPI = RetrofitHelper().getInstance()
    private var repository:CovidData = CovidDataImpl(api,this)
    private var formatter = SimpleDateFormat("dd-MM-yyyy")
    private var dateConsulted: MutableLiveData<String> = MutableLiveData()
    private var confirmedCases: MutableLiveData<Long> = MutableLiveData()
    private var deathPeople: MutableLiveData<Long> = MutableLiveData()
    private var isApiResponse: MutableLiveData<Boolean> = MutableLiveData()


    fun getData(date: Date){
        val body = RequestBody(formatter.format(date))
        getData(body)
    }

    fun getActualDate(){
        val body = RequestBody(formatter.format(Date()))
        getData(body)
    }

    fun isApiResponse():LiveData<Boolean>{
        return isApiResponse
    }

    fun getDate():LiveData<String>{
        return dateConsulted
    }

    fun getConfirmedCases():LiveData<Long>{
        return confirmedCases
    }

    fun getTotalDeaths():LiveData<Long>{
        return deathPeople
    }

    private fun getData(body: RequestBody){
        viewModelScope.launch {
            repository.getData(body)
        }
    }

    override fun showData(result: Result<CovidInfo?>) {
        if(result.isSuccess){
            val response :CovidInfo? = result.getOrNull()
            dateConsulted.postValue(response?.data?.date)
            confirmedCases.postValue(response?.data?.confirmed)
            deathPeople.postValue(response?.data?.deaths)
            isApiResponse.postValue(true)
        }
        else{
            //TODO show message
        }
    }

}