package com.example.exchange

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DataLoader {
    fun downloadData(urlString: String): String? {
        return try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            val result = reader.use { it.readText() }

            Log.d("DataLoader", "API Response: $result")
            result
        } catch (e: Exception) {
            Log.e("DataLoader", "Error fetching data: ${e.message}", e)
            null
        }
    }
}
