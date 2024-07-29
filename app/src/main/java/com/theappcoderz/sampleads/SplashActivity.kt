package com.theappcoderz.sampleads

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.theappcoderz.admobcustomeads.AdsApplication
import com.theappcoderz.admobcustomeads.NetworkUtils
import com.theappcoderz.admobcustomeads.GetSmartAdmobConfiguration
import com.theappcoderz.admobcustomeads.ads.L
import com.theappcoderz.admobcustomeads.api.ApiCallAdsConfig
import com.theappcoderz.admobcustomeads.api.OnCallApiResponce
import com.theappcoderz.sampleads.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity(), OnCallApiResponce {
    private var binding: ActivitySplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate: ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        binding = inflate
        setContentView(inflate.root)

        when (NetworkUtils.getNetworkType(this)) {
            NetworkUtils.NetworkType.NONE -> {
                ConnectionDialog.showConnectionDialog(this) // 'this' refers to the current activity context
            }

            NetworkUtils.NetworkType.WIFI -> {
                callToApi()
            }

            NetworkUtils.NetworkType.CELLULAR -> {
                callToApi()
            }

            NetworkUtils.NetworkType.VPN -> {
                callToApi()
            }
        }
    }
    private fun callToApi(){
        ApiCallAdsConfig(this@SplashActivity, this@SplashActivity).appInfoAdsData("link_master","2","http://appmobiztech.in/linkmaster/")
    }

    override fun onDestroy() {
        super.onDestroy()
        L.e("")
        (application as AdsApplication).unloadAd(this)
    }

    override fun onResponseForSplash() {
        GetSmartAdmobConfiguration(
            this,
            AdsApplication.prefs,
            AdsApplication.packages,
            object : GetSmartAdmobConfiguration.SmartListener {
                override fun onFinish(success: Boolean) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(
                            Intent(
                                this@SplashActivity,
                                MainActivity::class.java
                            )
                        )
                        finish()
                    }, 0)
                }

                override fun onAdShowed() {
                }
            })
    }

    override fun onFailed() {
    }
}