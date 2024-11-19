package com.example.samojlov_av_homework_module_15_number_10_koala.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samojlov_av_homework_module_15_number_10_koala.R
import com.example.samojlov_av_homework_module_15_number_10_koala.databinding.FragmentProfileBinding
import com.example.samojlov_av_homework_module_15_number_10_koala.models.IconAvatar
import com.example.samojlov_av_homework_module_15_number_10_koala.models.Profile
import com.example.samojlov_av_homework_module_15_number_10_koala.utils.profile.EditImageAdapter
import com.example.samojlov_av_homework_module_15_number_10_koala.utils.profile.ProfileAdapter
import com.example.samojlov_av_homework_module_15_number_10_koala.utils.profile.ProfileViewModel
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var listOfUsersRV: RecyclerView
    private lateinit var newProfileButtonBT: Button
    private lateinit var deleteProfileButtonBT: Button
    private var editImage: ImageView? = null

    private var adapter: ProfileAdapter? = null
    private lateinit var viewModel: ProfileViewModel
    private var listOfProfiles: List<Profile>? = null
    private var icon: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        listOfUsersRV = binding.listOfUsersRV
        newProfileButtonBT = binding.newProfileButtonBT
        deleteProfileButtonBT = binding.deleteProfileButtonBT


        listOfUsersRV.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[ProfileViewModel::class.java]

        newProfileButtonBT.setOnClickListener {
            initProfile()
        }

        initAdapter()

        lifeDataProfile()

        deleteProfileButtonBT.setOnLongClickListener {
            deleteAllProfile()
            false
        }
    }

    private fun deleteAllProfile() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val alert = dialogBuilder.create()
        val dialogValues = inflater.inflate(R.layout.delete_profile_alter_dialog, null)
        alert.setView(dialogValues)
        dialogValues.setBackgroundColor(Color.parseColor("#FFFFFF"))
        alert.show()

        val message = dialogValues.findViewById<TextView>(R.id.delete_messageTV)
        val cancel =
            dialogValues.findViewById<Button>(R.id.delete_profile_alter_dialog_button_cancelBT)
        val delete =
            dialogValues.findViewById<Button>(R.id.delete_profile_enter_alter_dialog_button_deleteBT)

        message.text =
            getString(R.string.delete_all_profiles_message_Text)

        cancel.setOnClickListener {
            alert.cancel()
        }

        delete.setOnClickListener {
            viewModel.deleteAllProfile()
            alert.cancel()
            Toast.makeText(
                context,
                getString(R.string.deleteAllProfile_Toast),
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun initProfile() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val alert = dialogBuilder.create()
        val dialogValues = inflater.inflate(R.layout.profile_alter_dialog, null)
        alert.setView(dialogValues)

        editImage = dialogValues?.findViewById(R.id.profile_alter_dialog_imageIV)
        val login = dialogValues?.findViewById<EditText>(R.id.profile_alter_dialog_loginET)
        val password = dialogValues?.findViewById<EditText>(R.id.profile_alter_dialog_passwordET)
        val cancel = dialogValues?.findViewById<Button>(R.id.profile_alter_dialog_button_cancelBT)
        val save = dialogValues?.findViewById<Button>(R.id.profile_alter_dialog_button_saveBT)

        dialogValues.setBackgroundColor(Color.parseColor("#FFFFFF"))
        alert.show()


        editImage?.setOnClickListener {
            if (editImage != null) {
                getImage(IconAvatar.listAvatarIcon)
            }
        }

        cancel?.setOnClickListener {
            alert.cancel()
        }


        save?.setOnClickListener {
            if (login!!.text.isEmpty() || password!!.text.isEmpty()) return@setOnClickListener
            viewModel.insertProfile(
                Profile(
                    icon,
                    login.text.toString().trim(),
                    password.text.toString().trim(),
                    false
                )
            )
            alert.cancel()
            deleteProfileButtonBT.visibility = View.VISIBLE
        }
    }

    private fun getImage(list: List<Int>) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val alert = dialogBuilder.create()
        val dialogValues = inflater.inflate(R.layout.edit_image_alert_dialog, null)
        alert.setView(dialogValues)

        dialogValues.setBackgroundColor(Color.parseColor("#FFFFFF"))
        alert.show()

        val recyclerView = dialogValues.findViewById<RecyclerView>(R.id.editImageRV)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val adapter = EditImageAdapter(list)
        recyclerView.adapter = adapter

        adapter.setIconClickListener(object : EditImageAdapter.OnEditImageClickListener {
            override fun onEditImageClickListener(image: Int, position: Int) {
                enterIcon(image)
                alert.cancel()
            }
        })


    }

    private fun enterIcon(image: Int?) {
        icon = image
        editImage?.setImageResource(image!!)
    }

    private fun initAdapter() {
        adapter = ProfileAdapter(requireContext().applicationContext)
        listOfUsersRV.adapter = adapter
        adapter!!.setOnProfileClickListener(object : ProfileAdapter.OnProfileClickListener {
            override fun onProfileClick(profile: Profile, position: Int) {
                enterProfile(profile)
            }

        })
    }

    private fun enterProfile(profile: Profile) {
        if (profile.selected) {
            editProfile(profile)
        } else {
            loginProfile(profile)
        }
    }

    private fun editProfile(profile: Profile) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val alert = dialogBuilder.create()
        val dialogValues = inflater.inflate(R.layout.profile_edit_alter_dialog, null)
        alert.setView(dialogValues)
        dialogValues.setBackgroundColor(Color.parseColor("#FFFFFF"))
        alert.show()

        editImage = dialogValues.findViewById(R.id.profile_edit_alter_dialog_imageIV)
        val login = dialogValues.findViewById<EditText>(R.id.profile_edit_alter_dialog_loginET)
        val password =
            dialogValues.findViewById<EditText>(R.id.profile_edit_alter_dialog_passwordET)
        val cansel =
            dialogValues.findViewById<Button>(R.id.profile_edit_alter_dialog_button_cancelBT)
        val save = dialogValues.findViewById<Button>(R.id.profile_edit_alter_dialog_button_saveBT)
        val delete =
            dialogValues.findViewById<Button>(R.id.profile_edit_alter_dialog_button_deleteBT)

        if (profile.icon != null) {
            editImage?.setImageResource(profile.icon!!)
        } else {
            editImage?.setImageResource(R.drawable.profile_icon_base)
        }

        login.setText(profile.login)
        password.setText(profile.password)

        editImage?.setOnClickListener {
            val list: MutableList<Int> = IconAvatar.listAvatarIcon.toMutableList()
            list.add(R.drawable.profile_icon_base)
            getImage(list.toList())
        }

        cansel.setOnClickListener {
            alert.cancel()
        }

        save.setOnClickListener {
            if (login.text.isEmpty() || password.text.isEmpty()) return@setOnClickListener
            profile.icon = icon
            profile.login = login.text.toString().trim()
            profile.password = password.text.toString().trim()
            viewModel.updateProfile(profile)
            alert.cancel()
        }

        delete.setOnLongClickListener {
            deleteOneProfile(alert, profile)
            false
        }
    }

    private fun deleteOneProfile(alertOut: AlertDialog, profile: Profile) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val alert = dialogBuilder.create()
        val dialogValues = inflater.inflate(R.layout.delete_profile_alter_dialog, null)
        alert.setView(dialogValues)
        dialogValues.setBackgroundColor(Color.parseColor("#FFFFFF"))
        alert.show()

        val message = dialogValues.findViewById<TextView>(R.id.delete_messageTV)
        val cancel =
            dialogValues.findViewById<Button>(R.id.delete_profile_alter_dialog_button_cancelBT)
        val delete =
            dialogValues.findViewById<Button>(R.id.delete_profile_enter_alter_dialog_button_deleteBT)

        message.text =
            getString(R.string.delete_profile_alter_dialog_message_Text, profile.login)

        cancel.setOnClickListener {
            alert.cancel()
        }

        delete.setOnClickListener {
            viewModel.deleteProfile(profile)
            alert.cancel()
            alertOut.cancel()
            Toast.makeText(
                context,
                getString(R.string.delete_profile_alert_dialog_Toast, profile.login),
                Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun loginProfile(profile: Profile) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val alert = dialogBuilder.create()
        val dialogValues = inflater.inflate(R.layout.profile_enter_alter_dialog, null)
        alert.setView(dialogValues)
        dialogValues.setBackgroundColor(Color.parseColor("#FFFFFF"))
        alert.show()

        editImage = dialogValues.findViewById(R.id.profile_enter_alter_dialog_imageIV)
        val login = dialogValues.findViewById<TextView>(R.id.profile_enter_alter_dialog_loginTV)
        val password =
            dialogValues.findViewById<EditText>(R.id.profile_enter_alter_dialog_passwordET)
        val cancel = dialogValues.findViewById<Button>(R.id.profile_alter_dialog_button_cancelBT)
        val enter = dialogValues.findViewById<Button>(R.id.profile_enter_alter_dialog_button_saveBT)

        if (profile.icon != null) {
            editImage?.setImageResource(profile.icon!!)
        } else {
            editImage?.setImageResource(R.drawable.profile_icon_base)
        }

        login.text = profile.login

        cancel.setOnClickListener {
            alert.cancel()
        }

        enter.setOnClickListener {
            if (password.text.toString() != profile.password) return@setOnClickListener
            profile.selected = true
            viewModel.updateProfile(profile)

            if (listOfProfiles != null) {
                for (otherProfile in listOfProfiles!!) {
                    if (otherProfile != profile) {
                        otherProfile.selected = false
                        viewModel.updateProfile(otherProfile)
                    }
                }
            }
            alert.cancel()
        }


    }

    private fun lifeDataProfile() {
        viewModel.profiles.observe(viewLifecycleOwner) { list ->
            list?.let {
                listOfProfiles = it
                adapter?.updateList(it)
            }
        }
    }
}