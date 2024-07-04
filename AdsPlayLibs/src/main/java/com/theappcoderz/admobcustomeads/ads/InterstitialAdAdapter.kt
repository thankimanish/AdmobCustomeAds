package com.theappcoderz.admobcustomeads.ads

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterstitialAdAdapter {
    var mInterstitialAd: InterstitialAd? = null
    private var mProgressDialog: DelayedProgressDialog? =
        DelayedProgressDialog()

    private fun loadInterAds(mContext: Context) {
        if (AdsConfiguration.display_data_enable == 0 || AdsConfiguration.display_ad_enable == 0) {
            return
        }
        if (AdsConfiguration.first_interestitial_enable == 0) {
            return
        }
        if (isLoaded) {
            return
        }
        when (AdsConfiguration.ad_priority) {
            1 -> {
                val builder = AdRequest.Builder()
                mContext.let {
                    InterstitialAd.load(it,
                        AdsConfiguration.first_interstitial_id,
                        builder.build(),
                        object : InterstitialAdLoadCallback() {
                            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                                // The mInterstitialAd reference will be null until
                                // an ad is loaded.
                                mInterstitialAd = interstitialAd
                            }

                            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                                // Handle the error
                                AdsConfiguration.isLoadFirstOpenOrInter = false
                                mInterstitialAd = null
                            }
                        })
                }
            }
        }
    }

    private val isLoaded: Boolean
        get() = mInterstitialAd != null

    fun showAds(
        activity: FragmentActivity,
        type: String,
        isSkipCounter: Boolean,
        interAdListener: InterAdListener
    ) {
        if (AdsConfiguration.display_data_enable == 0 || AdsConfiguration.display_ad_enable == 0) {
            callInter(activity, interAdListener, type)
            return
        }
        if (AdsConfiguration.first_interestitial_enable == 0) {
            callInter(activity, interAdListener, type)
            return
        }
        var adCounter = AdsConfiguration.prefs!!.getInt(AppConstant.ADCOUNTER, 0)
        adCounter += 1
        if (adCounter >= AdsConfiguration.interestitial_ad_counter) {
            adCounter = 0
        }
        if (isSkipCounter) {
            adCounter = 0
        }
        AdsConfiguration.prefs!!.setInt(AppConstant.ADCOUNTER, adCounter)
        if (AdsConfiguration.interestitial_ad_counter > 0 && adCounter % AdsConfiguration.interestitial_ad_counter == 0) {
            AdsConfiguration.alterintercounter++
            mProgressDialog!!.show(
                activity.supportFragmentManager, "tag"
            )
            if (mInterstitialAd != null) {
                mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {

                    override fun onAdClicked() {
                        // Called when a click is recorded for an ad.
                    }

                    override fun onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        AdsConfiguration.isLoadFirstOpenOrInter = true
                        mInterstitialAd = null
                        callInter(activity, interAdListener, type)
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        // Called when ad fails to show.
                        AdsConfiguration.isLoadFirstOpenOrInter = false
                        mInterstitialAd = null
                        callInter(activity, interAdListener, type)
                    }

                    override fun onAdImpression() {
                        // Called when an impression is recorded for an ad.
                    }

                    override fun onAdShowedFullScreenContent() {
                    }
                }
                mInterstitialAd!!.show(activity)
            } else {
                callInter(activity, interAdListener, type)
            }
        } else {
            callInter(activity, interAdListener, type)
        }
    }

    private fun callInter(
        context: Context,
        interAdListener: InterAdListener,
        type: String
    ) {
        if (mProgressDialog != null) {
            mProgressDialog!!.cancel()
        }
        loadInterAds(context)
        interAdListener.onAdClose(type)
    }

    fun loadAndShow(
        type: String,
        activity: Activity,
        isSkipCounter: Boolean,
        interAdListener: InterAdListener

    ) {
        if (AdsConfiguration.display_data_enable == 0 || AdsConfiguration.display_ad_enable == 0) {
            callInter(activity, interAdListener, type)
            return
        }
        if (AdsConfiguration.first_interestitial_enable == 0) {
            callInter(activity, interAdListener, type)
            return
        }
        when (AdsConfiguration.ad_priority) {
            1 -> {
                val builder = AdRequest.Builder()
                activity.let {
                    InterstitialAd.load(it,
                        AdsConfiguration.first_interstitial_id,
                        builder.build(),
                        object : InterstitialAdLoadCallback() {
                            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                                interstitialAd.fullScreenContentCallback =
                                    object : FullScreenContentCallback() {
                                        override fun onAdClicked() {
                                            // Called when a click is recorded for an ad.
                                        }

                                        override fun onAdDismissedFullScreenContent() {
                                            AdsConfiguration.isLoadFirstOpenOrInter = true
                                            callInter(activity, interAdListener, type)
                                        }

                                        override fun onAdFailedToShowFullScreenContent(
                                            adError: AdError
                                        ) {
                                            // Called when ad fails to show.
                                            callInter(activity, interAdListener, type)
                                        }

                                        override fun onAdImpression() {
                                            // Called when an impression is recorded for an ad.
                                        }

                                        override fun onAdShowedFullScreenContent() {
                                            // Called when ad is shown.
                                        }
                                    }
                                interstitialAd.show(activity)
                            }

                            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                                // Handle the error
                                AdsConfiguration.isLoadFirstOpenOrInter = false
                                callInter(activity, interAdListener, type)
                            }
                        })
                }
            }
        }
    }
}