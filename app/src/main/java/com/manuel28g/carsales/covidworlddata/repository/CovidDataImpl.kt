package com.manuel28g.carsales.covidworlddata.repository

import com.manuel28g.carsales.covidworlddata.model.CovidInfo
import com.manuel28g.carsales.covidworlddata.model.RequestBody
import com.manuel28g.carsales.covidworlddata.repository.api.CovidDataAPI
import com.manuel28g.carsales.covidworlddata.viewmodel.CovidInfoCallback

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CovidDataImpl(var api:CovidDataAPI,val callback: CovidInfoCallback):CovidData {

    override fun getData(body:RequestBody){
           GlobalScope.launch(Dispatchers.IO) {
               api.getData().enqueue(object : Callback<CovidInfo> {
                   override fun onResponse(call: Call<CovidInfo>, response: Response<CovidInfo>) {
                       callback.showData(Result.success(response.body()))
                   }

                   override fun onFailure(call: Call<CovidInfo>, t: Throwable) {
                       callback.showData(Result.failure(t))
                   }

               })
           }
       }
}