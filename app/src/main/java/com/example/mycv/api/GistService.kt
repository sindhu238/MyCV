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

interface GistService {
    @GET("/sindhu238/{gistId}/raw/53647205cba8d3dc1614bc7819751f5ae3bf2c59/CVData.json")
    fun getCVInfoAsync(@Path("gistId") gistId: String): Deferred<Response<CVInfo>>
}