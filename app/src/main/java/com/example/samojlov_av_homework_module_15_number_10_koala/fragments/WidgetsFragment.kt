package com.example.samojlov_av_homework_module_15_number_10_koala.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.samojlov_av_homework_module_15_number_10_koala.databinding.FragmentWidgetsBinding
import com.example.samojlov_av_homework_module_15_number_10_koala.models.Page
import com.example.samojlov_av_homework_module_15_number_10_koala.utils.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class WidgetsFragment : Fragment() {
    private lateinit var binding: FragmentWidgetsBinding
    private lateinit var tableLayoutTL:TabLayout
    private lateinit var viewPager: ViewPager2

    private val pagers = Page.pageList
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWidgetsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        tableLayoutTL = binding.tableLayoutTL
        viewPager = binding.viewPager

        adapter = ViewPagerAdapter(this, pagers)
        viewPager.adapter = adapter

        TabLayoutMediator(tableLayoutTL, viewPager) { tab, position ->
            val name = pagers[position].name
            tab.text = name
        }.attach()

    }

}