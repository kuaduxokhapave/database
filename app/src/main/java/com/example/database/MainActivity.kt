package com.example.database


//import TodoDao
//import TodoDatabase
//import TodoEntity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    lateinit var db: TodoDatabase
    lateinit var todoDao: TodoDao

    private val list = mutableListOf<TodoEntity>()
    private lateinit var adapter: RecyclerAdapter

    companion object {
        const val ID_CONTACT_KEY = "ID_CONTACT_KEY"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAdd = findViewById<Button>(R.id.button)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val editText = findViewById<EditText>(R.id.textInput)


        db = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java, "tododb"
        )// выполняемся в основном потоке
            .allowMainThreadQueries()
            .build()
        todoDao = db.todoDao()



        val list_ = todoDao.all//dbHelper.getTodos()

        list.addAll(list_)

        adapter = RecyclerAdapter(list) {

            val intent = Intent(this, Info::class.java)
            intent.putExtra(ID_CONTACT_KEY, list[it].id)
            startActivity(intent)


        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        buttonAdd.setOnClickListener {

            //list.add(Task(dbHelper.addTask(editText.text.toString()), editText.text.toString(), false))
            val todoEntity = TodoEntity()
            todoEntity.title = editText.text.toString()
            todoEntity.isDone = false
            todoEntity.phoneNumber = ""
            todoEntity.surname = ""
            todoEntity.birthDate = ""

            todoEntity.id = todoDao.insert(todoEntity)

            list.add(todoEntity)
            adapter.notifyItemInserted(list.lastIndex)
            editText.text.clear()

            val intent = Intent(this, Info::class.java)
            intent.putExtra(ID_CONTACT_KEY, todoEntity.id)
            startActivity(intent)

        }

        editText.doOnTextChanged { text, start, before, count ->
            run {
                //list.removeIf { it.title.toString().contains(text.toString()) }
                list.clear()
                list.addAll(list_)
                list.removeIf { !it.title.toString().contains(text.toString(), ignoreCase = true) }
                adapter.notifyDataSetChanged()
            }
        }


    }


}