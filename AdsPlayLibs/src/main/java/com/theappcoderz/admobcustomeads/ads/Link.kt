package com.theappcoderz.admobcustomeads.ads

data class Link(
    @JvmField var id: String,
    @JvmField var title: String,
    @JvmField var url: String,
    @JvmField var token: String,
    @JvmField var name: String,
    @JvmField var description: String,
    @JvmField var promo: Int,
    @JvmField var create: String,
    @JvmField var post_url: String,
    @JvmField var isClaimed: Boolean,
    @JvmField var onlytoken: String,
)