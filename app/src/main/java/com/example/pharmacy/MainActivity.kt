package com.example.pharmacy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val nav_login = nav_view.menu.findItem(R.id.nav_login)
        val nav_logout = nav_view.menu.findItem(R.id.nav_logout)
        val nav_signup = nav_view.menu.findItem(R.id.nav_signup)

        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val loggedin = sharedPreferences.getString("loggedin","")
        if (loggedin=="true"){
            nav_login.setVisible(false)
            nav_signup.setVisible(false)
            nav_logout.setVisible(true)
        } else {
            nav_login.setVisible(true)
            nav_signup.setVisible(true)
            nav_logout.setVisible(false)
        }



        fab.setOnClickListener { view ->
            Toast.makeText(this@MainActivity,"do something when clicking here ", Toast.LENGTH_LONG).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                println(" i im in the camera icon ")
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_login -> {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_signup -> {
                val intent = Intent(applicationContext, SignUpActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_logout -> {
                val call = RetrofitService.endpoint.logout()
                call.enqueue(object : Callback<JsonObject>{
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Toast.makeText(this@MainActivity," failed connecting server", Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        if(response?.isSuccessful!!){
                            val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()

                            val responseStatus = response.body()?.get("status").toString()
                            if (responseStatus=="200"){
                                Toast.makeText(this@MainActivity,"you are logged out ", Toast.LENGTH_LONG).show()
                                editor.putString("loggedin","false")
                                editor.apply()
                                println("alright I m out right now")

                                val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@MainActivity,"misunderstanding on the server ! just wait ", Toast.LENGTH_LONG).show()
                            }



                        }else {
                            Toast.makeText(this@MainActivity,response.errorBody().toString()+" error connecting server", Toast.LENGTH_LONG).show()
                        }
                    }



                })

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
