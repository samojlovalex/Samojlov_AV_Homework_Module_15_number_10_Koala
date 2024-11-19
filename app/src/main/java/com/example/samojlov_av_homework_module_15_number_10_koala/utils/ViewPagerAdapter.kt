package com.example.samojlov_av_homework_module_15_number_10_koala.utils

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.samojlov_av_homework_module_15_number_10_koala.fragments.WebViewFragment
import com.example.samojlov_av_homework_module_15_number_10_koala.models.Page
import com.google.gson.Gson
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

class ViewPagerAdapter(
    fragment: Fragment,
    private val viewPagerList: MutableList<Page>,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return viewPagerList.size
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun createFragment(position: Int): Fragment {
        val fragment = WebViewFragment()
        val type = typeOf<Page>().javaType
        val gson = Gson().toJson(viewPagerList[position], type)
        fragment.arguments = bundleOf("vp" to gson)
        return fragment
    }
}