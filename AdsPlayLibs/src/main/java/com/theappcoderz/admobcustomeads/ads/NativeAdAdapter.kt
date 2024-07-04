package com.theappcoderz.admobcustomeads.ads

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.nativead.NativeAd

class NativeAdAdapter(
    private val mcontext: Context,
    private val nativesmallAdLayout: TemplateView,
    val nativeAdListener: NativeAdListener
) {
    init {
        loadNativeAds()
    }

    private fun loadNativeAds() {
        when (AdsConfiguration.ad_priority) {
            1 -> {
                val adLoader = AdLoader.Builder(mcontext, AdsConfiguration.first_native_id)
                    .forNativeAd { ad: NativeAd ->
                        nativesmallAdLayout.setNativeAd(ad)
                        nativeAdListener.onAdShow()
                    }
                    .withAdListener(object : AdListener() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            nativeAdListener.onAdFailed()
                        }
                    })
                    .withNativeAdOptions(
                        com.google.android.gms.ads.nativead.NativeAdOptions.Builder()
                            .build()
                    )
                    .build()
                adLoader.loadAd(AdRequest.Builder().build())
            }
        }
    }
}