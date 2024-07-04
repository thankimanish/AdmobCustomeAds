package com.theappcoderz.sampleads

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.theappcoderz.admobcustomeads.AdsApplication
import com.theappcoderz.admobcustomeads.ads.InterAdListener
import com.theappcoderz.sampleads.databinding.ActivityMainBinding

class MainActivity:AppCompatActivity() {
    private var binding:ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        binding = inflate
        setContentView(inflate.root)
        binding!!.clickads.setOnClickListener {
            AdsApplication.intInterstitialAdAdapter?.showAds(this@MainActivity,
                "ConfigurationActivity",
                false,
                object : InterAdListener {
                    override fun onAdClose(type: String) {
                        startActivity(
                            Intent(
                                this@MainActivity,
                                AdsActivity::class.java
                            )
                        )
                    }
                })

        }
    }

}