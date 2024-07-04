package com.theappcoderz.admobcustomeads.ads

import com.theappcoderz.admobcustomeads.AdsApplication
import org.json.JSONArray
import org.json.JSONObject

object ApiUtils {
    private fun adType(ftype: String, stype: String): Int {
        if (ftype.equals("0") && stype.equals("0")) {
            return 0
        } else if (ftype.equals("0") && !stype.equals("0")) {
            return stype.toInt()
        } else if (stype.equals("0") && !ftype.equals("0")) {
            return ftype.toInt()
        } else {
            return (ftype + stype).toInt()
        }
    }

    private fun getdomainlist(jsonArray: JSONArray): String {
        val domainsAsString = StringBuilder()
        for (i in 0 until jsonArray.length()) {
            val domain = jsonArray.getString(i)
            domainsAsString.append("$domain\n")
        }
        return domainsAsString.toString()
    }

    private fun getintmulti(pos: Int, sid: String): String {
        if (sid.contains(",")) {
            val strs = sid.split(",").toTypedArray()
            if (strs.size > pos) {
                return strs[pos]
            } else {
                return strs[strs.size - 1]
            }
        } else {
            return sid
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
            js.getString("openad_load_as_inter").toInt(),
            js.getString("manage_native").toInt(),
            js.getString("manage_exit_ads").toInt(),
            getdomainlist(js.getJSONArray("domain_list"))
        )
    }
}