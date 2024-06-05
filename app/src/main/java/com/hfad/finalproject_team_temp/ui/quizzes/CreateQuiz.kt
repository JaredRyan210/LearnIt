package com.hfad.finalproject_team_temp.ui.quizzes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.firebase.firestore.FirebaseFirestore
import com.hfad.finalproject_team_temp.R
import com.hfad.finalproject_team_temp.databinding.ActivityCreateQuizBinding
import com.hfad.finalproject_team_temp.quizClass
import com.hfad.finalproject_team_temp.ui.home.HomeFragment

class CreateQuiz : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCreateQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.grey)
        binding = ActivityCreateQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val fragment = QuizzesFragment() // Replace with your fragment class
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.nav_gallery, fragment)
//            .addToBackStack(null) // Add the fragment to the back stack
//            .commit()

        var questionList = arrayListOf<String>()
        var answerList = arrayListOf<String>()
        val addButton = findViewById<Button>(R.id.addQuestion)
        val doneButton = findViewById<Button>(R.id.doneButton)
        addButton.setOnClickListener{
            val title = findViewById<EditText>(R.id.Title).text.toString()
            val question = findViewById<EditText>(R.id.Question).text.toString()
            val answer = findViewById<EditText>(R.id.Answer).text.toString()
            questionList.add(question)
            answerList.add(answer)
            Toast.makeText(this, "Question Added!", Toast.LENGTH_LONG).show()
            findViewById<EditText>(R.id.Question).setText("")
            findViewById<EditText>(R.id.Answer).setText("")

        }

        doneButton.setOnClickListener{
            val title = findViewById<EditText>(R.id.Title).text.toString()
            val db = FirebaseFirestore.getInstance()
            val quiz: MutableMap<String, Any> = HashMap()


            quiz["title"] = title
            quiz["questions"] = questionList
            quiz["answers"] = answerList

            db.collection("quizzes")
                .add(quiz)
                .addOnSuccessListener {
                    Log.d("dbfirebase", "save: ${quiz}")
                }
                .addOnFailureListener{
                    Log.d("dbfirebase Failed", "${quiz}")
                }
            db.collection("quizzes")
                .get()
                .addOnCompleteListener{
                    val result: StringBuffer = StringBuffer()
                    if(it.isSuccessful) {
                        for(document in it.result!!) {
                            Log.d("dbfirebase", "retrive:" +
                                    "${document.data.getValue("Title")} " +
                                    "${document.data.getValue("questions")} " +
                                    "${document.data.getValue("answers")}")
                        }
                    }
                    Toast.makeText(this, "Quiz Successfully Saved", Toast.LENGTH_LONG).show()

                }
            //need to add quizzes to firebase, currently this just sends them to the next activity
            val intent = Intent(this, HomeFragment::class.java)
            this.startActivity(intent)
            val newQuiz = quizClass(title, questionList, answerList)
            Toast.makeText(this, "Going to back to home page!", Toast.LENGTH_LONG).show()


        }
        val backButton = findViewById<Button>(R.id.backButton) // Replace with your back button ID
        backButton.setOnClickListener {
            onBackPressed() // This will simulate the back button press
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_create_quiz)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}