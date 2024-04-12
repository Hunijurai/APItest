package com.example.apitest

import java.net.HttpURLConnection
import java.net.URL

public class ForecastAPI {
    val baseURL = "https://api.open-meteo.com/v1/forecast?"

    public fun parseCoords(coords: String): Pair<Double, Double> {
        val coordsList = coords.split(", ")
        return Pair(coordsList[0].toDouble(), coordsList[1].toDouble())
    }

    public fun getWeather(lat: Double, lon: Double,
                          temp: Boolean,
                          wind: Boolean,
                          humidity: Boolean,
                          rain: Boolean,
                          atemp: Boolean
    ): String {
        var current = "current="

        if (temp)
            current += "temperature_2m,"

        if (wind)
            current += "wind_speed_10m,"

        if (humidity)
            current += "relative_humidity_2m,"

        if (rain)
            current += "rain,"

        if (atemp)
            current += "apparent_temperature,"


        current = current.substring(0, current.length - 1)
        var url = "${baseURL}latitude=${lat}&longitude=${lon}&${current}"

        return getContentV2(url)
    }

    public fun getContentV2(url: String): String {
        val connection = URL(url).openConnection() as HttpURLConnection
        val data = connection.inputStream.bufferedReader().readText()
        return data
    }
}