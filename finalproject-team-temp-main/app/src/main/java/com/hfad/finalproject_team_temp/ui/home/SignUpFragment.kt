package com.hfad.finalproject_team_temp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.hfad.finalproject_team_temp.R

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_sign_up, container, false)

        //val BacktoHome = view.findViewById<Button>(R.id.bBacktoHome)

        view.findViewById<Button>(R.id.bCreateAcc).setOnClickListener {

            val email = view.findViewById<EditText>(R.id.TextEmail).text.toString()
            val password = view.findViewById<EditText>(R.id.TextPassword).text.toString()
            val passwordConfirm = view.findViewById<EditText>(R.id.TextConfirmPassword).text.toString()
            val fName = view.findViewById<EditText>(R.id.fName).text.toString()
            val lName = view.findViewById<EditText>(R.id.lName).text.toString()
            val username = view.findViewById<EditText>(R.id.TextUsername).text.toString()

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

                        findNavController().navigate(R.id.nav_home)

                        Toast.makeText(requireContext(), "Account Successfully Created", Toast.LENGTH_LONG).show()


                    }
            } else {
                Toast.makeText(requireContext(), "Passwords do not Match", Toast.LENGTH_LONG).show()
            }
        }
        /*
        BacktoHome.setOnClickListener {
            startActivity(Intent(requireContext(), HomeFragment::class.java))
        }
        */
        return view
    }
}