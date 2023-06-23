package com.pmartinb.clickbaitresolver.web.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pmartinb.clickbaitresolver.databinding.ActivityNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    private val newsViewModel: NewsViewModel by viewModels()

    private val INITIAL_URL = "some url"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initializeView()
        initWebView(INITIAL_URL)
    }

    private fun initializeView() {
        binding.send.setOnClickListener {
            newsViewModel.sendNewsContentClicked()
        }
        binding.send.isEnabled = false
        CoroutineScope(Dispatchers.Main).launch {
            newsViewModel.currentHeadline.collect {
                setHeadline(it)
            }
        }
    }

    private fun initWebView(url: String){
        with(binding.activityWebView) {
            settings.javaScriptEnabled = true
            val jInterface = MyJavaScriptInterface()
            addJavascriptInterface(jInterface, "HtmlViewer")

            webViewClient = object : WebViewClient() {

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    loadUrl("javascript:window.HtmlViewer.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")
                }
            }

            loadUrl(url)
            jInterface.showHTML(jInterface.html)

        }
    }

    private fun setHeadline(headline: String) {
        binding.headline.text = headline
    }

    private fun setNewsText(text: String) {
        binding.webContent.text = text
        CoroutineScope(Dispatchers.Main).launch {

            binding.webContent.movementMethod = ScrollingMovementMethod()
            binding.send.isEnabled = true
        }
    }

    inner class MyJavaScriptInterface {
        var html: String? = null
        @JavascriptInterface
        fun showHTML(_html: String?): String {
            html = _html
            var paragraph = ""
            html?.let {
                val doc: Document = Jsoup.parse(it)
                val content: Elements? = doc.getElementsByClass("article__paragraph")
                paragraph = content?.text() ?: ""
                setNewsText(paragraph)
                Log.i("TAG", "showHTML: -->> $paragraph")
            }
            return paragraph
        }
    }
}


