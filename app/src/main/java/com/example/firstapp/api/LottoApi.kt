package com.example.firstapp.api


import com.example.firstapp.data.LottoModel
import retrofit2.http.GET
import retrofit2.http.Query

interface LottoApi{
    @GET("common.do")
    suspend fun getLottoNumber(
        @Query("drwNo") num: Int,
        @Query("method") method: String = "getLottoNumber"
    ): LottoModel
}