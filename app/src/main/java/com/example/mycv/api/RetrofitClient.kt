package com.example.mycv.api

import com.example.mycv.models.CVInfo
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Deferred
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

object RetrofitClient {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl("https://gist.githubusercontent.com")
                .build()
    }
}

interface GistApi {
    @GET("/sindhu238/{gistId}/raw/02678573d4d85bb4c42528b429e0686193899f25/CVData.json")
    fun getCVInfoAsync(@Path("gistId") gistId: String): Deferred<Response<CVInfo>>
}