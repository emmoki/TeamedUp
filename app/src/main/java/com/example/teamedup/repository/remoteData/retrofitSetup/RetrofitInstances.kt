package com.example.teamedup.repository.remoteData.retrofitSetup

import com.example.teamedup.BuildConfig.*
import com.example.teamedup.repository.remoteData.GameApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstances {
    val api: GameApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GameApi::class.java)
    }
}