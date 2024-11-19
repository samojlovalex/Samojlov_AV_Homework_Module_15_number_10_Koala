package com.example.samojlov_av_homework_module_15_number_10_koala.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class Profile(
    @ColumnInfo(name = "icon") var icon: Int?,
    @ColumnInfo(name = "login") var login: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "selected") var selected: Boolean,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)