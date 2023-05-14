package com.hammer.talkbbokki.presentation

import android.content.Context
import android.widget.Toast
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.hammer.talkbbokki.R

var interstitialAd: InterstitialAd? = null

fun loadPageAd(context: Context) {
    InterstitialAd.load(
        context,
        context.getString(R.string.admob_ads_page_id),
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(e: LoadAdError) {
                super.onAdFailedToLoad(e)
                interstitialAd = null
            }

            override fun onAdLoaded(ad: InterstitialAd) {
                super.onAdLoaded(ad)
                interstitialAd = ad
            }
        }
    )
}

fun showPageAd(context: Context, onAdDismissed: () -> Unit) {
    val activity = context.findActivity()
    if (interstitialAd != null && activity != null) {
        interstitialAd?.let {
            it.fullScreenContentCallback = object : FullScreenContentCallback() {

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    interstitialAd = null
                    loadPageAd(context)
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    interstitialAd = null
                }
            }
        }
        interstitialAd?.show(activity)
    } else {
        Toast.makeText(context, "아직 광고가 준비되지 않았어요", Toast.LENGTH_SHORT).show()
        onAdDismissed()
    }
}

fun removePageAd() {
    interstitialAd?.fullScreenContentCallback = null
    interstitialAd = null
}
