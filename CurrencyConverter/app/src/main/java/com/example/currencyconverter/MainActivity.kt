package com.example.currencyconverter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Request.Method
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.currencyconverter.databinding.ActivityMainBinding
import org.json.JSONObject
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {
    private lateinit var currencyList :JSONObject

    private lateinit var binding: ActivityMainBinding
    @SuppressLint("SetTextI18n")
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

        binding.toCard.labelFrom.text = "To"

        getCurrency("usd")

        binding.btnConvert.setOnClickListener {
            val amount = currencyList.get("inr") as Double
            val inputAmount = binding.fromCard.edtNumber.text.toString()

            if(inputAmount.isNotBlank()){
                (binding.toCard.edtNumber as TextView).text = ""+(amount * inputAmount.toDouble())
            }

        }

    }

    private fun getCurrency(currency:String){

        val queue = Volley.newRequestQueue(this)
        val url = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/${currency}.json"

//        binding.toCard.spTypes.setBackgroundColor(Color.BLACK)
//        binding.fromCard.spTypes.setBackgroundColor(Color.BLACK)
        val stringRequest = StringRequest(
            Method.GET,
            url,
            { response ->
                val data = JSONObject(response)
                Log.d("REsques t",data.get("usd").toString())
                val dataList = data.get(currency) as JSONObject

//                Toast.makeText(this, "DataList : $dataList",Toast.LENGTH_LONG).show()

//                Toast.makeText(this,"data send : "+data.get("usd").toString(),Toast.LENGTH_LONG).show()
                val list = ArrayList<String>()
                dataList.keys().forEach { it -> list.add(it) }

                val adapter = ArrayAdapter<String>(this@MainActivity,android.R.layout.simple_spinner_item,list)
                binding.toCard.spTypes.adapter = adapter;
                binding.fromCard.spTypes.adapter = adapter

                currencyList = dataList

            },
            {error->
                Toast.makeText(this, "error : $error",Toast.LENGTH_LONG).show()
            }
        )

        queue.add(stringRequest)
    }
}