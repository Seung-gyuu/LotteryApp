package com.example.firstapp

import android.util.Log
import com.example.firstapp.api.LottoApi
import com.example.firstapp.api.RetrofitInstance
import com.example.firstapp.data.LottoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "LottoController"

class LottoController(private val service: LottoApi = RetrofitInstance.api) {
    fun getLottoNumber(num: Int, onSuccess: (LottoData) -> Unit, onError: (String) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            runCatching {
                val result = withContext(Dispatchers.IO) { service.getLottoNumber(num = num) }
                onSuccess(result.toLottoData())
            }.onFailure {
                Log.e(TAG, "getLottoNumber() failed! : ${it.message}")
                onError(it.message ?: "Unknown error")
            }
        }
    }
}