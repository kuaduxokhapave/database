package com.example.database

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import java.util.*


class Info : AppCompatActivity() {

    lateinit var db: TodoDatabase
    lateinit var todoDao: TodoDao
    var saveStateFlag: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        // val intent = getIntent()
        val id = intent.getLongExtra(MainActivity.ID_CONTACT_KEY, 0)

        val textView = findViewById<TextView>(R.id.textView2)
        val textViewSurname = findViewById<TextView>(R.id.textView4)
        val textViewNumber = findViewById<TextView>(R.id.textView5)
        val textViewDate = findViewById<TextView>(R.id.editTextDate)




        db = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java, "tododb"
        )// выполняемся в основном потоке
            .allowMainThreadQueries()
            .build()
        todoDao = db.todoDao()

        val entity = todoDao.getById(id)
        textView.text = entity.title.toString()
        textViewSurname.text = entity.surname.toString()
        textViewNumber.text = entity.phoneNumber.toString()
        textViewDate.text = entity.birthDate.toString()


        val buttonDelete = findViewById<Button>(R.id.button4)
        buttonDelete.setOnClickListener {
            todoDao.delete(todoDao.getById(id))
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val buttonSave = findViewById<Button>(R.id.button2)
        buttonSave.setOnClickListener {
            if (saveStateFlag) {

                textView.isEnabled = false
                textViewSurname.isEnabled = false
                textViewNumber.isEnabled = false
                textViewDate.isEnabled = false


                entity.surname = textViewSurname.text.toString()
                entity.phoneNumber = textViewNumber.text.toString()
                entity.title = textView.text.toString()
                entity.birthDate = textViewDate.text.toString()
                todoDao.update(entity)


                buttonSave.text = "редактировать"

                saveStateFlag = !saveStateFlag
            } else {

                textView.isEnabled = true
                textViewSurname.isEnabled = true
                textViewNumber.isEnabled = true
                textViewDate.isEnabled = true

                buttonSave.text = "сохранить"

                saveStateFlag = !saveStateFlag
            }
        }
        val buttonExit = findViewById<Button>(R.id.button3)
        buttonExit.setOnClickListener {
            finish()
        }

        val buttonCall = findViewById<Button>(R.id.button5)
        buttonCall.setOnClickListener {
            val toDial = "tel:" + textViewNumber.text.toString()
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(toDial)))
        }

        textViewNumber.setOnClickListener {
            if(!saveStateFlag)
            {
                val toDial = "tel:" + textViewNumber.text.toString()
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(toDial)))
            }

        }

    }
}