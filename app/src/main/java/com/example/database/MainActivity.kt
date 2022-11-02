package com.example.database


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val dbHelper = DBHelper(this)

    private var list = mutableListOf<Task>()
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAdd = findViewById<Button>(R.id.button)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val editText = findViewById<EditText>(R.id.textInput)

        adapter = RecyclerAdapter(list) {
            // адаптеру передали обработчик удаления элемента
            dbHelper.removeTask(list[it].id)
            list.removeAt(it)
            adapter.notifyItemRemoved(it)

        }

        val list_ = dbHelper.getTodos()
        list.addAll(list_)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        //val buttonAdd = findViewById<Button>(R.id.button)


        buttonAdd.setOnClickListener {

            list.add(Task(dbHelper.addTask(editText.text.toString()), editText.text.toString(), false))
            adapter.notifyItemInserted(list.lastIndex)
            editText.text.clear()

        }
    }
}