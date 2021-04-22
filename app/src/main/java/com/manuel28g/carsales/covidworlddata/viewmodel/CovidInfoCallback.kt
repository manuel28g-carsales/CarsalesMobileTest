package com.manuel28g.carsales.covidworlddata.viewmodel

import com.manuel28g.carsales.covidworlddata.model.CovidInfo

interface CovidInfoCallback {
    fun showData(result: Result<CovidInfo?>)
}