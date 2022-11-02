package com.example.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
class TodoEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var title: String = ""
    @ColumnInfo(name = "is_done")
    var isDone = false
}