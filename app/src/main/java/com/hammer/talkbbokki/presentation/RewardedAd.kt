package com.hammer.talkbbokki.presentation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.hammer.talkbbokki.R

var rewardedAd: RewardedAd? = null

fun loadAd(context: Context) {
    RewardedAd.load(
        context,
        context.getString(R.string.admob_ads_full_screen_id),
        AdRequest.Builder().build(),
        object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                // 광고 로드 실패
                rewardedAd = null
            }

            override fun onAdLoaded(ad: RewardedAd) {
                // 광고 로드 성공
                rewardedAd = ad
            }
        }
    )
}

fun showRewardedAd(context: Context, onAdDismissed: () -> Unit) {
    val activity = context.findActivity()

    if (rewardedAd != null && activity != null) {
        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                rewardedAd = null
                loadAd(context)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                rewardedAd = null
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
            }
        }
        rewardedAd?.show(activity) {
            onAdDismissed()
        }
    } else {
        Toast.makeText(context, "아직 광고가 준비되지 않았어요", Toast.LENGTH_SHORT).show()
        onAdDismissed()
    }
}

fun removeAd() {
    rewardedAd?.fullScreenContentCallback = null
    rewardedAd = null
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
