package com.example.pharmacy_app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitService {
    val endpoint :Endpoint by lazy{
        Retrofit.Builder().baseUrl("http://localhost:3000").
            addConverterFactory(GsonConverterFactory.create()).build().create(Endpoint::class.java)
    }
}