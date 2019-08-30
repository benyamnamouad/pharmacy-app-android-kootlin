package com.example.pharmacy

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        goToLogin.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            println(textInputNom.text.toString().trim())
            println(textInputPrenom.text.toString().trim())
            println(textInputPhone.text.toString().trim())
            println(textInputAdresse.text.toString().trim())
            println(textInputNumeroSocial.text.toString().trim())

            val call = RetrofitService.endpoint.signUp(User(
                textInputNumeroSocial.text.toString().trim(),
                textInputNom.text.toString().trim(),
                textInputPrenom.text.toString().trim(),
                textInputAdresse.text.toString().trim(),
                textInputPhone.text.toString().trim()))

            call.enqueue(object:Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    println(t.toString());
                    Toast.makeText(this@SignUpActivity," failed connecting server", Toast.LENGTH_LONG).show()            }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response?.isSuccessful!!){
                        println(response.body())
                        val responseStatus = response.body()?.get("status").toString()
                        if (responseStatus=="200"){
                            Toast.makeText(this@SignUpActivity,"user added", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this@SignUpActivity,"numero social or phone number already exists", Toast.LENGTH_LONG).show()
                        }


                    }else {
                        Toast.makeText(this@SignUpActivity,response.errorBody().toString()+" error connecting server", Toast.LENGTH_LONG).show()
                    }

                }
            })

        }
    }
}

