package com.example.weather_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_app.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)

        Log.d("Stage1", "retrofit created !!")

        val response =
            retrofit.getWeatherData("patna", "d326688fd6b854c03eb398d041bf09ff", "metric")
        Log.d("Stage2", "Retrofit Called with data")
        response.enqueue(object : Callback<WeatherApp> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<WeatherApp?>, response: Response<WeatherApp?>) {
                val responseBody = response.body()
                Log.d("Stage3", "Running ON Response")
                Toast.makeText(applicationContext, "Waiting for Response : ", Toast.LENGTH_LONG)
                    .show()

                if (response.isSuccessful && responseBody != null) {
                    val temperature = responseBody.main.temp
                    val humidity = responseBody.main.humidity
                    val windSpeed = responseBody.wind.speed
                    val sunRise = responseBody.sys.sunrise
                    val sunSet = responseBody.sys.sunset
                    val seaLevel = responseBody.main.pressure
                    val condition = responseBody.weather.firstOrNull()?.main ?: "unknown"
                    val maxTemp = responseBody.main.temp_max
                    val minTemp = responseBody.main.temp_min

                    binding.temprature.text = "$temperature *C"
                    binding.weather.text = condition
                    binding.maxTemp.text = "Max : $maxTemp *C"
                    binding.minTemp.text = "Min : $minTemp *C"
                    binding.humidity.text = "$humidity %"
                    binding.windspeed.text = "$windSpeed m/s"
                    binding.sunrise.text = "$sunRise "
                    binding.sunset.text = "$sunSet "
                    binding.sea.text = "$seaLevel hPa"

                    Log.d("response : ", responseBody.toString())

                }
            }

            override fun onFailure(
                call: Call<WeatherApp?>, t: Throwable
            ) {
                Toast.makeText(applicationContext, "Got an Error : $t ", Toast.LENGTH_LONG).show()

//                Toast.makeText(applicationContext,"Error !! $t ", Toast.LENGTH_LONG).show()
                Log.d("Stage4", "onFailure: $t")
            }

        })
    }
}


// api = https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}

// api_key = d326688fd6b854c03eb398d041bf09ff