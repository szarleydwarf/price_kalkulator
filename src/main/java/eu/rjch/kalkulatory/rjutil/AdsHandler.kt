package eu.rjch.kalkulatory.rjutil

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class AdsHandler {
    public fun getAds(view : AdView) {
        val adBanner = view
        val ar = AdRequest.Builder().build()
        adBanner.loadAd(ar)
    }
}