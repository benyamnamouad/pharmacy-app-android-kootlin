package com.example.pharmacy_app

import com.twilio.rest.chat.v1.service.User
import  retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoint {
    @GET("getusers")
    fun getUsers():Call<List<User>>


}