package com.example.gituserfinder

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.gituserfinder.databinding.ActivityUserDetailsBinding

class UserDetails : AppCompatActivity() {
    private lateinit var binding:ActivityUserDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = intent
        val url = intent.getStringExtra("URL")
        val username = intent.getStringExtra("USERNAME")
        val repos = intent.getStringExtra("REPOS")

        Glide.with(this).load(url).into(binding.userImg)
        binding.txtUserName.text = username;
        binding.txtRepos.text = repos

    }
}