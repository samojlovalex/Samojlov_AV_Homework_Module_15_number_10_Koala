package com.example.samojlov_av_homework_module_15_number_10_koala.utils.message

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.samojlov_av_homework_module_15_number_10_koala.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MessageRepository
    val messages: LiveData<List<Message>>

    init {
        val dao = MessageDataBase.getDataBase(application).getMessageDao()
        repository = MessageRepository(dao)
        messages = repository.messages
    }

    fun insertMessage(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(message)
    }

    fun deleteMessage(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(message)
    }

    fun updateMessage(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(message)
    }

    fun deleteAllMessage() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}