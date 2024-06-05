package com.hfad.finalproject_team_temp.ui.quizzes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.hfad.finalproject_team_temp.R

class EditQuizAdapter(
    var questionList: MutableList<String>,
    var answerList: MutableList<String>
) : RecyclerView.Adapter<EditQuizAdapter.EditQuizViewHolder>() {

    // ViewHolder to hold each row view
    class EditQuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val editTextQuestion: EditText = itemView.findViewById(R.id.editTextQuestion)
        val editTextAnswer: EditText = itemView.findViewById(R.id.editTextAnswer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditQuizViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_edit_question, parent, false)
        return EditQuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditQuizViewHolder, position: Int) {
        if (position == 0) {
            holder.editTextQuestion.isEnabled = false
            holder.editTextAnswer.isEnabled = false
            holder.editTextQuestion.setText("Questions")
            holder.editTextAnswer.setText("Answers")
        } else {
            holder.editTextQuestion.isEnabled = true
            holder.editTextAnswer.isEnabled = true
            holder.editTextQuestion.setText(questionList[position - 1])
            holder.editTextAnswer.setText(answerList[position - 1])

            // Save the edited data when text changes
            holder.editTextQuestion.addTextChangedListener {
                questionList[position - 1] = it.toString()
            }

            holder.editTextAnswer.addTextChangedListener {
                answerList[position - 1] = it.toString()
            }
        }
    }

    override fun getItemCount(): Int = questionList.size + 1

    // Function to retrieve updated question list
    fun getUpdatedQuestions(): MutableList<String> {
        return questionList
    }

    fun updateQuestionAtPosition(position: Int, updatedQuestion: String) {
        if (position >= 0 && position < questionList.size) {
            questionList[position] = updatedQuestion
            notifyItemChanged(position + 1) // +1 for the header
        }
    }

    fun updateAnswerAtPosition(position: Int, updatedAnswer: String) {
        if (position >= 0 && position < answerList.size) {
            answerList[position] = updatedAnswer
            notifyItemChanged(position + 1) // +1 for the header
        }
    }

    // Function to retrieve updated answer list
    fun getUpdatedAnswers(): List<String> {
        return answerList
    }
}
