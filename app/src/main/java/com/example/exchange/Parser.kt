package com.example.exchange

import android.util.Log
import org.json.JSONObject

class Parser {
    fun parse(jsonData: String): List<String> {
        val currencies = mutableListOf<String>()
        try {
            val jsonObject = JSONObject(jsonData)
            val conversionRates = jsonObject.getJSONObject("conversion_rates")

            conversionRates.keys().forEach { currencyCode ->
                val rate = conversionRates.getDouble(currencyCode)
                currencies.add("$currencyCode - $rate")
            }

            Log.d("Parser", "Parsed Data: $currencies")
        } catch (e: Exception) {
            Log.e("Parser", "Error parsing JSON data: ${e.message}", e)
        }
        return currencies
    }
}