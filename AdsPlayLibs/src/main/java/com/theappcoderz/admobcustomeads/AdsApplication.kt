package com.theappcoderz.admobcustomeads

import android.app.Activity
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.ContextThemeWrapper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.bumptech.glide.Glide
import com.google.android.gms.ads.MobileAds
import com.jakewharton.threetenabp.AndroidThreeTen
import com.theappcoderz.admobcustomeads.ads.Adp
import com.theappcoderz.admobcustomeads.ads.ApiUtils
import com.theappcoderz.admobcustomeads.ads.AppConstant
import com.theappcoderz.admobcustomeads.ads.DatabaseHelper
import com.theappcoderz.admobcustomeads.ads.DelayedProgressDialog
import com.theappcoderz.admobcustomeads.ads.InterstitialAdAdapter
import com.theappcoderz.admobcustomeads.ads.Link
import org.json.JSONObject

open class AdsApplication(private val applicationId: String) : MultiDexApplication(),
    Application.ActivityLifecycleCallbacks,
    DefaultLifecycleObserver {
    private lateinit var appOpenAdManager: AppOpenAdManager
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
                val js = ApiUtils.getpackagesorappinfo(jo)
                ApiUtils.getParsingAdsInformation(js)
                ApiUtils.getParsingAdsLink(jo)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        MobileAds.initialize(this)

        appOpenAdManager = getappmanagerinstance()
        intInterstitialAdAdapter = InterstitialAdAdapter()
    }


    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        currentActivity?.let {
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

    fun unloadAd(activity: Activity) {
        appOpenAdManager.unload()
    }

    fun loadAd(activity: Activity) {
        appOpenAdManager.loadAd(activity)
    }

    interface OnShowAdCompleteListener {
        fun onShowAdComplete()

        fun onShowAdFailed()
    }


    companion object {
        var link1: ArrayList<Link> = ArrayList()
        var intInterstitialAdAdapter: InterstitialAdAdapter? = null
        const val TAG = "AdsApplication"
        private var instance: AdsApplication? = null
        private var appopenmanger: AppOpenAdManager? = null

        @Synchronized
        fun getInstance(): AdsApplication? {
            return instance
        }

        fun getappmanagerinstance(): AppOpenAdManager {
            return appopenmanger ?: synchronized(this) {
                appopenmanger ?: AppOpenAdManager(getInstance()).also { appopenmanger = it }
            }
        }

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(false)
        }

        var prefs: Prefs? = null
        var packages = Adp.createDefault()
        var db = DatabaseHelper(getInstance())

        var mProgressDialog: DelayedProgressDialog? =
            DelayedProgressDialog()

        fun availableUpdate(isCancel: Boolean, context: Context) {
            val versionName = context.packageManager.getPackageInfo(
                context.packageName, 0
            ).versionName
            if (versionName.toFloat() < packages.app_version) {
                if (isCancel) {
                    val builder: AlertDialog.Builder =
                        AlertDialog.Builder(ContextThemeWrapper(context, R.style.myDialog))
                    builder.setCancelable(false)
                    builder.setTitle("Update Message")
                        .setMessage("Update is available. Are you sure you want to update?")
                        .setCancelable(false)
                        .setPositiveButton("Update") { dialog, _ ->
                            updateApp(context)
                            dialog.dismiss()
                        }

                        .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                    var dialog: AlertDialog = builder.create()
                    dialog.show()

                } else {
                    val builder: AlertDialog.Builder =
                        AlertDialog.Builder(ContextThemeWrapper(context, R.style.myDialog))
                    builder.setCancelable(false)
                    builder.setTitle("Update Message")
                        .setMessage("Latest Update is available.")
                        .setCancelable(false)
                        .setPositiveButton("Update") { dialog, _ ->
                            updateApp(context)
                            dialog.dismiss()
                        }
                    var dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
        }

        fun customeAds(context: Context) {
            val alertCustomdialog: View =
                LayoutInflater.from(context).inflate(R.layout.custome_ad, null)
            val alert = context.let { AlertDialog.Builder(it) }
            alert.setView(alertCustomdialog)


            var install = alertCustomdialog.findViewById<View>(R.id.ad_install) as Button
            var cancel_button =
                alertCustomdialog.findViewById<View>(R.id.cancel_button) as ImageView
            var ad_app_icon = alertCustomdialog.findViewById<View>(R.id.ad_app_icon) as ImageView
            var ad_image = alertCustomdialog.findViewById<View>(R.id.ad_image) as ImageView
            var ad_headline = alertCustomdialog.findViewById<View>(R.id.ad_headline) as TextView
            var ad_description =
                alertCustomdialog.findViewById<View>(R.id.ad_description) as TextView

            if (packages.install_type === 0) {
                cancel_button.visibility = View.VISIBLE
                install.text = "OK"
            } else if (packages.install_type === 1) {
                cancel_button.visibility = View.VISIBLE
                install.text = "Install"
            } else if (packages.install_type === 2) {
                cancel_button.visibility = View.GONE
                install.text = "Install"
            }
            ad_headline.text = packages.custome_ads_title
            ad_description.text = packages.custome_ads_description
            context.let {
                Glide.with(it).load(packages.custome_ads_image_url).into(ad_app_icon)
            }
            context.let {
                Glide.with(it).load(packages.custome_ads_image_url).into(ad_image)
            }

            val dialog = alert.create()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()

            if (packages.install_type == 2) {
                dialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event -> // Prevent dialog close on back press button
                    keyCode == KeyEvent.KEYCODE_BACK
                })
            }

            cancel_button.setOnClickListener {
                dialog.dismiss()
            }
            install.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(packages.custome_ads_install_url)
                )
                context.startActivity(browserIntent)
            })
        }

        fun maintenanceUpdate(context: Context) {
            val builder: AlertDialog.Builder =
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.myDialog))
            builder.setCancelable(false)
            builder.setTitle("Maintenance")
                .setMessage("Service Temporarily Unavailable The maintenance mode is enabled")
                .setCancelable(false)
            var dialog: AlertDialog = builder.create()
            dialog.show()
        }

        fun updateApp(context: Context) {
            val uri = Uri.parse("market://details?id=" + context.packageName)
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
            try {
                context.startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + context.packageName)
                    )
                )
            }
        }

        fun shareApp(context: Context, appname: String) {
            try {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plain"
                i.putExtra(Intent.EXTRA_SUBJECT, appname)
                var sAux = "Download $appname App\n"
                sAux =
                    sAux + "https://play.google.com/store/apps/details?id=" + context.packageName + " \n"
                i.putExtra(Intent.EXTRA_TEXT, sAux)
                context.startActivity(Intent.createChooser(i, "choose one"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun contactus(context: Context) {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "onetoolsapp@gmail.com", null
                )
            )
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Spin Link")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")
            context.startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }

        fun rateApp(context: Context) {
            val builder: AlertDialog.Builder =
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.myDialog))
            builder.setCancelable(false)
            builder.setTitle("Rate Message")
                .setMessage("Please give us ratings & Review ?")
                .setCancelable(false)
                .setPositiveButton("Rate Us") { dialog, _ ->
                    updateApp(context)
                    dialog.dismiss()
                }

                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            var dialog: AlertDialog = builder.create()
            dialog.show()
        }

        fun redirecttoweb(data: String, context: Context) {
            val intent1 = Intent(
                context,
                StandardWebView::class.java
            )
            intent1.putExtra("ppurl", data)
            context.startActivity(intent1)
        }

    }

}
