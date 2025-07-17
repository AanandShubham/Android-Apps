package com.example.notpad

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notpad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbNotes: DbNotes
//    private lateinit var navBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var toggle :ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NotPad)
        binding = ActivityMainBinding.inflate(layoutInflater)
        dbNotes = DbNotes(this)
//        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        navBarDrawerToggle = ActionBarDrawerToggle(this,binding.root,R.string.open,R.string.close)
//        binding.root.addDrawerListener(navBarDrawerToggle)
//        navBarDrawerToggle.syncState() // Override OnOptionsItemSelected
//        supportActionBar?.setDisplayShowHomeEnabled(true)


// Setting ActionBar to Main Screen
        toggle = ActionBarDrawerToggle(this,binding.main,binding.toolbar,R.string.open,R.string.close)
        binding.main.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.navView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.hidden->{

                    val dialog = AlertDialog.Builder(this);
                    dialog.setMessage("Do you realy want to Switch to hidden notes ?")
//                    dialog.setPositiveButton()
                    dialog.show()

                    val intent:Intent = Intent(this,HiddenActivity::class.java)
                    startActivity(intent)
                    binding.main.closeDrawer(GravityCompat.START)
                }

                R.id.login->Toast.makeText(this,"Login Button is not in work now",Toast.LENGTH_LONG).show()

                R.id.settings->Toast.makeText(this,"Settings Button Clicked",Toast.LENGTH_LONG).show()
            }

            return@setNavigationItemSelectedListener true
        }

// ************ Recycler View Setup Start **********************

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        val dataList:ArrayList<Note> = dbNotes.getNotes()
        val adapter = RecyclerViewAdapter(this,dataList)
        binding.recyclerView.adapter = adapter

// ************** Recycler View Setup End *********************

// Adding listener to Add Notes Button
        binding.floatBtnAddMain.setOnClickListener {
            val intent  = Intent(this,NotesEdit::class.java)
            intent.putExtra("Flag",false)
            startActivity(intent)
        }

// Adding Listener to Update or Delete the DataBase Table
        binding.floatBtnAddMain.setOnLongClickListener {
            DbNotes(this).deleteAllNotes()
            Toast.makeText(this,"All notes Deleted Success fully ",Toast.LENGTH_LONG).show()
            recreate()
            return@setOnLongClickListener true
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(toggle.onOptionsItemSelected(item))
            true
        else
            super.onOptionsItemSelected(item)
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        if(binding.main.isDrawerOpen(GravityCompat.START)){
            binding.main.closeDrawer(GravityCompat.START)
        }
        else{
            onBackPressedDispatcher.onBackPressed()
            super.onBackPressed()
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }
}