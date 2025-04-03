package com.example.notpad

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notpad.databinding.ActivityNotesEditBinding

class NotesEdit : AppCompatActivity() {
    lateinit var binding: ActivityNotesEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesEditBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_NotPad)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

// *********** Taking Data From The Intent ***********

        val bundle = intent
        if(bundle.getBooleanExtra("Flag",false)){
            val note:Note = bundle.getSerializableExtra("NOTE") as Note
            binding.edtTitleENotes.setText(note.title)
            binding.edtNotesENotes.setText(note.note)
        }
// *********** Adding or Updating Notes to The DataBase On Button Click
        binding.btnSaveENotes.setOnClickListener {
            val cl = generateColors()
            val title = binding.edtTitleENotes.text.toString()
            val notes = binding.edtNotesENotes.text.toString()

            val db = DbNotes(this)

            if(!bundle.getBooleanExtra("Flag",false)){
                db.addNote(Note("0",title,notes,generateColors(),0))
                Log.d("Color Code Test : ",generateColors())
                db.close()
                Toast.makeText(this,"Notes Saved ",Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                val note = bundle.getSerializableExtra("NOTE") as Note
                db.updateNote(Note(note.id,title,notes,note.color,0))
                db.close()
                Toast.makeText(this,"Notes Updated ",Toast.LENGTH_LONG).show()
                finish()
            }

        }
    }

    private fun generateColors():String{
        val colors ="0123456789ABCDE"
        var colorHex:String="#"
        for(a in 0..5 )
            colorHex += colors[(Math.random()*colors.length).toInt()]
        return colorHex
    }
}