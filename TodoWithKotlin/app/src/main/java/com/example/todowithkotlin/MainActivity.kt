package com.example.todowithkotlin

import android.annotation.SuppressLint
import android.content.res.Resources.Theme
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.inputmethod.InputBinding
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {


    private lateinit var recyclerView : RecyclerView
    private lateinit var database: Db_Class
    private lateinit var todoMessage : TextInputEditText
    private lateinit var addBtn : AppCompatButton


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Base_Theme_TodoWithKotlin)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        todoMessage = findViewById(R.id.edt_todo_main)
        addBtn = findViewById(R.id.add_todo_btn)

        recyclerView = findViewById(R.id.recycler_view_main)
        recyclerView.layoutManager = LinearLayoutManager(this)

        database = Db_Class(this);

        val todoList : ArrayList<Todo>  = database.showTodos()
        database.close()

        val adapter : Custom_Adapter_Recycler_View = Custom_Adapter_Recycler_View(this,todoList)

        recyclerView.adapter = adapter

        addBtn.setOnLongClickListener {

            database = Db_Class(this)
            database.clearAll();
            database.close()
            recreate()

            true;
        }

        addBtn.setOnClickListener {
            database = Db_Class(this)
            if(todoMessage.text!!.isNotBlank()){
                database.addTodo(Todo(todoMessage.text.toString(),0))
                todoMessage.setText("")
                database.close()
                recreate()
            }

        }


    }
}