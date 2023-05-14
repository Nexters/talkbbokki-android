package com.hammer.talkbbokki.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.hammer.talkbbokki.R

@Composable
fun BannerAds(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth().height(50.dp)) {
        val adId = stringResource(R.string.admob_ads_banner_id)
        val adRequest = AdRequest.Builder().build()
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = adId
                    loadAd(adRequest)
                }
            },
            update = { adView ->
                adView.loadAd(adRequest)
            }
        )
    }
}
