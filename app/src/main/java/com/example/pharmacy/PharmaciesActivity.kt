package com.example.pharmacy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cities.*
import kotlinx.android.synthetic.main.activity_pharmacies.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PharmaciesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacies)

        var listPharmacies:List<Pharmacy> = ArrayList<Pharmacy>()

        var wilayaId:String = intent.getStringExtra("id_wilaya")
        print("id is "+wilayaId)
        val call = RetrofitService.endpoint.getPharmaciesByCity(wilayaId!!)
        call.enqueue(object : Callback<List<Pharmacy>> {
            override fun onFailure(call: Call<List<Pharmacy>>, t: Throwable) {
                Toast.makeText(this@PharmaciesActivity,"   failed connecting server", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Pharmacy>>, response: Response<List<Pharmacy>>) {
                if(response?.isSuccessful!!){
                    //getting teams
                    listPharmacies = response.body()!!

                    val adapter = PharmaciesListAdapter(this@PharmaciesActivity, listPharmacies as ArrayList<Pharmacy>)
                    listViewPharmacies.adapter= adapter

                }else {
                    Toast.makeText(this@PharmaciesActivity,response.errorBody().toString()+" error connecting server", Toast.LENGTH_LONG).show()
                }
            }



        })
        listViewPharmacies.setOnItemClickListener { _, _, position, _ ->
            // 1
            val selectedPharmacy = listPharmacies[position]

            // 2
            val detailIntent = Intent(this, PharmacyDetailActivity::class.java)
            detailIntent.putExtra("nom_pharmacy",selectedPharmacy.nom!!)
            detailIntent.putExtra("id_pharmacy",selectedPharmacy.id!!)

            // 3
            startActivity(detailIntent)
        }



    }
}
