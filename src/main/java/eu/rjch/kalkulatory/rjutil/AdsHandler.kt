package eu.rjch.kalkulatory.rjutil

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import eu.rjch.kalkulatory.R

class AdsHandler : Fragment() {

    val TAG = "ADS"
    companion object{
        fun newInstance() = AdsHandler()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.ads_handler_layout, container, false)

        getAds(v.findViewById(R.id.adViewB))

        return v
    }


    fun getAds(view: AdView) {
        val adBanner = view
        val ar = AdRequest.Builder()
                .build()
        adBanner.loadAd(ar)
    }
}