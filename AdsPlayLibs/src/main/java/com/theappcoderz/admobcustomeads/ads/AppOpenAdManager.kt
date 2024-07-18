package com.theappcoderz.admobcustomeads.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.appopen.AppOpenAd
import com.theappcoderz.admobcustomeads.AdsApplication
import java.util.Date

class AppOpenAdManager(instance: AdsApplication?) {
    private var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager? =
        instance?.let { GoogleMobileAdsConsentManager.getInstance(it) }
    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd = false
    var isShowingAd = false
    private var loadTime: Long = 0

    private fun getopenadid(): String {
        return if (AdsConfiguration.display_ad_enable == 1 && AdsConfiguration.first_opened_enable == 1) {
            AdsConfiguration.first_opened_id
        } else {
            ""
        }
    }

    fun loadAd(context: Context) {
        if (isLoadingAd || isAdAvailable() && AdsConfiguration.first_opened_enable == 0) {
            return
        }
        isLoadingAd = true
        val request = AdManagerAdRequest.Builder().build()
        Log.e("AppOpenAd:::", getopenadid())
        AppOpenAd.load(context,
            getopenadid(),
            request,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    isLoadingAd = false
                    loadTime = Date().time
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isLoadingAd = false
                }
            })
        // Do not load ad if there is an unused ad or one is already loading.
    }

    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference: Long = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < (numMilliSecondsPerHour * numHours)
    }

    private fun isAdAvailable(): Boolean {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)
    }

    fun showAdIfAvailable(activity: Activity) {
        showAdIfAvailable(activity, object : AdsApplication.OnShowAdCompleteListener {
            override fun onShowAdComplete() {
                //AdsApplication.myloadTime = Date().time
            }

            override fun onShowAdFailed() {
            }
        })
    }


    fun showAdIfAvailable(
        activity: Activity, onShowAdCompleteListener: AdsApplication.OnShowAdCompleteListener
    ) {
        if (isShowingAd) {
            return
        }
        if (!isAdAvailable()) {
            
            onShowAdCompleteListener.onShowAdFailed()
            if (googleMobileAdsConsentManager?.canRequestAds == true) {
                loadAd(activity)
            }
            return
        }

        appOpenAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                
                // Set the reference to null so isAdAvailable() returns false.
                appOpenAd = null
                isShowingAd = false

                onShowAdCompleteListener.onShowAdComplete()
                if (googleMobileAdsConsentManager?.canRequestAds == true) {
                    loadAd(activity)
                }
            }

            /** Called when fullscreen content failed to show. */
            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                
                appOpenAd = null
                isShowingAd = false

                onShowAdCompleteListener.onShowAdFailed()
                if (googleMobileAdsConsentManager?.canRequestAds == true) {
                    loadAd(activity)
                }

            }

            /** Called when fullscreen content is shown. */
            override fun onAdShowedFullScreenContent() {
                
                //broswerdontpause()
            }
        }
        isShowingAd = true
        appOpenAd!!.show(activity)
    }
}