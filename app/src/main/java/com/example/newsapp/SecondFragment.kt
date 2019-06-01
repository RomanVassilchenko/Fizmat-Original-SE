package com.example.newsapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

class SecondFragment : Fragment() {

    lateinit var url: String
    lateinit var vBrowser: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO Создать собственную 404 страницу
        val url404 = "http://hotdot.pro/en/404/"
        url = arguments?.getString("url") ?: url404

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.sec_fragment, container, false)
        vBrowser = view.findViewById<WebView>(R.id.frag2_browser)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vBrowser.loadUrl(url)
    }
}