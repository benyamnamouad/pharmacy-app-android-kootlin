package com.example.pharmacy


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        goToSignUp.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener {
            println(inputTextPassword.text.toString().trim())
            println(inputTextPhone.text.toString().trim())


            val userCredentials = JsonObject()
            userCredentials.addProperty("password",inputTextPassword.text.toString().trim())
            userCredentials.addProperty("telephone",inputTextPhone.text.toString().trim())
            print(userCredentials)

            val call = RetrofitService.endpoint.login(userCredentials)

            call.enqueue(object:Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    println(t.toString());
                    Toast.makeText(this@LoginActivity," failed connecting server", Toast.LENGTH_LONG).show()            }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response?.isSuccessful!!){
                        println(response.body())
                        val responseStatus = response.body()?.get("status").toString()
                        val numeroSocial = response.body()?.get("numero_social").toString().removeSurrounding("\"")
                        if (responseStatus=="200"){
                            Toast.makeText(this@LoginActivity,"logged in ", Toast.LENGTH_LONG).show()

                            val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("user_id",numeroSocial)
                            editor.putString("loggedin","true")
                            editor.apply()

                            if(response.body()?.get("is_first_auth").toString()=="1"){
                                val intent = Intent(applicationContext, UpdatePasswordActivity::class.java)
                                startActivity(intent)
                                finish();
                            } else {
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Toast.makeText(this@LoginActivity,"password or phone number are wrong", Toast.LENGTH_LONG).show()
                        }


                    }else {
                        Toast.makeText(this@LoginActivity,response.errorBody().toString()+" error connecting server", Toast.LENGTH_LONG).show()
                    }

                }
            })

        }
    }
}

