package com.example.database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.room.Room


class Info : AppCompatActivity() {

    lateinit var db: TodoDatabase
    lateinit var todoDao: TodoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        // val intent = getIntent()
        val id = intent.getLongExtra(MainActivity.ID_CONTACT_KEY, 0)

        val textView = findViewById<TextView>(R.id.textView2)

        db = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java, "tododb"
        )// выполняемся в основном потоке
            .allowMainThreadQueries()
            .build()
        todoDao = db.todoDao()

        textView.text = todoDao.getById(id).title.toString()

        var buttonDelete = findViewById<Button>(R.id.button4)
        buttonDelete.setOnClickListener{
            todoDao.delete(todoDao.getById(id))
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}