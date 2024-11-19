package com.example.samojlov_av_homework_module_15_number_10_koala.utils.message

import androidx.lifecycle.LiveData
import com.example.samojlov_av_homework_module_15_number_10_koala.models.Message

class MessageRepository (
    private val messageDao: MessageDao
) {
    val messages: LiveData<List<Message>> = messageDao.getAllMessage()

    suspend fun insert (message: Message){
        messageDao.insert(message)
    }

    suspend fun delete (message: Message){
        messageDao.delete(message)
    }

    suspend fun update(message: Message){
        messageDao.update(message)
    }

    suspend fun deleteAll(){
        messageDao.deleteAllMessage()
    }
}