package com.ott.tv.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.ott.tv.R

class PlaybackActivityYoutube : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playback_youtube)
       // intent.getStringExtra("")
      /*  val url = intent.getStringExtra("youtube")*/
        val url = "https://www.youtube.com/watch?v=cw0mAUbVGhc"
        val splitParts = url?.split("/watch?v=")
        val videoId = splitParts?.get(1)

        val frameVideo =
            "<html><body style='margin:0;padding:0;'><iframe width='100%' height='100%' src='https://www.youtube.com/embed/$videoId?rel=0&amp;showinfo=0' frameborder='0' allowfullscreen referrerpolicy='no-referrer-when-downgrade'></iframe></body></html>"

        /* val frameVideo =
             "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$videoId\" frameborder=\"0\" allowfullscreen referrerpolicy=\"no-referrer-when-downgrade allow=\"autoplay; encrypted-media\"></iframe></body></html>"
       */

        val displayYoutubeVideo: WebView = findViewById<View>(R.id.mWebView) as WebView
        displayYoutubeVideo.setWebViewClient(object : WebViewClient() {
            override  fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }
        })
        val webSettings: WebSettings = displayYoutubeVideo.getSettings()
        webSettings.setJavaScriptEnabled(true)


        displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8")


    }
}