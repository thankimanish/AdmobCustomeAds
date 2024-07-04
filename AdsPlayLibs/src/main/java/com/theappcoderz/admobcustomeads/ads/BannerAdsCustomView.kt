package com.theappcoderz.admobcustomeads.ads

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.theappcoderz.admobcustomeads.R

class BannerAdsCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.banner_custom_view_layout, this, true)
        val linLayout: LinearLayout = findViewById(R.id.main_banner_container)
        if (AdsConfiguration.display_data_enable == 1
            && AdsConfiguration.display_ad_enable == 1
            && (AdsConfiguration.first_banner_enable == 1 || AdsConfiguration.sec_banner_enable == 1)) {
            linLayout.visibility = View.VISIBLE
            var adViewone = AdView(context!!)
            adViewone.setAdSize(AdSize.SMART_BANNER)
            adViewone.adUnitId = AdsConfiguration.first_banner_id
            val adRequestone = AdRequest.Builder().build()
            adViewone.loadAd(adRequestone)
            linLayout.removeAllViews()
            linLayout.addView(adViewone)
        } else {
            linLayout.visibility = View.GONE
        }
    }
}