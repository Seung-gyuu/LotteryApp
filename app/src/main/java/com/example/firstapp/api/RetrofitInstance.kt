package com.example.firstapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//앱안에서 하나만 만들 수 있음 -obj
object RetrofitInstance {
    private const val BASE_URL = "https://www.dhlottery.co.kr/"
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: LottoApi by lazy { retrofit.create(LottoApi::class.java) }
}
