package com.example.notpad

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notpad.databinding.ActivityHiddenBinding

class HiddenActivity : AppCompatActivity() {
    lateinit var binding: ActivityHiddenBinding
    lateinit var dbNotes: DbNotes
    lateinit var notesList: ArrayList<Note>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NotPad)
        binding = ActivityHiddenBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbNotes = DbNotes(this)
        notesList = dbNotes.getHiddenNotes()

        binding.hiddenRecyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.hiddenRecyclerView.adapter = RecyclerViewAdapter(this,notesList)
    }
}