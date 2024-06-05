import com.google.firebase.firestore.FirebaseFirestore

class QuizFirestoreManager {
    private val db = FirebaseFirestore.getInstance()
    private val quizzesCollection = db.collection("quizzes")

    fun addQuiz(quizData: Map<String, Any>){
        quizzesCollection.add(quizData)
            .addOnSuccessListener { documentReference ->
                val documentId = documentReference.id
            }
            .addOnFailureListener{ e ->
                //stuff

            }
    }
    fun editQuiz(quizId: String, updatedQuizData: Map<String, Any>) {
        quizzesCollection.document(quizId)
            .update(updatedQuizData)
            .addOnSuccessListener {
                // Quiz updated successfully
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }

    fun deleteQuiz(quizId: String) {
        quizzesCollection.document(quizId)
            .delete()
            .addOnSuccessListener {
                // Quiz deleted successfully
            }
            .addOnFailureListener { e ->
                // Handle failure
            }
    }

/* EXAMPLE QUIZ DATA
    val quizData = mapOf(
        "title" to "Your Quiz Title",
        "questions" to listOf(
            mapOf(
                "questionText" to "Question 1",
                "answers" to listOf(
                    "Answer 1",
                    "Answer 2",
                    "Answer 3",
                    "Answer 4"
                ),
                "correctAnswerIndex" to 0 // Index of the correct answer in the "answers" list
            ),
            mapOf(
                "questionText" to "Question 2",
                "answers" to listOf(
                    "Answer 1",
                    "Answer 2",
                    "Answer 3",
                    "Answer 4"
                ),
                "correctAnswerIndex" to 1 // Index of the correct answer in the "answers" list
            ),
            // Add more questions similarly
        )
    )
 */


}


