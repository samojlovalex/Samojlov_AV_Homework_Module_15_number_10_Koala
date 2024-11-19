package com.example.samojlov_av_homework_module_15_number_10_koala

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.samojlov_av_homework_module_15_number_10_koala.databinding.ActivityMainBinding
import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.samojlov_av_homework_module_15_number_10_koala.models.Profile
import com.example.samojlov_av_homework_module_15_number_10_koala.utils.profile.ProfileViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var homeCV: CardView
    private lateinit var chatCV: CardView
    private lateinit var profileCV: CardView
    private lateinit var widgetsCV: CardView
    private lateinit var settingsCV: CardView
    private lateinit var logoutCV: CardView
    private lateinit var viewModel: ProfileViewModel

    private var listOfProfiles: List<Profile>? = null
    private var activeProfile: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
        initPermission()
    }

    private fun init() {
        homeCV = binding.homeCV
        chatCV = binding.chatCV
        profileCV = binding.profileCV
        widgetsCV = binding.widgetsCV
        settingsCV = binding.settingsCV
        logoutCV = binding.logoutCV

        viewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ProfileViewModel::class.java]



        homeCV.setOnClickListener {
            setIntent("home")
        }
        chatCV.setOnClickListener {
            if (listOfProfiles == null || listOfProfiles!!.isEmpty() || activeProfile == null) return@setOnClickListener

            Toast.makeText(this, activeProfile?.login, Toast.LENGTH_LONG).show()

            setIntent("chat")
        }
        profileCV.setOnClickListener {
            setIntent("profile")
        }
        widgetsCV.setOnClickListener {
            setIntent("widgets")
        }
        settingsCV.setOnClickListener {
            setIntent("settings")
        }
        logoutCV.setOnClickListener {
            Toast.makeText(this, R.string.toast_exit, Toast.LENGTH_LONG).show()
            finish()
        }

        lifeDataListProfile()

    }


    @SuppressLint("InlinedApi")
    private fun initPermission() {
        val permission = arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        permissionLauncher.launch(permission)
    }

    private fun setIntent(key: String) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("key", key)
        startActivity(intent)
        finish()
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { }

    private fun lifeDataListProfile() {
        viewModel.profiles.observe(this) { list ->
            list?.let {
                listOfProfiles = it
                checkProfile()
            }
        }

    }

    private fun checkProfile() {
        if (listOfProfiles != null) {
            for (profile in listOfProfiles!!) {
                if (profile.selected) {
                    activeProfile = profile
                }
            }
        }
    }
}