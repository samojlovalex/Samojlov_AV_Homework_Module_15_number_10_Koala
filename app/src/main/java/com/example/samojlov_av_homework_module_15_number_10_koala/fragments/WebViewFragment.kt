package com.example.samojlov_av_homework_module_15_number_10_koala.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.samojlov_av_homework_module_15_number_10_koala.R
import com.example.samojlov_av_homework_module_15_number_10_koala.databinding.FragmentWebViewBinding
import com.example.samojlov_av_homework_module_15_number_10_koala.models.Page
import com.google.gson.Gson
import kotlin.reflect.javaType
import kotlin.reflect.typeOf


class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebViewBinding
    private lateinit var webViewWV: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun init() {
        webViewWV = binding.webViewWV
        webViewWV.webViewClient = WebViewClient()
        val type = typeOf<Page?>().javaType
        val gson = arguments?.getString("vp")
        val viewPagerItem = Gson().fromJson<Page>(gson,type)
        webViewWV.loadUrl(viewPagerItem.url)
    }

}