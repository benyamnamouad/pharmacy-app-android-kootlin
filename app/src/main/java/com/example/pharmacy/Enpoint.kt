package com.example.pharmacy


import  retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoint {
    @GET("api/getusers")
    fun getUsers():Call<List<User>>


}