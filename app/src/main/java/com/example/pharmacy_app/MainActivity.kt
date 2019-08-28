package com.example.pharmacy_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.ArrayAdapter
import com.twilio.rest.chat.v1.service.User
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var listTeam:List<User> = ArrayList<User>()
        val call = RetrofitService.endpoint.getUsers()

        call.enqueue(object : Callback<List<User>>{

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                println(t.toString());
                Toast.makeText(this@MainActivity," failed connecting server", Toast.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response?.isSuccessful!!){
                    //getting teams
                    listTeam = response.body()!!
                    println(listTeam);

//                    val listTeamName:ArrayList<String> = ArrayList<String>()
//                    for(item in listTeam!!){
//                        listTeamName.add(item.team_name)
//                    }
//
//                    val adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, listTeamName)
//                    listViewTeams.adapter=adapter

                }

                else {
                    Toast.makeText(this@MainActivity,response.errorBody().toString()+" error connecting server", Toast.LENGTH_LONG).show()
                }
            }



        })
}
}
