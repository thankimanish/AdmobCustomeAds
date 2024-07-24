package com.theappcoderz.admobcustomeads.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


class APIClientAppInfo {

    companion object {
        private var retofit: Retrofit? = null
        val client: Retrofit
            get() {
                if (retofit == null) {
                    retofit = Retrofit.Builder()
                        .baseUrl("https://theappcoderz.com/appinfo/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build()
                }
                return retofit!!
            }
        val clientlink: Retrofit
            get() {
                if (retofit == null) {
                    retofit = Retrofit.Builder()
                        .baseUrl("http://appmobiztech.in/linkmaster/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build()
                }
                return retofit!!
            }
    }
}