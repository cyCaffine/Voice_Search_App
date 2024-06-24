package com.example.bestvoicesearch.ui.fragments

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bestvoicesearch.R
import com.example.bestvoicesearch.databinding.FragmentWebViewBinding
import com.example.bestvoicesearch.utils.NetworkChecker

class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebViewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentWebViewBinding.inflate(inflater, container, false)


        binding.webView!!.webViewClient  = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view!!.loadUrl(url)
                }
                return true
            }
        }


        binding.webView!!.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                binding.progressBar!!.visibility = View.VISIBLE
                binding.progressBar!!.progress = newProgress

                if (newProgress == 100) {
                    binding.progressBar!!.visibility = View.INVISIBLE
                }
                super.onProgressChanged(view, newProgress)
            }
        }


        val webSettings = binding.webView!!.settings
        webSettings.javaScriptEnabled = true

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // the string you passed in .navigate()
        val searchString = arguments?.getString("search")

        if (searchString != null) {
            loadWebView(searchString)
        }

        // Set the Toolbar as the ActionBar
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)

        // Enable the Up button for navigation
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowHomeEnabled(true)


        binding.toolbar.setNavigationOnClickListener{
            if(binding.webView.canGoBack()){
                binding.webView.goBack()
            }else {
                activity.onBackPressed()
            }
        }

        // Handle the back button press
        binding.webView.isFocusableInTouchMode = true
        binding.webView.requestFocus()
        binding.webView.setOnKeyListener { _, _, keyEvent ->
            if (keyEvent.keyCode == KeyEvent.KEYCODE_BACK && !binding.webView.canGoBack()) {
                false
            } else if (keyEvent.keyCode == KeyEvent.KEYCODE_BACK && keyEvent.action == MotionEvent.ACTION_UP) {
                binding.webView.goBack()
                true
            } else true
        }


    }

    // Method Call to load Webview with the search string provided by google mic
    private fun loadWebView(searchStr :String){

        //If network is not available then just return
        if(!NetworkChecker.isNetworkAvailable(requireContext())){
            Toast.makeText(requireContext(), "No Internet Available", Toast.LENGTH_SHORT).show()
//            binding.searchLayout!!.visibility = View.VISIBLE
//            binding.webViewLayout!!.visibility = View.GONE
            return
        }
          //  binding.searchLayout!!.visibility = View.GONE
            binding.webViewLayout!!.visibility = View.VISIBLE
            binding.webView!!.loadUrl("https://www.google.com/search?q=$searchStr")


    }


}