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


    fun appInfoAdsData(methodname:String,cat_id:String,baseurl:String) {
        var apiServiceslink: ApiInterface = APIClientAppInfo.getClient(baseurl).create(ApiInterface::class.java)
        var version: String = ""
        try {
            val pInfo = con.packageManager.getPackageInfo(con.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        var call = if(cat_id.isEmpty()){
            apiServiceslink.getList(methodname, con.packageName, version)
        }else{
            apiServiceslink.getListByCategory(methodname, con.packageName, version, cat_id)
        }

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