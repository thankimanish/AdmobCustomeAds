package com.theappcoderz.admobcustomeads.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class APIClientAppInfo {

    companion object {
        private var retrofit: Retrofit? = null

        fun getClient(baseUrl: String): Retrofit {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
            return retrofit!!
        }
    }
}
