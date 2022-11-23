package com.example.database

import android.graphics.Picture
import java.util.*

data class Task(
    val id: Long,
    val title: String,
    val isDone: Boolean,
    var surname: String,
    var phoneNumber: String,
    var birthDate: String,
    var avatar: String,
)