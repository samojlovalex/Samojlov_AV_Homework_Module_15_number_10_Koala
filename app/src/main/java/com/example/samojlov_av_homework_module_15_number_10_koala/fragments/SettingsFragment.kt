package com.example.samojlov_av_homework_module_15_number_10_koala.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.samojlov_av_homework_module_15_number_10_koala.MainActivity
import com.example.samojlov_av_homework_module_15_number_10_koala.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var settingGifImageIV: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        settingGifImageIV = binding.settingGifImageIV
        settingGifImageIV.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }
    }
}