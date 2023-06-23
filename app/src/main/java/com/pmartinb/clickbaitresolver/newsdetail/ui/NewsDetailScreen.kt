package com.pmartinb.clickbaitresolver.newsdetail.ui

import android.util.Base64
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.pmartinb.clickbaitresolver.newsdetail.viewmodel.NewsDetailViewModel
import java.nio.charset.Charset

@Composable
fun NewsDetailScreen(
    newsDetailViewModel: NewsDetailViewModel,
    url: String,
) {
    newsDetailViewModel.newsUrl.value = String(Base64.decode(url, Base64.DEFAULT))
    AndroidView(
        factory = { context ->
            WebView(context).apply {

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(newsDetailViewModel.newsUrl.value)
            }
        }
    )
}