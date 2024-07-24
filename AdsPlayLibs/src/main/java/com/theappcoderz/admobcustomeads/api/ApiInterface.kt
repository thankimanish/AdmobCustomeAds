package com.theappcoderz.admobcustomeads.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @GET("api.php?")
    fun getList(
        @Query("method_name") getFromApp: String,
        @Query("package_name") package_name: String,
        @Query("app_version") app_version: String
    ): Call<String>

    @GET("api.php?")
    fun getListByCategory(
        @Query("method_name") getFromApp: String,
        @Query("package_name") package_name: String,
        @Query("package_version") app_version: String,
        @Query("cat_id") cat_id: String
    ): Call<String>
}