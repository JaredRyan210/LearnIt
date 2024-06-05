package com.hfad.finalproject_team_temp.ui.quizzes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.hfad.finalproject_team_temp.R

class QuizDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_detail)

        val quizId = intent.getStringExtra("ID")
        Log.d("quiz detail", "QuizID: $quizId")
        val quizTitleView = findViewById<TextView>(R.id.textViewQuizTitle)
        val recyclerViewQuestions = findViewById<RecyclerView>(R.id.recyclerViewQuestions)

        val bEditQuiz = findViewById<Button>(R.id.bEditQuiz)
        val bTakeQuiz = findViewById<Button>(R.id.bTakeQuiz)

        val questionList = mutableListOf<String>()

        val db = FirebaseFirestore.getInstance()

        db.collection("quizzes")
            .whereEqualTo("title", quizId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val questions = document["questions"] as? List<String>
                    if (questions != null) {
                        questionList.addAll(questions)
                    }
                }

                recyclerViewQuestions.layoutManager = LinearLayoutManager(this)
                val answerList = listOf(
                    "Answer 1",
                    "Answer 2",
                    "Answer 3"
                )

                quizTitleView.text = quizId.toString()
                var questionAdapter = QuestionAdapter(questionList, answerList)
                recyclerViewQuestions.adapter = questionAdapter
                val quizIdList = listOf(quizId)

                val bBack = findViewById<Button>(R.id.bBack)
                bBack.setOnClickListener {
                    //startActivity(Intent(this, QuizzesFragment::class.java))
                    finish()
                }
                bEditQuiz.setOnClickListener {
                    val intent = Intent(this, EditQuiz::class.java)
                    intent.putExtra("quizId", quizId)
                    startActivity(intent)
                }
                bTakeQuiz.setOnClickListener {
                    val intent = Intent(this, TakeQuiz::class.java)
                    intent.putExtra("quizId", quizId)
                    startActivity(intent)
                }
            }
    }
}