package com.manuel28g.carsales.covidworlddata.helpers

import com.manuel28g.carsales.covidworlddata.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BasicInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder: Request.Builder = chain.request().newBuilder()
        requestBuilder.addHeader("X-RapidApi-Key",BuildConfig.RAPID_API_KEY)
        return chain?.proceed(requestBuilder.build())
    }

}