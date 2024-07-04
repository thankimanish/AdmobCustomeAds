package com.theappcoderz.admobcustomeads.ads

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.google.android.gms.common.util.IOUtils
import com.theappcoderz.admobcustomeads.Prefs
import java.io.IOException
import java.lang.ref.WeakReference

object AdsConfiguration {
    var prefs: Prefs? = null
    var isLoadFirstOpenOrInter: Boolean = false
    var alterintercounter = 0
    var instance: AdsConfiguration? = null
    var ad_priority: Int = 0 // 1=ADMOB, 2=FACEBOOK, 3=INMOBI
    var package_name: String = ""
    var monetize_id: String = ""
    var secMonetizeId: String = ""
    var display_data_enable: Int = 0
    var display_ad_enable: Int = 0
    var alternet_ad_enable: Int = 0
    var secondory_ad_enable: Int = 0
    var interestitial_ad_counter: Int = 0
    var video_ad_counter: Int = 0
    var force_update: Int = 0
    var app_version: Float = 0.0f
    var first_banner_enable: Int = 0
    var first_banner_id: String = ""
    var first_interestitial_enable: Int = 0
    var first_interstitial_id: String = ""
    var first_interstitial_id1: String = ""
    var first_interstitial_id2: String = ""
    var first_video_enable: Int = 0
    var first_video_id: String = ""
    var sec_banner_enable: Int = 0
    var sec_banner_id: String = ""
    var sec_interestitial_enable: Int = 0
    var sec_interstitial_id: String = ""
    var sec_interstitial_id1: String = ""
    var sec_interstitial_id2: String = ""
    var sec_video_enable: Int = 0
    var sec_video_id: String = ""
    var on_swipe_count: Int = 0
    var on_native_count: Int = 0
    var first_native_enable: Int = 0
    var first_native_id: String = ""
    var first_native_id1: String = ""
    var first_native_id2: String = ""
    var first_native_id3: String = ""
    var first_native_id4: String = ""
    var sec_native_enable: Int = 0
    var sec_native_id: String = ""
    var sec_native_id1: String = ""
    var sec_native_id2: String = ""
    var sec_native_id3: String = ""
    var sec_native_id4: String = ""
    var first_opened_enable: Int = 1
    var first_opened_id: String = ""
    var first_opened_id1: String = ""
    var sec_opened_enable: Int = 0
    var sec_opened_id: String = ""
    var sec_opened_id1: String = ""
    var privacy_policy_url: String = ""
    var terms_usage_url: String = ""
    var copyright_policy_url: String = ""
    var cookies_policy: String = ""
    var pp_for_younger_user: String = ""
    var community_guideline: String = ""
    var law_enforcement: String = ""
    var server_base_url: String = ""
    var custome_ads_enable: Int = 0
    var custome_ads_title: String = ""
    var custome_ads_description: String = ""
    var custome_ads_image_url: String = ""
    var custome_ads_install_url: String = ""
    var install_type: Int = 0
    var openad_load_as_inter: Int = 0
    var manage_native: Int = 0
    var manage_exit_ads: Int = 0
    var getdomainlist: String = ""

    @SuppressLint("StaticFieldLeak")
    private var activityRef: WeakReference<Activity>? = null

    @Throws(IOException::class)
    fun getByte(context: Context, i: Int): ByteArray {
        return IOUtils.toByteArray(context.resources.openRawResource(i))
    }


    @SuppressLint("CommitPrefEdits")
    @Synchronized
    fun getInstance(activity: Activity): AdsConfiguration? {
        activityRef = WeakReference(activity)
        if (instance == null) {
            instance = AdsConfiguration
        }
        return instance
    }
}