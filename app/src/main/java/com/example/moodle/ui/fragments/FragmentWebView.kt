package com.example.moodle.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.moodle.R

class FragmentWebView: Fragment(){
    lateinit var webview:WebView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =layoutInflater.inflate(R.layout.activity_webpage,container,false)
        webview = view.findViewById(R.id.webview)
        webview.webViewClient=WebViewClient()
        webview.loadUrl("https://lms.iitpkd.ac.in")
        return view
    }

    public fun goBack():Boolean{
        if(webview.canGoBack()){
            webview.goBack()
            return true}
        activity?.supportFragmentManager?.popBackStack()
        return false
    }



}
