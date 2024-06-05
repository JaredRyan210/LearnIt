package com.hfad.finalproject_team_temp.ui.quizzes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.hfad.finalproject_team_temp.R
import com.hfad.finalproject_team_temp.ui.dashboard.DashboardViewModel

class TakeQuiz : AppCompatActivity() {

    private lateinit var quizId: String
    private lateinit var dashboardViewModel: DashboardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.grey)
        setContentView(R.layout.activity_take_quiz)
        quizId = intent.getStringExtra("quizId") ?: ""
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        val backButton = findViewById<Button>(R.id.bBack)
        backButton.setOnClickListener {
            onBackPressed()
        }
        val savedUsername = dashboardViewModel.loadUsername(this) // obtain username of currently logged in user

        val nextButton = findViewById<Button>(R.id.bNext)
        val db = FirebaseFirestore.getInstance()
        val questionList = mutableListOf<String>()
        val answerList = mutableListOf<String>()
        val title: TextView = findViewById(R.id.txtQuizTitle)
        val question: TextView = findViewById(R.id.txtQuestion)
        val answerVisibility: TextView = findViewById(R.id.txtAnswer)

        var incrementer = 0;
        var totalCorrect = 0;

        answerVisibility.visibility = View.INVISIBLE

        db.collection("quizzes")
            .whereEqualTo("title", quizId)
            .get()
            .addOnCompleteListener {
                    for (document in it.result!!) {
                        val questions = document["questions"] as? List<String>
                        val answers = document["answers"] as? List<String>

                        questions?.let { questionList.addAll(it) }
                        answers?.let { answerList.addAll(it) }
                        val questionSize = questionList.size
                        nextButton.setOnClickListener {
                            if (incrementer < questionSize) {
                                answerVisibility.visibility = View.VISIBLE
                                var questionNumber = incrementer + 1
                                var helper1 = questionList[incrementer]
                                question.text = "Question $questionNumber/$questionSize: $helper1"
                                var answer = findViewById<EditText>(R.id.txtAnswer).text.toString()
                                if(incrementer > 0) {
                                    if (answer == answerList[incrementer - 1]) {
                                        totalCorrect++
                                    }
                                }
                                incrementer++
                                findViewById<EditText>(R.id.txtAnswer).setText("")
                            }
                            else {
                                var answer = findViewById<EditText>(R.id.txtAnswer).text.toString()
                                if(answer == answerList[incrementer - 1]) totalCorrect++
                                findViewById<EditText>(R.id.txtAnswer).setText("")
                                Toast.makeText(this, "You got a $totalCorrect/$questionSize!", Toast.LENGTH_LONG).show()
                                question.visibility = View.GONE
                                answerVisibility.visibility = View.GONE
                                nextButton.visibility = View.GONE
                                backButton.text = "Finish"

                                val correct = totalCorrect.toDouble()
                                val numQuestions = questionSize.toDouble()
                                val score = correct / numQuestions
                                val scoreEntry: MutableMap<String, Any> = HashMap()
                                scoreEntry["Score"] = score
                                scoreEntry["quizID"] = quizId
                                scoreEntry["username"] = savedUsername ?: "default"
                                db.collection("quizScores")
                                    .add(scoreEntry)
                            }
                        }
                    }
            }
        title.text = quizId

    }
}