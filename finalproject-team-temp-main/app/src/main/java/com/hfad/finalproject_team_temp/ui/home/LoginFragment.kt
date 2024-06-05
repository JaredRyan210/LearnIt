package com.hfad.finalproject_team_temp.ui.home

import android.content.Intent
import android.os.Bundle
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
import com.hfad.finalproject_team_temp.ui.dashboard.DashboardFragment

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_login, container,false)

        //val BacktoHome = view.findViewById<Button>(R.id.bBacktoHome)
        val login = view.findViewById<Button>(R.id.bLogin)

        val db = FirebaseFirestore.getInstance()
        val users = db.collection("users")

        login.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.TextEmail).text.toString()
            val password = view.findViewById<EditText>(R.id.TextPassword).text.toString()

            users.whereEqualTo("Email", email).get()
                .addOnSuccessListener { queryResult ->
                    if (queryResult.isEmpty == false) {
                        val doc = queryResult.documents[0]
                        val pass = doc.getString("Password")

                        if (password == pass) {
                            Toast.makeText(requireContext(), "Success!", Toast.LENGTH_LONG).show()
                            val fragmentManager = parentFragmentManager

                            val username = doc.getString("Username")
                            val bundle = Bundle().apply {
                                putString("username", username)
                            }

                            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment, bundle)
                        } else {
                            Toast.makeText(requireContext(), "Password does not match!", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Email not found", Toast.LENGTH_LONG).show()
                    }
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