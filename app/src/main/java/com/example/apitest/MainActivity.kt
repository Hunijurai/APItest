package com.example.apitest


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import org.json.JSONObject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edCoords = findViewById<EditText>(R.id.edCoords)
        val tAnswer = findViewById<EditText>(R.id.tAnswer)
        val chTemperature = findViewById<CheckBox>(R.id.chTemperature)
        val chWindSpeed = findViewById<CheckBox>(R.id.chWindSpeed)
        val chHumidity = findViewById<CheckBox>(R.id.chHumidity)
        val chRain = findViewById<CheckBox>(R.id.chRain)
        val chApparentT = findViewById<CheckBox>(R.id.chApparentT)
        val btnGetWeather = findViewById<Button>(R.id.btnGetWeather)
        val theme_btn = findViewById<ImageButton>(R.id.theme_btn)
        val mainCL = findViewById<ConstraintLayout>(R.id.main_CL)

        var x: Boolean = true
        theme_btn.setOnClickListener {
            if (x) {
                theme_btn.setImageResource(R.drawable.light)
                mainCL.setBackgroundResource(R.drawable.dark_theme)
            } else {
                theme_btn.setImageResource(R.drawable.dark)
                mainCL.setBackgroundResource(R.drawable.light_theme)
            }
            x = !x
        }
        btnGetWeather.setOnClickListener {
            val api = ForecastAPI()
            val coords = api.parseCoords(edCoords.text.toString())
            thread {
                val text = api.getWeather(coords.first, coords.second,
                    chTemperature.isChecked,
                    chWindSpeed.isChecked,
                    chHumidity.isChecked,
                    chRain.isChecked,
                    chApparentT.isChecked
                )
                var Parsed_text = ""
                val json = JSONObject(text)
                val current = json.getJSONObject("current")

                if (chTemperature.isChecked) {
                    val temp = current.getDouble("temperature_2m")
                    Parsed_text += "Temperature: $temp\n"}
                if (chWindSpeed.isChecked) {
                    val windSpeed = current.getDouble("wind_speed_10m")
                    Parsed_text += "Wind speed: $windSpeed\n"}
                if (chHumidity.isChecked) {
                    val humidity = current.getDouble("relative_humidity_2m")
                    Parsed_text += "Humidity: $humidity\n"}
                if (chRain.isChecked) {
                    val rain = current.getDouble("rain")
                    Parsed_text += "Rain: $rain\n"}
                if (chApparentT.isChecked) {
                    val atemp = current.getDouble("apparent_temperature")
                    Parsed_text += "Apparent temperature: $atemp"}


                runOnUiThread {
                    tAnswer.setText(Parsed_text)
                }


            }
        }


    }
}