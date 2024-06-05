package com.hfad.finalproject_team_temp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.hfad.finalproject_team_temp.R


class ForgotPasswordFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        val emailOrUsername = view.findViewById<EditText>(R.id.textEmailOrPassword).text.toString()
        val newPassword = view.findViewById<EditText>(R.id.textNewPassword).text.toString()
        val confirmNewPassword = view.findViewById<EditText>(R.id.textConfirmPassword).text.toString()

        val submitButton = view.findViewById<Button>(R.id.bSubmit)
        val backtoLogin = view.findViewById<Button>(R.id.bBacktoLogin)



        submitButton.setOnClickListener {
            checkIfExists(emailOrUsername) { exists ->
                if (exists) {
                    if (newPassword == confirmNewPassword) {
                        updatePassword(emailOrUsername, newPassword)
                        //Toast.makeText(requireContext(), "Password Successfully Reset", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Email or Username Not Found", Toast.LENGTH_LONG).show()
                }
            }
        }
        backtoLogin.setOnClickListener{
            findNavController().navigate(R.id.action_forgotPassword_to_Login)
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun checkIfExists(emailOrUsername: String, callback: (Boolean) -> Unit) {
        db.collection("users")
            .whereEqualTo("Email", emailOrUsername)
            .get()
            .addOnCompleteListener { emailQuery ->
                if (emailQuery.isSuccessful && emailQuery.result != null && !emailQuery.result!!.isEmpty) {
                    callback.invoke(true)
                } else {
                    db.collection("users")
                        .whereEqualTo("Username", emailOrUsername)
                        .get()
                        .addOnCompleteListener { usernameQuery ->
                            if (usernameQuery.isSuccessful && usernameQuery.result != null && !usernameQuery.result!!.isEmpty) {
                                callback.invoke(true)
                            } else {
                                callback.invoke(false)
                            }
                        }
                }
            }
    }

    private fun updatePassword(emailOrUsername: String, newPassword: String) {
        db.collection("users")
            .whereEqualTo("Email", emailOrUsername)
            .get()
            .addOnSuccessListener { emailQuery ->
                if (emailQuery.documents.isNotEmpty()) {
                    val doc = emailQuery.documents[0]
                    val userId = doc.id

                    Log.d("updatePassword", "Found user by email")

                    db.collection("users")
                        .document(userId)
                        .update("Password", newPassword)
                        .addOnSuccessListener {
                            Log.d("UpdatePassword", "password updated successfully")
                            Toast.makeText(requireContext(), "Password Successfully Reset", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener { e ->
                            Log.e("updatePassword", "error updating password", e)
                        }
                } else {
                    db.collection("users")
                        .whereEqualTo("Username", emailOrUsername)
                        .get()
                        .addOnSuccessListener { usernameQuery ->
                            if (usernameQuery.documents.isNotEmpty()) {
                                val doc = usernameQuery.documents[0]
                                val userId = doc.id

                                Log.d("updatePassword", "Found user by username")
                                db.collection("users")
                                    .document(userId)
                                    .update("Password", newPassword)
                                    .addOnSuccessListener {
                                        Log.d("UpdatePassword", "password updated successfully")
                                        Toast.makeText(requireContext(), "Password Successfully Reset", Toast.LENGTH_LONG).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("updatePassword", "error updating password", e)
                                    }
                            }
                        }
                }
            }

    }
}