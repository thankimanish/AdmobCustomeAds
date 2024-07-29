package com.theappcoderz.admobcustomeads.ads

interface InterAdListener {
    fun onAdClose(type:String)
    fun onAdShow(type:String)
}