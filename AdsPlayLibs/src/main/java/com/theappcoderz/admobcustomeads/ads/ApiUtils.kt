package com.theappcoderz.admobcustomeads.ads

import com.theappcoderz.admobcustomeads.AdsApplication
import org.json.JSONArray
import org.json.JSONObject

object ApiUtils {
    private fun adType(ftype: String, stype: String): Int {
        return if (ftype == "0" && stype == "0") {
            0
        } else if (ftype == "0") {
            stype.toInt()
        } else if (stype == "0") {
            ftype.toInt()
        } else {
            (ftype + stype).toInt()
        }
    }

    private fun getdomainlist(js: JSONObject): String {
        return if (js.has("domain_list")) {
            val jsonArray: JSONArray = js.getJSONArray("domain_list")
            val domainsAsString = StringBuilder()
            for (i in 0 until jsonArray.length()) {
                val domain = jsonArray.getString(i)
                domainsAsString.append("$domain\n")
            }
            domainsAsString.toString()
        } else {
            ""
        }
    }

    private fun getintmulti(pos: Int, sid: String): String {
        if (sid.contains(",")) {
            val strs = sid.split(",").toTypedArray()
            return if (strs.size > pos) {
                strs[pos]
            } else {
                strs[strs.size - 1]
            }
        } else {
            return sid
        }
    }

    fun getpackagesorappinfo(js: JSONObject): JSONObject {
        return if (js.has("appinfo")) {
            js.getJSONObject("appinfo")
        } else {
            js.getJSONObject("packages")
        }
    }

    fun getParsingAdsInformation(js: JSONObject) {
        AdsApplication.packages = Adp(
            adType(js.getString("first_ad_type"), js.getString("second_ad_type")),
            js.getString("package_name"),

            js.getString("first_monetize_id"),
            js.getString("sec_monetize_id"),

            js.getString("display_data_enable").toInt(),
            js.getString("display_ad_enable").toInt(),

            js.getString("alternet_ad_enable").toInt(),
            js.getString("secondory_ad_enable").toInt(),


            js.getString("interestitial_ad_counter").toInt(),
            js.getString("video_ad_counter").toInt(),

            js.getString("force_update").toInt(),
            js.getString("app_version").toFloat(),

            js.getString("first_banner_enable").toInt(),
            js.getString("first_banner_id"),
            js.getString("first_interestitial_enable").toInt(),
            getintmulti(0, js.getString("first_interstitial_id")),
            getintmulti(1, js.getString("first_interstitial_id")),
            getintmulti(2, js.getString("first_interstitial_id")),
            js.getString("first_video_enable").toInt(),
            js.getString("first_video_id"),

            js.getString("sec_banner_enable").toInt(),
            js.getString("sec_banner_id"),
            js.getString("sec_interestitial_enable").toInt(),
            getintmulti(0, js.getString("sec_interstitial_id")),
            getintmulti(1, js.getString("sec_interstitial_id")),
            getintmulti(2, js.getString("sec_interstitial_id")),

            js.getString("sec_video_enable").toInt(),
            js.getString("sec_video_id"),

            js.getString("on_swipe_count").toInt(),
            js.getString("on_native_count").toInt(),

            js.getString("first_native_enable").toInt(),
            getintmulti(0, js.getString("first_native_id")),
            getintmulti(1, js.getString("first_native_id")),
            getintmulti(2, js.getString("first_native_id")),
            getintmulti(3, js.getString("first_native_id")),
            getintmulti(4, js.getString("first_native_id")),

            js.getString("sec_native_enable").toInt(),
            getintmulti(0, js.getString("sec_native_id")),
            getintmulti(1, js.getString("sec_native_id")),
            getintmulti(2, js.getString("sec_native_id")),
            getintmulti(3, js.getString("sec_native_id")),
            getintmulti(4, js.getString("sec_native_id")),


            js.getString("first_opened_enable").toInt(),
            getintmulti(0, js.getString("first_opened_id")),
            getintmulti(1, js.getString("first_opened_id")),

            js.getString("sec_opened_enable").toInt(),
            getintmulti(0, js.getString("sec_opened_id")),
            getintmulti(1, js.getString("sec_opened_id")),


            js.getString("privacy_policy_url"),
            js.getString("terms_usage_url"),
            js.getString("copyright_policy_url"),
            js.getString("cookies_policy"),
            js.getString("pp_for_younger_user"),
            js.getString("community_guideline"),
            js.getString("law_enforcement"),
            js.getString("server_base_url"),
            js.getString("custome_ads_enable").toInt(),
            js.getString("custome_ads_title"),
            js.getString("custome_ads_description"),
            js.getString("custome_ads_image_url"),
            js.getString("custome_ads_install_url"),
            js.getString("install_type").toInt(),
            getisopt(js).toInt(),
            mangetnativeads(js).toInt(),
            manageexitads(js).toInt(),
            getdomainlist(js)
        )
    }

    fun getParsingAdsLink(jo: JSONObject) {
        try {
            if (jo.has("link" + AppConstant.CAT_ID)) {
                AdsApplication.link1.clear()
                val link = jo.getJSONArray("link" + AppConstant.CAT_ID)
                for (i in 0 until link.length()) {
                    val datajs = link.getJSONObject(i)
                    val linkss = Link(
                        datajs.optString("id"),
                        datajs.optString("title"),
                        datajs.optString("url"),
                        generateLink(datajs.optString("token")),
                        datajs.optString("name"),
                        datajs.optString("description"),
                        ConvertIntoNumeric(datajs.optString("promo")),
                        datajs.optString("create"),
                        datajs.optString("post_url"),
                        AdsApplication.db.isDeleted(datajs.getString("id")),
                        datajs.optString("token")
                    )
                    AdsApplication.link1.add(linkss)
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun ConvertIntoNumeric(xVal: String): Int {
        return try {
            xVal.toInt()
        } catch (ex: java.lang.Exception) {
            0
        }
    }

    private fun getisopt(js: JSONObject): String {
        return if (js.isNull("openad_load_as_inter")) {
            "0"
        } else {
            js.optString("openad_load_as_inter").toString()
        }
    }

    private fun mangetnativeads(js: JSONObject): String {
        return if (js.isNull("manage_native")) {
            "0"
        } else {
            js.optString("manage_native").toString()
        }
    }

    private fun manageexitads(js: JSONObject): String {
        return if (js.isNull("manage_exit_ads")) {
            "0"
        } else {
            js.optString("manage_exit_ads").toString()
        }
    }

    private fun generateLink(token: String): String {
        val a: StringBuilder = StringBuilder("coinmaster://promotions?af_deeplink=true@campaign=")
        a.append(token)
        a.append("&media_source=FB_PAGE")
        return a.toString()
    }
}