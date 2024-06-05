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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hfad.finalproject_team_temp.R
import com.hfad.finalproject_team_temp.ui.dashboard.DashboardFragment

class LoginFragment : Fragment() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val usersCollection = FirebaseFirestore.getInstance().collection("users")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_login, container,false)

        val login = view.findViewById<Button>(R.id.bLogin)
        val forgotPassButton = view.findViewById<Button>(R.id.bForgotPassword)

        login.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.TextEmail).text.toString()
            val password = view.findViewById<EditText>(R.id.TextPassword).text.toString()

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if(task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        fetchUsername(user?.uid)
                    } else {
                        Toast.makeText(requireContext(), "Authentication Failed", Toast.LENGTH_LONG).show()
                    }
                }
        }

        forgotPassButton.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_forgotPasswordFragment)
        }

        return view
    }

    private fun fetchUsername(uid: String?) {
        if (uid != null) {
            usersCollection.document(uid).get()
                .addOnSuccessListener { documentSnapshot ->
                    val username = documentSnapshot.getString("Username")
                    if (username != null) {
                        navigateToDashboard(username)
                    } else {
                        Toast.makeText(requireContext(), "Username Not Found", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error fetching username: $e", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun navigateToDashboard(username: String) {
        Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_LONG).show()
        val fragmentManager = parentFragmentManager
        val bundle = Bundle().apply {
            putString("username", username)
        }
        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment, bundle)
    }
}