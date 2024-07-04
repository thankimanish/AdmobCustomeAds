package com.theappcoderz.sampleads

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.theappcoderz.sampleads.databinding.ActivityAdsBinding

class AdsActivity : AppCompatActivity() {
    private var binding: ActivityAdsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate: ActivityAdsBinding = ActivityAdsBinding.inflate(layoutInflater)
        binding = inflate
        setContentView(inflate.root)
    }
}