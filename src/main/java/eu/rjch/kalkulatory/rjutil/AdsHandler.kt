package eu.rjch.kalkulatory.rjutil

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class AdsHandler {
    fun getAds(view: AdView) {
        val adBanner = view
        val ar = AdRequest.Builder()
                .build()
        adBanner.loadAd(ar)
    }
}