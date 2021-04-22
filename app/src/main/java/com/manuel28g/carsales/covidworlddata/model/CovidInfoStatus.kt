package com.manuel28g.carsales.covidworlddata.model

import com.google.gson.annotations.SerializedName

data class CovidInfoStatus(
    @SerializedName("date")
    var date: String,
    @SerializedName("last_update")
    var lastUpdate: String,
    @SerializedName("confirmed")
    var confirmed:Long,
    @SerializedName("confirmed_diff")
    var confirmedDiff: Long,
    @SerializedName("deaths")
    var deaths:Long,
    @SerializedName("_diff")
    var deathsDiff:Long,
    @SerializedName("recovered")
    var recovered:Long,
    @SerializedName("recovered_diff")
    var recoveredDiff:Long,
    @SerializedName("active")
    var active:Long,
    @SerializedName("active_diff")
    var activeDiff:Long,
    @SerializedName("fatality_rate")
    var fatalityRate:Double
)