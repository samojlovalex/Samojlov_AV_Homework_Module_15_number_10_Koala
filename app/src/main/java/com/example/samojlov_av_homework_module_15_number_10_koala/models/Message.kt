package com.example.samojlov_av_homework_module_15_number_10_koala.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
data class Message(
    @ColumnInfo(name = "icon") var icon: Int?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "time") var time: String,
    @ColumnInfo(name = "message")var message: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)