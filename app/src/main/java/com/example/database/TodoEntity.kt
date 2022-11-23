package com.example.database

import android.graphics.Picture
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.HttpCookie.parse
import java.text.DateFormat
import java.util.*
import java.util.Date.parse

@Entity(tableName = "tasks")
class TodoEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var title: String = ""
    @ColumnInfo(name = "is_done")
    var isDone = false
    var surname: String = ""
    var phoneNumber: String = ""
    var birthDate: String = ""
    var avatar: String = ""
}