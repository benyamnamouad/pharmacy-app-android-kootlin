package com.example.pharmacy

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_pharmacy_detail.*
import android.content.Intent
import android.net.Uri


class PharmacyDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_detail)


        nomPharmacy.text=intent.getStringExtra("nom_pharmacy")
        adressPharmacy.text=intent.getStringExtra("adresse_pharmacy")
        phoneNumber.text="+ 213 "+intent.getStringExtra("telephone_pharmacy")
        wilaya.text=intent.getStringExtra("wilaya_pharmacy")
        fromTime.text=intent.getStringExtra("ouverture_pharmacy")
        toTime.text=intent.getStringExtra("fermeture_pharmacy")



        callingButton.setOnClickListener {
            /*val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+213"+phoneNumber.text))
            startActivity(intent)*/
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:"+"0"+intent.getStringExtra("telephone_pharmacy"))
            startActivity(intent)
        }


        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val loggedIn=sharedPreferences.getBoolean("isLoggedIn",false)
        println(" User status is : "+loggedIn.toString())

    }
}
