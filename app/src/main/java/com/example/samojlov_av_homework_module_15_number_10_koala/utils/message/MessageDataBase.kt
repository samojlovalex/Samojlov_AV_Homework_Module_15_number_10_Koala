package com.example.samojlov_av_homework_module_15_number_10_koala.utils.message

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.samojlov_av_homework_module_15_number_10_koala.models.Message

@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class MessageDataBase : RoomDatabase() {
    abstract fun getMessageDao(): MessageDao

    companion object {
        private var INSTANSE: MessageDataBase? = null
        fun getDataBase(context: Context): MessageDataBase {
            return INSTANSE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        MessageDataBase::class.java,
                        "message_database"
                    ).build()
                INSTANSE = instance
                instance
            }
        }
    }
}