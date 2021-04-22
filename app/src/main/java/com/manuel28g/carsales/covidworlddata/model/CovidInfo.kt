package com.manuel28g.carsales.covidworlddata.model

import com.google.gson.annotations.SerializedName

data class CovidInfo (
        @SerializedName("data")
        var data: CovidInfoStatus
        )