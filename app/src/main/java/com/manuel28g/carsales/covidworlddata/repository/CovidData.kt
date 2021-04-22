package com.manuel28g.carsales.covidworlddata.repository

import com.manuel28g.carsales.covidworlddata.model.CovidInfo
import com.manuel28g.carsales.covidworlddata.model.RequestBody
import kotlinx.coroutines.flow.Flow

interface CovidData {
    fun getData(body: String):Flow<CovidInfo?>
    fun getCurrentData():Flow<CovidInfo?>
}