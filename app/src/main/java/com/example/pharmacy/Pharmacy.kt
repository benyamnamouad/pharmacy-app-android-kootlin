package com.example.pharmacy

import java.sql.Time

data class Pharmacy (
    var id:Int,
    var nom:String,
    var adresse:String,
    var ouverture:String,
    var fermeture:String,
    var telephone:Int,
    var facebook_page:String,
    var lat:Float,
    var lng:Float,
    var wilaya_id:Int,
    var owner_id:Int
)