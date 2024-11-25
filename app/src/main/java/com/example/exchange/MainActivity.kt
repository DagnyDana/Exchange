package com.example.exchange

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.lifecycle.viewmodel.compose.viewModel
import android.util.Log
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyRatesApp()
        }
    }
}

@Composable
fun CurrencyRatesApp(viewModel: CurrencyRatesView = viewModel()) {
    val currencyList by viewModel.filteredCurrencies.collectAsState()
    Log.d("CurrencyRatesApp", "UI Currency List: $currencyList")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        var searchText by remember { mutableStateOf("") }

        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                viewModel.filterCurrencies(searchText)
            },
            label = { Text("Search for Currency") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        if (currencyList.isEmpty()) {
            Text("No data available.", modifier = Modifier.fillMaxSize().wrapContentSize())
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(currencyList) { currency ->
                    Text(text = currency, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadCurrencyData()
    }
}
