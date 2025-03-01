package com.example.gituserfinder

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gituserfinder.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnGet.setOnClickListener {
            val username = binding.edtUserName.text.toString();

            if(username.isNotBlank()){
                getUserData(username)
            }

        }

    }

    private fun getUserData(username:String){

        val queue  = Volley.newRequestQueue(this@MainActivity)
        val url = "https://api.github.com/users/${username}"
        val stringRequest = StringRequest(Request.Method.GET,url,
        { response ->
            val responseObject = JSONObject(response)
            val img = responseObject.get("avatar_url")
            val login = responseObject.get("login")
            val publicRepos = responseObject.get("public_repos")

            val intent = Intent(this,UserDetails::class.java)
            intent.putExtra("URL",img.toString());
            intent.putExtra("USERNAME",login.toString())
            intent.putExtra("REPOS",publicRepos.toString())
            startActivity(intent)
        },
        { error-> Toast.makeText(this,"Error : "+error.localizedMessage,Toast.LENGTH_LONG).show()

        })

        queue.add(stringRequest)
    }
}