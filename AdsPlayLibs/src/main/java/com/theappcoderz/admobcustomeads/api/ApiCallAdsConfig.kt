package com.theappcoderz.admobcustomeads.api

import android.content.Context
import android.content.pm.PackageManager
import com.theappcoderz.admobcustomeads.AdsApplication
import com.theappcoderz.admobcustomeads.ads.ApiUtils
import com.theappcoderz.admobcustomeads.ads.AppConstant
import com.theappcoderz.admobcustomeads.ads.L
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiCallAdsConfig(val apilistner: OnCallApiResponce, val con: Context) {



    fun appInfoAdsData() {
        var apiServices: ApiInterface = APIClientAppInfo.client.create(ApiInterface::class.java)
        var version: String = ""
        try {
            val pInfo = con.packageManager.getPackageInfo(con.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        var call = apiServices.getList("app_settings_adx", con.packageName, version)
        //println(call.request().url)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                //Toast.makeText()
                try {
                    println(response.body()!!.toString())
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val jsonresponse = response.body()!!.toString()
                            AdsApplication.prefs?.setString(AppConstant.JSONRESPONSE, jsonresponse)
                            dataParsing(jsonresponse)
                        } else {
                            dataParsing(
                                AdsApplication.prefs?.getString(AppConstant.JSONRESPONSE, "")
                                    .toString()
                            )
                        }
                    } else {
                        dataParsing(
                            AdsApplication.prefs?.getString(AppConstant.JSONRESPONSE, "").toString()
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    AdsApplication.prefs?.getString(AppConstant.JSONRESPONSE, "").toString()
                    apilistner.onFailed()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                apilistner.onFailed()
            }
        })
    }
    fun appInfoAdsLinksData() {
        var apiServiceslink: ApiInterface = APIClientAppInfo.clientlink.create(ApiInterface::class.java)
        var version: String = ""
        try {
            val pInfo = con.packageManager.getPackageInfo(con.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        var call = apiServiceslink.getListByCategory("link_master", con.packageName, version,AppConstant.CAT_ID)
        println(call.request().url)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                //Toast.makeText()
                try {
                    println(response.body()!!.toString())
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val jsonresponse = response.body()!!.toString()
                            AdsApplication.prefs?.setString(AppConstant.JSONRESPONSE, jsonresponse)
                            dataParsing(jsonresponse)
                        } else {
                            dataParsing(
                                AdsApplication.prefs?.getString(AppConstant.JSONRESPONSE, "")
                                    .toString()
                            )
                        }
                    } else {
                        dataParsing(
                            AdsApplication.prefs?.getString(AppConstant.JSONRESPONSE, "").toString()
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    AdsApplication.prefs?.getString(AppConstant.JSONRESPONSE, "").toString()
                    apilistner.onFailed()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                apilistner.onFailed()
            }
        })
    }
    fun dataParsing(jsonresponse: String) {
        try {
            val jo = JSONObject(jsonresponse)
            val js = ApiUtils.getpackagesorappinfo(jo)
            ApiUtils.getParsingAdsInformation(js)
            ApiUtils.getParsingAdsLink(jo)
            apilistner.onResponseForSplash()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            apilistner.onFailed()
        }
    }
}