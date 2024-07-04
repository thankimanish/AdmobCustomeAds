package com.theappcoderz.admobcustomeads.ads

import android.app.Activity
import android.os.CountDownTimer
import android.widget.Toast
import com.google.android.gms.ads.MobileAds
import com.theappcoderz.admobcustomeads.AdsApplication
import com.theappcoderz.admobcustomeads.Prefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class GetSmartAdmobConfiguration(
    private val activity: Activity,
    private val prefs: Prefs?,
    private val adsId: Adp,
    private val listener: SmartListener
) {
    private lateinit var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)

    init {
        AdsConfiguration.getInstance(activity)
    }

    fun execute() {
        CoroutineScope(Dispatchers.Main).launch {
            onPreExecute()
            val result = withContext(Dispatchers.IO) {
                doInBackground()
            }
            onPostExecute(result)
        }
    }

    private fun onPreExecute() {
        // Pre-execution code if needed
    }

    private suspend fun doInBackground(): Boolean {
        return try {
            AdsConfiguration.prefs = prefs
            AdsConfiguration.ad_priority = adsId.ad_priority
            AdsConfiguration.package_name = adsId.package_name
            AdsConfiguration.monetize_id = adsId.monetize_id
            AdsConfiguration.secMonetizeId = adsId.secMonetizeId
            AdsConfiguration.display_data_enable = adsId.display_data_enable
            AdsConfiguration.display_ad_enable = adsId.display_ad_enable
            AdsConfiguration.alternet_ad_enable = adsId.alternet_ad_enable
            AdsConfiguration.secondory_ad_enable = adsId.secondory_ad_enable
            AdsConfiguration.interestitial_ad_counter = adsId.interestitial_ad_counter
            AdsConfiguration.video_ad_counter = adsId.video_ad_counter
            AdsConfiguration.force_update = adsId.force_update
            AdsConfiguration.app_version = adsId.app_version
            AdsConfiguration.first_banner_enable = adsId.first_banner_enable
            AdsConfiguration.first_banner_id = adsId.first_banner_id
            AdsConfiguration.first_interestitial_enable = adsId.first_interestitial_enable
            AdsConfiguration.first_interstitial_id = adsId.first_interstitial_id
            AdsConfiguration.first_interstitial_id1 = adsId.first_interstitial_id1
            AdsConfiguration.first_interstitial_id2 = adsId.first_interstitial_id2
            AdsConfiguration.first_video_enable = adsId.first_video_enable
            AdsConfiguration.first_video_id = adsId.first_video_id
            AdsConfiguration.sec_banner_enable = adsId.sec_banner_enable
            AdsConfiguration.sec_banner_id = adsId.sec_banner_id
            AdsConfiguration.sec_interestitial_enable = adsId.sec_interestitial_enable
            AdsConfiguration.sec_interstitial_id = adsId.sec_interstitial_id
            AdsConfiguration.sec_interstitial_id1 = adsId.sec_interstitial_id1
            AdsConfiguration.sec_interstitial_id2 = adsId.sec_interstitial_id2
            AdsConfiguration.sec_video_enable = adsId.sec_video_enable
            AdsConfiguration.sec_video_id = adsId.sec_video_id
            AdsConfiguration.on_swipe_count = adsId.on_swipe_count
            AdsConfiguration.on_native_count = adsId.on_native_count
            AdsConfiguration.first_native_enable = adsId.first_native_enable
            AdsConfiguration.first_native_id = adsId.first_native_id
            AdsConfiguration.first_native_id1 = adsId.first_native_id1
            AdsConfiguration.first_native_id2 = adsId.first_native_id2
            AdsConfiguration.first_native_id3 = adsId.first_native_id3
            AdsConfiguration.first_native_id4 = adsId.first_native_id4
            AdsConfiguration.sec_native_enable = adsId.sec_native_enable
            AdsConfiguration.sec_native_id = adsId.sec_native_id
            AdsConfiguration.sec_native_id1 = adsId.sec_native_id1
            AdsConfiguration.sec_native_id2 = adsId.sec_native_id2
            AdsConfiguration.sec_native_id3 = adsId.sec_native_id3
            AdsConfiguration.sec_native_id4 = adsId.sec_native_id4
            AdsConfiguration.first_opened_enable = adsId.first_opened_enable
            AdsConfiguration.first_opened_id = adsId.first_opened_id
            AdsConfiguration.first_opened_id1 = adsId.first_opened_id1
            AdsConfiguration.sec_opened_enable = adsId.sec_opened_enable
            AdsConfiguration.sec_opened_id = adsId.sec_opened_id
            AdsConfiguration.sec_opened_id1 = adsId.sec_opened_id1
            AdsConfiguration.privacy_policy_url = adsId.privacy_policy_url
            AdsConfiguration.terms_usage_url = adsId.terms_usage_url
            AdsConfiguration.copyright_policy_url = adsId.copyright_policy_url
            AdsConfiguration.cookies_policy = adsId.cookies_policy
            AdsConfiguration.pp_for_younger_user = adsId.pp_for_younger_user
            AdsConfiguration.community_guideline = adsId.community_guideline
            AdsConfiguration.law_enforcement = adsId.law_enforcement
            AdsConfiguration.server_base_url = adsId.server_base_url
            AdsConfiguration.custome_ads_enable = adsId.custome_ads_enable
            AdsConfiguration.custome_ads_title = adsId.custome_ads_title
            AdsConfiguration.custome_ads_description = adsId.custome_ads_description
            AdsConfiguration.custome_ads_image_url = adsId.custome_ads_image_url
            AdsConfiguration.custome_ads_install_url = adsId.custome_ads_install_url
            AdsConfiguration.install_type = adsId.install_type
            AdsConfiguration.openad_load_as_inter = adsId.openad_load_as_inter
            AdsConfiguration.manage_native = adsId.manage_native
            AdsConfiguration.manage_exit_ads = adsId.manage_exit_ads
            AdsConfiguration.getdomainlist = adsId.getdomainlist
            true
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            }
            false
        }
    }

    private fun onPostExecute(success: Boolean) {
        AdsApplication.appOpenAdManager = AppOpenAdManager(AdsApplication.getInstance())
        createTimer(AppConstant.COUNTER_TIME)
        googleMobileAdsConsentManager =
            GoogleMobileAdsConsentManager.getInstance(activity)
        googleMobileAdsConsentManager.gatherConsent(activity) {
            if (googleMobileAdsConsentManager.canRequestAds) {
                L.e("")
                initializeMobileAdsSdk()
            }
            if (secondsRemaining <= 0) {
                L.e("")
                listener.onFinish(success)
            }
        }
        if (googleMobileAdsConsentManager.canRequestAds) {
            L.e("")
            initializeMobileAdsSdk()
        }
        //listener.onFinish(success)
    }

    private var secondsRemaining: Long = 0L
    private fun createTimer(seconds: Long) {
        val countDownTimer: CountDownTimer = object : CountDownTimer(seconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1
            }

            override fun onFinish() {
                secondsRemaining = 0
                L.e("")
                adshowafterconsent()
            }
        }
        countDownTimer.start()
    }

    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return
        }
        // Load an ad.
        L.e("")
        (activity.application as AdsApplication).loadAd(activity)
    }

    fun adshowafterconsent() {
        L.e("")
        val application = activity.application as? AdsApplication
        // If the application is not an instance of MyApplication, log an error message and
        // start the MainActivity without showing the app open ad.
        if (application == null) {
            L.e("")
            listener.onFinish(true)
            return
        }

        if (AdsConfiguration.display_ad_enable == 1) {
            if (AdsConfiguration.openad_load_as_inter == 1) {
                AdsApplication.intInterstitialAdAdapter?.loadAndShow("splash",
                    activity,
                    true,
                    object : InterAdListener {
                        override fun onAdClose(type: String) {
                            if (googleMobileAdsConsentManager.canRequestAds) {
                                L.e("")
                                listener.onFinish(true)
                            }
                        }
                    })
            } else {
                if (AdsConfiguration.first_opened_enable == 1) {
                    application.showAdIfAvailable(activity,
                        object : AdsApplication.OnShowAdCompleteListener {
                            override fun onShowAdComplete() {
                                if (googleMobileAdsConsentManager.canRequestAds) {
                                    AdsConfiguration.isLoadFirstOpenOrInter = true
                                    L.e("")
                                    listener.onFinish(true)
                                }
                            }

                            override fun onShowAdFailed() {
                                if (googleMobileAdsConsentManager.canRequestAds) {
                                    AdsConfiguration.isLoadFirstOpenOrInter = false
                                    L.e("")
                                    listener.onFinish(true)
                                }
                            }
                        })
                } else {
                    L.e("")
                    listener.onFinish(true)
                }
            }
        } else {
            L.e("")
            listener.onFinish(true)
        }
    }

    interface SmartListener {
        fun onFinish(success: Boolean)
    }
}
