package com.theappcoderz.admobcustomeads

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.google.android.gms.ads.MobileAds
import com.jakewharton.threetenabp.AndroidThreeTen
import com.theappcoderz.admobcustomeads.ads.Adp
import com.theappcoderz.admobcustomeads.ads.ApiUtils
import com.theappcoderz.admobcustomeads.ads.AppConstant
import com.theappcoderz.admobcustomeads.ads.AppOpenAdManager
import com.theappcoderz.admobcustomeads.ads.InterstitialAdAdapter
import org.json.JSONObject

open class AdsApplication(private val applicationId: String) : MultiDexApplication(),
    Application.ActivityLifecycleCallbacks,
    DefaultLifecycleObserver {
    private var currentActivity: Activity? = null
    override fun onCreate() {
        super<MultiDexApplication>.onCreate()
        registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        AndroidThreeTen.init(applicationContext)
        instance = this
        prefs = Prefs(this, applicationId)
        if (prefs!!.contains(AppConstant.JSONRESPONSE)) {
            try {
                val jo = JSONObject(prefs!!.getString(AppConstant.JSONRESPONSE, "").toString())
                val js = jo.getJSONObject("appinfo")
                ApiUtils.getParsingAdsInformation(js)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        MobileAds.initialize(this)
        appOpenAdManager = AppOpenAdManager(getInstance())
        intInterstitialAdAdapter = InterstitialAdAdapter()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        currentActivity?.let {
            // Show the ad (if available) when the app moves to foreground.
            appOpenAdManager.showAdIfAvailable(it)
        }
    }

    /**
     * Called when the Activity calls [super.onCreate()][Activity.onCreate].
     */
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    /**
     * Called when the Activity calls [super.onStart()][Activity.onStart].
     */
    override fun onActivityStarted(activity: Activity) {
        if (!appOpenAdManager.isShowingAd) {
            currentActivity = activity
        }
    }

    /**
     * Called when the Activity calls [super.onResume()][Activity.onResume].
     */
    override fun onActivityResumed(activity: Activity) {
    }

    /**
     * Called when the Activity calls [super.onPause()][Activity.onPause].
     */
    override fun onActivityPaused(activity: Activity) {
    }

    /**
     * Called when the Activity calls [super.onStop()][Activity.onStop].
     */
    override fun onActivityStopped(activity: Activity) {
    }

    /**
     * Called when the Activity calls
     * [super.onSaveInstanceState()][Activity.onSaveInstanceState].
     */
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    /**
     * Called when the Activity calls [super.onDestroy()][Activity.onDestroy].
     */
    override fun onActivityDestroyed(activity: Activity) {
    }

    fun showAdIfAvailable(activity: Activity, onShowAdCompleteListener: OnShowAdCompleteListener) {
        appOpenAdManager.showAdIfAvailable(activity, onShowAdCompleteListener)
    }

    fun loadAd(activity: Activity) {
        appOpenAdManager.loadAd(activity)
    }

    interface OnShowAdCompleteListener {
        fun onShowAdComplete()

        fun onShowAdFailed()
    }

    companion object {
        lateinit var appOpenAdManager: AppOpenAdManager
        var intInterstitialAdAdapter: InterstitialAdAdapter? = null
        const val TAG = "AdsApplication"
        private var instance: AdsApplication? = null

        @Synchronized
        fun getInstance(): AdsApplication? {
            return instance
        }

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(false)
        }

        var prefs: Prefs? = null
        var packages = Adp.createDefault()
    }

}
