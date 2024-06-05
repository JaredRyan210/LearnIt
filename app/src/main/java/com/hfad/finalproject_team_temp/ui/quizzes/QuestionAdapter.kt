package com.hfad.finalproject_team_temp.ui.quizzes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hfad.finalproject_team_temp.R

class QuestionAdapter (private val questions: List<String>, answers: List<String>): RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.textQuestion)

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, QuizDetail::class.java)
                intent.putExtra("ID", questionTextView.text)

                itemView.context.startActivity(intent)

                Toast.makeText(itemView.context, "Going to QuizDetail", Toast.LENGTH_LONG)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
        holder.questionTextView.text = question
    }

    override fun getItemCount(): Int {
        return questions.size
    }

}
