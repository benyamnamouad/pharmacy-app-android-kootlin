package com.example.pharmacy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cities.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitiesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)

        var listCities:List<City> = ArrayList<City>()
        val call = RetrofitService.endpoint.getCities()
        call.enqueue(object : Callback<List<City>> {
            override fun onFailure(call: Call<List<City>>, t: Throwable) {
                Toast.makeText(this@CitiesActivity,"   failed connecting server", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                if(response?.isSuccessful!!){
                    //getting teams
                    listCities = response.body()!!
                    val listCitiesName:ArrayList<String> = ArrayList<String>()
                    for(item in listCities!!){
                        listCitiesName.add(item.nom)
                    }

                    val adapter = ArrayAdapter<String>(this@CitiesActivity, android.R.layout.simple_list_item_1, listCitiesName)
                    listViewCities.adapter= adapter

                }else {
                    Toast.makeText(this@CitiesActivity,response.errorBody().toString()+" error connecting server", Toast.LENGTH_LONG).show()
                }
            }



        })
        listViewCities.setOnItemClickListener { _, _, position, _ ->
            // 1
            val selectedCity = listCities[position]

            // 2
            val detailIntent = Intent(this, PharmaciesActivity::class.java)

            detailIntent.putExtra("nom_wilaya",selectedCity.nom!!)

                detailIntent.putExtra("id_wilaya", selectedCity.id!!.toString())

            startActivity(detailIntent)
        }


    }
}
