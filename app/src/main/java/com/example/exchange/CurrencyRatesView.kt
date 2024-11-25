package com.example.exchange

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CurrencyRatesView : ViewModel() {
    private val _filteredCurrencies = MutableStateFlow<List<String>>(emptyList())
    val filteredCurrencies: StateFlow<List<String>> = _filteredCurrencies

    fun loadCurrencyData() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("CurrencyRatesView", "Loading currency data...")
            val dataLoader = DataLoader()
            val jsonData = dataLoader.downloadData("https://v6.exchangerate-api.com/v6/2520539b8136aa8fb49162b7/latest/USD")
            if (!jsonData.isNullOrEmpty()) {
                val parser = Parser()
                val parsedData = parser.parse(jsonData)
                Log.d("CurrencyRatesView", "Data loaded successfully: $parsedData")
                _filteredCurrencies.value = parsedData
            } else {
                Log.e("CurrencyRatesView", "No data fetched from API")
            }
        }
    }

    fun filterCurrencies(query: String) {
        val currentData = _filteredCurrencies.value
        _filteredCurrencies.value = if (query.isBlank()) {
            currentData
        } else {
            currentData.filter { it.contains(query, ignoreCase = true) }
        }
        Log.d("CurrencyRatesView", "Filtered data: ${_filteredCurrencies.value}")
    }
}
