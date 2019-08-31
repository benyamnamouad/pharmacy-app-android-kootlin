package com.example.pharmacy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.JsonObject

import kotlinx.android.synthetic.main.activity_update_pasword.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpdatePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pasword)


        confirmPassword.setOnClickListener {


            val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getString("user_id","")
            println("printing the user is from the update password acitivyt $userId")

            val userCredentials = JsonObject()


            userCredentials.addProperty("user_id",userId.trim())
            userCredentials.addProperty("password",textInputPassword.text.toString().trim())
            userCredentials.addProperty("c_password",textInputConfirmPassword.text.toString().trim())
            println("these are the user credentials: $userCredentials")

            val call = RetrofitService.endpoint.updatePassword(userCredentials)

            call.enqueue(object:Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    println(t.toString());
                    Toast.makeText(this@UpdatePasswordActivity," failed connecting server", Toast.LENGTH_LONG).show()            }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response?.isSuccessful!!){
                        println("this is the query response: ${response.body()}")
                        val responseStatus = response.body()?.get("status").toString()
                        if (responseStatus=="200"){
                            Toast.makeText(this@UpdatePasswordActivity,"password updated ", Toast.LENGTH_LONG).show()
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@UpdatePasswordActivity,"passwords don't match", Toast.LENGTH_LONG).show()
                        }


                    }else {
                        println(response.errorBody())
                        Toast.makeText(this@UpdatePasswordActivity,response.errorBody().toString()+" error connecting server", Toast.LENGTH_LONG).show()
                    }

                }
            })
        }
    }
}