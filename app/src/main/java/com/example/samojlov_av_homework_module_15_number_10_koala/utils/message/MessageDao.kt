package com.example.samojlov_av_homework_module_15_number_10_koala.utils.message

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.samojlov_av_homework_module_15_number_10_koala.models.Message

@Dao
interface MessageDao {

    @Insert
    suspend fun insert (message: Message)

    @Delete
    suspend fun  delete (message: Message)

    @Update
    suspend fun update (message: Message)

    @Query("SELECT * FROM message_table ORDER BY id ASC")
    fun getAllMessage (): LiveData<List<Message>>

    @Query("DELETE FROM message_table")
    fun deleteAllMessage()

}