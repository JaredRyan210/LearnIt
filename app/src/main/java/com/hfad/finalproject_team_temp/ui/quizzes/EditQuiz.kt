package com.hfad.finalproject_team_temp.ui.quizzes

import com.google.firebase.firestore.DocumentSnapshot


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.hfad.finalproject_team_temp.R

class EditQuiz : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EditQuizAdapter
    private lateinit var quizId: String
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_quiz)
        quizId = intent.getStringExtra("quizId") ?: ""

        db = FirebaseFirestore.getInstance()

        setupActionBar()
        setupRecyclerView()

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
           finish()
        }
    }

    private fun setupActionBar() {
        // Enable the back button in the ActionBar/Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Handle the back button press
        finish() // Close this activity and go back
        return true
    }

    private fun setupRecyclerView() {
        db.collection("quizzes")
            .whereEqualTo("title", quizId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val questionList = mutableListOf<String>()
                val answerList = mutableListOf<String>()

                for (document in querySnapshot.documents) {
                    val questions = document["questions"] as? List<String>
                    val answers = document["answers"] as? List<String>

                    questions?.let { questionList.addAll(it) }
                    answers?.let { answerList.addAll(it) }
                }

                recyclerView = findViewById(R.id.recyclerViewEditQuiz)
                adapter = EditQuizAdapter(questionList.toMutableList(), answerList.toMutableList())
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter

                val saveButton = findViewById<Button>(R.id.saveButton)
                saveButton.setOnClickListener {
                    val updatedQuestions = adapter.getUpdatedQuestions()
                    val updatedAnswers = adapter.getUpdatedAnswers()

                    for (i in updatedQuestions.indices) {
                        adapter.updateQuestionAtPosition(i, updatedQuestions[i])
                        adapter.updateAnswerAtPosition(i, updatedAnswers[i])
                    }

                    // Update Firebase Firestore with new information
                    updateFirebase(querySnapshot.documents.firstOrNull(), updatedQuestions, updatedAnswers)

                    //adapter.notifyDataSetChanged()

                    Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateFirebase(document: DocumentSnapshot?, updatedQuestions: List<String>, updatedAnswers: List<String>) {
        document?.let {
            val quizRef = db.collection("quizzes").document(it.id)

            quizRef.update(
                mapOf(
                    "questions" to updatedQuestions,
                    "answers" to updatedAnswers
                )
            ).addOnSuccessListener {
                Log.d("EditQuiz", "Document successfully updated in Firestore!")
            }.addOnFailureListener { e ->
                Log.w("EditQuiz", "Error updating document", e)
            }
        }
    }
}
