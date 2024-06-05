package com.hfad.finalproject_team_temp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.hfad.finalproject_team_temp.R
import android.util.Log

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //val BacktoHome = findViewById<Button>(R.id.bBacktoHome)

        findViewById<Button>(R.id.bCreateAcc).setOnClickListener {

            val email = findViewById<EditText>(R.id.TextEmail).text.toString()
            val password = findViewById<EditText>(R.id.TextPassword).text.toString()
            val passwordConfirm = findViewById<EditText>(R.id.TextConfirmPassword).text.toString()
            val fName = findViewById<EditText>(R.id.fName).text.toString()
            val lName = findViewById<EditText>(R.id.lName).text.toString()
            val username = findViewById<EditText>(R.id.TextUsername).text.toString()

            if (password == passwordConfirm) {
                val db = FirebaseFirestore.getInstance()
                val user: MutableMap<String, Any> = HashMap()

                user["Email"] = email
                user["Password"] = password
                user["First Name"] = fName
                user["Last Name"] = lName
                user["Username"] = username

                db.collection("users")
                    .add(user)
                    .addOnSuccessListener {
                        Log.d("dbfirebase", "save: ${user}")
                    }
                    .addOnFailureListener{
                        Log.d("dbfirebase Failed", "${user}")
                    }
                db.collection("users")
                    .get()
                    .addOnCompleteListener{
                        val result: StringBuffer = StringBuffer()
                        if(it.isSuccessful) {
                            for(document in it.result!!) {
                                Log.d("dbfirebase", "retrive:" +
                                        "${document.data.getValue("Email")} " +
                                        "${document.data.getValue("Password")} " +
                                        "${document.data.getValue("Name")}")
                            }
                        }
                        Toast.makeText(this, "Account Successfully Created", Toast.LENGTH_LONG).show()

                    }
            } else {
                Toast.makeText(this, "Passwords do not Match", Toast.LENGTH_LONG).show()
            }
        }
        /*
        BacktoHome.setOnClickListener{
            startActivity(Intent(this, HomeFragment::class.java))
        }
        */
    }
}