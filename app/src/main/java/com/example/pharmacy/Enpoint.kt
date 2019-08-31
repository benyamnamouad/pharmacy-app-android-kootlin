package com.example.pharmacy


import android.net.wifi.hotspot2.pps.Credential
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import  retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoint {
    @GET("api/getusers")
    fun getUsers():Call<List<User>>
    @POST("api/signup")
    fun signUp(@Body user:User):Call<JsonObject>

    @POST("api/login")
    fun login(@Body userCredential:JsonObject):Call<JsonObject>

    @GET("/api/getwilayas")
    fun getCities():Call<List<City>>

}