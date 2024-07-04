package com.theappcoderz.admobcustomeads.ads

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.facebook.shimmer.ShimmerFrameLayout
import com.theappcoderz.admobcustomeads.R

class NativeAdsCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var isSmallAds: Boolean = false
    private var isHandleFromAPI: Boolean = false
    private var isHandleExit: Boolean = false

    init {
        LayoutInflater.from(context).inflate(R.layout.native_custom_view_layout, this, true)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.NativeAdsCustomView)
            isSmallAds = typedArray.getBoolean(R.styleable.NativeAdsCustomView_isSmallAds, false)
            isHandleFromAPI =
                typedArray.getBoolean(R.styleable.NativeAdsCustomView_isHandleFromAPI, false)
            isHandleExit =
                typedArray.getBoolean(R.styleable.NativeAdsCustomView_isHandleExit, false)
            typedArray.recycle()
        }

        val linLayout: LinearLayout = findViewById(R.id.main_banner_container_supper)
        if (AdsConfiguration.display_data_enable == 1
            && AdsConfiguration.display_ad_enable == 1
            && (AdsConfiguration.first_native_enable == 1 || AdsConfiguration.sec_native_enable == 1)) {
            linLayout.visibility = View.VISIBLE
            val inflater = LayoutInflater.from(context)
            if (isHandleFromAPI) {
                var inflatedLayout: View?
                if (AdsConfiguration.manage_native == 1) {
                    inflatedLayout =
                        inflater.inflate(R.layout.native_new_ad_layout, linLayout, false)
                } else if (AdsConfiguration.manage_native == 2) {
                    inflatedLayout =
                        inflater.inflate(R.layout.native_new_ad_layout_big, linLayout, false)
                } else {
                    inflatedLayout = null
                }

                linLayout.removeAllViews()
                if (inflatedLayout != null) {
                    linLayout.addView(inflatedLayout)
                }
                val nativeAdLayout: TemplateView? = inflatedLayout?.findViewById(R.id.my_template)
                val loaadingads: ShimmerFrameLayout? =
                    inflatedLayout?.findViewById(R.id.shimmer_layout)
                val cdbgadsshimmer: CardView? = inflatedLayout?.findViewById(R.id.cdbgadsshimmer)
                if (nativeAdLayout != null) {
                    NativeAdAdapter(context, nativeAdLayout, object : NativeAdListener {
                        override fun onAdShow() {
                            linLayout.visibility = VISIBLE
                            if (loaadingads != null) {
                                loaadingads.stopShimmer()
                                loaadingads.visibility = GONE

                            }
                            inflatedLayout?.findViewById<CardView?>(R.id.cdbgads)
                                ?.setCardBackgroundColor(Color.TRANSPARENT)
                            if (cdbgadsshimmer != null) {
                                cdbgadsshimmer.visibility = GONE
                            }
                        }

                        override fun onAdFailed() {
                            if (loaadingads != null) {
                                loaadingads.stopShimmer()
                                linLayout.visibility = GONE

                            }
                            inflatedLayout?.findViewById<CardView?>(R.id.cdbgads)
                                ?.setCardBackgroundColor(Color.TRANSPARENT)
                            if (cdbgadsshimmer != null) {
                                cdbgadsshimmer.visibility = GONE
                            }
                        }
                    })
                } else {
                    linLayout.visibility = View.GONE
                }
            } else if (isHandleExit) {
                var inflatedLayout: View?
                if (AdsConfiguration.manage_exit_ads == 1) {
                    inflatedLayout =
                        inflater.inflate(R.layout.native_new_ad_layout, linLayout, false)
                } else if (AdsConfiguration.manage_exit_ads == 2) {
                    inflatedLayout =
                        inflater.inflate(R.layout.native_new_ad_layout_big, linLayout, false)
                } else {
                    inflatedLayout = null
                }

                linLayout.removeAllViews()
                if (inflatedLayout != null) {
                    linLayout.addView(inflatedLayout)
                }
                val nativeAdLayout: TemplateView? = inflatedLayout?.findViewById(R.id.my_template)
                val loaadingads: ShimmerFrameLayout? =
                    inflatedLayout?.findViewById(R.id.shimmer_layout)
                val cdbgadsshimmer: CardView? = inflatedLayout?.findViewById(R.id.cdbgadsshimmer)
                if (nativeAdLayout != null) {
                    NativeAdAdapter(context, nativeAdLayout, object : NativeAdListener {
                        override fun onAdShow() {
                            linLayout.visibility = VISIBLE
                            if (loaadingads != null) {
                                loaadingads.stopShimmer()
                                loaadingads.visibility = GONE

                            }
                            inflatedLayout?.findViewById<CardView?>(R.id.cdbgads)
                                ?.setCardBackgroundColor(Color.TRANSPARENT)
                            if (cdbgadsshimmer != null) {
                                cdbgadsshimmer.visibility = GONE
                            }
                        }

                        override fun onAdFailed() {
                            if (loaadingads != null) {
                                loaadingads.stopShimmer()
                                linLayout.visibility = GONE

                            }
                            inflatedLayout?.findViewById<CardView?>(R.id.cdbgads)
                                ?.setCardBackgroundColor(Color.TRANSPARENT)
                            if (cdbgadsshimmer != null) {
                                cdbgadsshimmer.visibility = GONE
                            }
                        }
                    })
                } else {
                    linLayout.visibility = View.GONE
                }
            } else {
                var inflatedLayout: View?
                if (isSmallAds) {
                    inflatedLayout =
                        inflater.inflate(R.layout.native_new_ad_layout, linLayout, false)
                } else {
                    inflatedLayout =
                        inflater.inflate(R.layout.native_new_ad_layout_big, linLayout, false)
                }

                linLayout.removeAllViews()
                if (inflatedLayout != null) {
                    linLayout.addView(inflatedLayout)
                }
                val nativeAdLayout: TemplateView? = inflatedLayout?.findViewById(R.id.my_template)
                val loaadingads: ShimmerFrameLayout? = inflatedLayout?.findViewById(R.id.shimmer_layout)
                val cdbgadsshimmer: CardView? = inflatedLayout?.findViewById(R.id.cdbgadsshimmer)


                if (nativeAdLayout != null) {
                    NativeAdAdapter(context, nativeAdLayout, object : NativeAdListener {
                        override fun onAdShow() {
                            linLayout.visibility = VISIBLE
                            if (loaadingads != null) {
                                loaadingads.stopShimmer()
                                loaadingads.visibility = GONE
                            }
                            inflatedLayout?.findViewById<CardView?>(R.id.cdbgads)
                                ?.setCardBackgroundColor(Color.TRANSPARENT)
                            if (cdbgadsshimmer != null) {
                                cdbgadsshimmer.visibility = GONE
                            }
                        }

                        override fun onAdFailed() {
                            if (loaadingads != null) {
                                loaadingads.stopShimmer()
                                linLayout.visibility = GONE
                            }
                            inflatedLayout?.findViewById<CardView?>(R.id.cdbgads)
                                ?.setCardBackgroundColor(Color.TRANSPARENT)
                            if (cdbgadsshimmer != null) {
                                cdbgadsshimmer.visibility = GONE
                            }
                        }
                    })
                } else {
                    linLayout.visibility = View.GONE
                }
            }
        } else {
            linLayout.visibility = View.GONE
        }
    }
}