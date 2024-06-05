package com.hfad.finalproject_team_temp.ui.home

import android.content.ComponentCallbacks
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
import com.google.firebase.auth.FirebaseAuth
import com.hfad.finalproject_team_temp.R

class SignUpFragment : Fragment() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_sign_up, container, false)

        view.findViewById<Button>(R.id.bCreateAcc).setOnClickListener {

            val email = view.findViewById<EditText>(R.id.TextEmail).text.toString()
            val password = view.findViewById<EditText>(R.id.TextPassword).text.toString()
            val passwordConfirm = view.findViewById<EditText>(R.id.TextConfirmPassword).text.toString()
            val fName = view.findViewById<EditText>(R.id.fName).text.toString()
            val lName = view.findViewById<EditText>(R.id.lName).text.toString()
            val username = view.findViewById<EditText>(R.id.TextUsername).text.toString()

            if (password == passwordConfirm) {

                checkExistingUser(email, username) { doesNotExist ->
                    if (doesNotExist) {
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(requireActivity()) { task ->
                                if (task.isSuccessful) {
                                    val user = firebaseAuth.currentUser
                                    val db = FirebaseFirestore.getInstance()
                                    val userData: MutableMap<String, Any> = HashMap()

                                    userData["Email"] = email
                                    userData["First Name"] = fName
                                    userData["Last Name"] = lName
                                    userData["Username"] = username

                                    db.collection("users")
                                        .document(user!!.uid)
                                        .set(userData)
                                        .addOnSuccessListener {
                                            Log.d("dbfirebase", "save: ${user}")
                                            findNavController().navigate(R.id.nav_home)
                                            Toast.makeText(requireContext(), "Account created successfully", Toast.LENGTH_LONG).show()
                                        }
                                        .addOnFailureListener{
                                            Log.d("dbfirebase Failed", "${user}")
                                        }
                                } else {
                                    Toast.makeText(requireContext(), "Authentication Failed", Toast.LENGTH_LONG).show()
                                }
                            }
                    }  else {
                        Toast.makeText(requireContext(), "Username or Email already exist", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Passwords do not Match", Toast.LENGTH_LONG).show()

            }
        }
        return view
    }

    private fun checkExistingUser(email: String, username: String, callback: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .whereEqualTo("Email", email)
            .get()
            .addOnCompleteListener { emailQuery ->
                if (emailQuery.isSuccessful && emailQuery.result != null && !emailQuery.result!!.isEmpty) {
                    callback.invoke(false)
                } else {
                    db.collection("users")
                        .whereEqualTo("Username", username)
                        .get()
                        .addOnCompleteListener { usernameQuery ->
                            if (usernameQuery.isSuccessful && usernameQuery.result != null && !usernameQuery.result!!.isEmpty) {
                                callback.invoke(false)
                            } else {
                                callback.invoke(true)
                            }
                        }
                }
            }
    }
}