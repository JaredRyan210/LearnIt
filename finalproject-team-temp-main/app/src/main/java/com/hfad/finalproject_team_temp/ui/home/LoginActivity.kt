package com.hfad.finalproject_team_temp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.hfad.finalproject_team_temp.R
import com.hfad.finalproject_team_temp.ui.dashboard.DashboardFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //val BacktoHome = findViewById<Button>(R.id.bBacktoHome)
        val login = findViewById<Button>(R.id.bLogin)

        val db = FirebaseFirestore.getInstance()
        val users = db.collection("users")

        login.setOnClickListener{
            val email = findViewById<EditText>(R.id.TextEmail).text.toString()
            val password = findViewById<EditText>(R.id.TextPassword).text.toString()

            users.whereEqualTo("Email", email).get() //search database for user with matching email
                .addOnSuccessListener { queryResult ->
                    if(queryResult.isEmpty == false){ //check if the query was empty or not
                        val doc = queryResult.documents[0] //if not empty then grab the first doc from the query
                        val pass = doc.getString("Password") //get password from the database

                        if(password == pass){ //check to see if entered password matches the one in database
                            Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show()
                            val fragmentManager = supportFragmentManager

                            fragmentManager.beginTransaction()
                                .replace(R.id.nav_host_fragment_content_main, DashboardFragment())
                                .addToBackStack(null)
                                .commit()
                        }
                        else{
                            Toast.makeText(this, "Password does not match!", Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
                        Toast.makeText(this, "Email not found in database!!", Toast.LENGTH_LONG).show()
                    }
                }
        }
        /*
        BacktoHome.setOnClickListener{
            startActivity(Intent(this, HomeFragment::class.java))
        }

         */
    }
}