package com.example.samojlov_av_homework_module_15_number_10_koala.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samojlov_av_homework_module_15_number_10_koala.R
import com.example.samojlov_av_homework_module_15_number_10_koala.databinding.FragmentChatBinding
import com.example.samojlov_av_homework_module_15_number_10_koala.models.Message
import com.example.samojlov_av_homework_module_15_number_10_koala.models.Profile
import com.example.samojlov_av_homework_module_15_number_10_koala.utils.message.MessageAdapter
import com.example.samojlov_av_homework_module_15_number_10_koala.utils.message.MessageViewModel
import com.example.samojlov_av_homework_module_15_number_10_koala.utils.profile.ProfileViewModel

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var listMessageRV: RecyclerView
    private lateinit var messageEditET: EditText
    private lateinit var messageSendIV: ImageView
    private lateinit var profileListViewModel: ProfileViewModel
    private lateinit var viewModel: MessageViewModel

    private var listOfProfiles: List<Profile>? = null
    private var activeProfile: Profile? = null
    private var listActiveMessage: List<Message>? = null
    private var adapter: MessageAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun getProfile() {
        if (listOfProfiles != null) {
            for (profile in listOfProfiles!!) {
                if (profile.selected) {
                    activeProfile = profile
                }
            }
        }
    }

    private fun init() {
        listMessageRV = binding.listMessageRV
        messageEditET = binding.messageEditET
        messageSendIV = binding.messageSendIV

        listMessageRV.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[MessageViewModel::class.java]

        profileListViewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[ProfileViewModel::class.java]


        initAdapter()

        lifeDataProfile()

        lifeDataMessage()

        messageSendIV.setOnClickListener {
            if (messageEditET.text.isEmpty()) return@setOnClickListener
            enterMessage()
        }


    }

    private fun enterMessage() {
        val time = ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
            .format(DateTimeFormatter.ofPattern("dd.MM.yy / HH.mm.ss"))
        if (activeProfile != null) {
            viewModel.insertMessage(
                Message(
                    activeProfile!!.icon,
                    activeProfile!!.login,
                    time,
                    messageEditET.text.toString().trim()
                )
            )
        }
        messageEditET.text.clear()
    }

    private fun initAdapter() {
        adapter = MessageAdapter(requireContext().applicationContext)
        listMessageRV.adapter = adapter
        adapter!!.setOnMessageClickListener(object : MessageAdapter.OnMessageClickListener {
            override fun onMessageClick(message: Message, position: Int) {
                editMessage(message)
            }
        })
    }

    private fun editMessage(message: Message) {
        Toast.makeText(
            context,
            getString(R.string.editMessage_Toast, message.name), Toast.LENGTH_LONG
        ).show()
    }

    private fun lifeDataMessage() {
        viewModel.messages.observe(viewLifecycleOwner) { list ->
            list?.let {
                val listCurrent = it.filter { message -> message.name == activeProfile?.login }
                adapter?.updateList(listCurrent!!)
            }
        }
    }

    private fun lifeDataProfile() {
        profileListViewModel.profiles.observe(viewLifecycleOwner) { list ->
            list?.let {
                listOfProfiles = it
                getProfile()
            }
        }
    }
}