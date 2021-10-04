package fr.leboncoin.test.commun

import android.webkit.WebSettings
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadUrl(url: String?, defaultResId: Int) {
    if (url.isNullOrEmpty()) {
        this.setImageResource(defaultResId)
    } else {
        val requestOptions = RequestOptions().apply {
            placeholder(defaultResId)
            fitCenter()
        }
        //there is issue related to header user-agent with "https://via.placeholder.com/"
        //this is just workaround #NotGlide Bug
        //@see https://github.com/bumptech/glide/issues/4074
        val glideUrl = GlideUrl(url, LazyHeaders.Builder()
                .addHeader("User-Agent", WebSettings.getDefaultUserAgent(context))
                .build()
        )

        Glide.with(context)
            .setDefaultRequestOptions(RequestOptions().placeholder(defaultResId))
            .load(glideUrl)
            .apply(requestOptions)
            .into(this)
    }
}