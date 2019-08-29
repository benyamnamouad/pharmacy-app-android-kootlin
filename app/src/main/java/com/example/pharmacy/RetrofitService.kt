package com.example.pharmacy

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitService {
    val endpoint :Endpoint by lazy{
        Retrofit.Builder().baseUrl("http://192.168.8.104:3000/").
            addConverterFactory(GsonConverterFactory.create()).build().create(Endpoint::class.java)
    }
}