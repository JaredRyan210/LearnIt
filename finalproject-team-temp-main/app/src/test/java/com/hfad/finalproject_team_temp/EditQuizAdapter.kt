package com.hfad.finalproject_team_temp

import android.view.LayoutInflater
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.mockito.Mockito.mock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.hfad.finalproject_team_temp.ui.quizzes.EditQuizAdapter
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertEquals


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28]) // Choose an appropriate Android SDK version
class EditQuizAdapterTest {

    private lateinit var editQuizAdapter: EditQuizAdapter

    // Mocking RecyclerView
    private val recyclerViewMock = mock(RecyclerView::class.java)

    @Before
    fun setUp() {
        // Initialize EditQuizAdapter with mock data and the mocked RecyclerView
        val questionList = mutableListOf("Question1", "Question2")
        val answerList = mutableListOf("Answer1", "Answer2")
        editQuizAdapter = EditQuizAdapter(questionList, answerList)

        // Set the mocked RecyclerView in the adapter
        editQuizAdapter.onAttachedToRecyclerView(recyclerViewMock)
    }

    @Test
    fun getItemCount_ReturnsCorrectCount() {
        val expectedCount = editQuizAdapter.itemCount
        assertEquals(expectedCount, 3) // Header + Question1 + Question2
    }
    @Test
    fun getItemCount_ReturnsIncorrectCount() {
        val expectedCount = editQuizAdapter.itemCount
        assertEquals(expectedCount, 2) // Header + Question1 + Question2
    }

    /*@Test
    fun getUpdatedAnswers_ReturnsCorrect() {
        val
    }
    fun getUpdatedAnswers(): List<String> {
        return answerList
    }*/

    @Test
    fun updateQuestionAtPosition_MovesQuestionToCorrectPosition() {
        // Arrange
        val initialPosition = 0
        val newPosition = 2
        val initialQuestion = "InitialQuestion"
        val updatedQuestion = "UpdatedQuestion"

        // Set up the initial state of the adapter
        editQuizAdapter.questionList = mutableListOf(initialQuestion, "Question1", "Question2")

        //Update
        editQuizAdapter.updateQuestionAtPosition(initialPosition, updatedQuestion)

        // Assert
        assertEquals(updatedQuestion, editQuizAdapter.questionList[0])
    }

    @Test
    fun updateAnswerAtPosition_MovesAnswerToCorrectPosition() {
        // Arrange
        val initialPosition = 0
        val newPosition = 1
        val initialAnswer = "InitialAnswer"
        val updatedAnswer = "UpdatedAnswer"

        // Set up the initial state of the adapter
        editQuizAdapter.questionList = mutableListOf(initialAnswer, "Question1", "Question2")

        //Update
        editQuizAdapter.updateQuestionAtPosition(newPosition, updatedAnswer)

        // Assert
        assertEquals(updatedAnswer, editQuizAdapter.questionList[1])
    }

    @Test
    fun updateAnswerAtPosition_MovesAnswerToIncorrectPosition() {
        // Arrange
        val newPos = 3;
        val initialAnswer = "InitialAnswer"
        val updatedAnswer = "UpdatedAnswer"

        // Set up the initial state of the adapter
        editQuizAdapter.questionList = mutableListOf("Question1", initialAnswer,"Question2")

        //Update
        editQuizAdapter.updateQuestionAtPosition(newPos, updatedAnswer)

        // Assert
        assertEquals(updatedAnswer, editQuizAdapter.questionList[3])
    }

    @Test
    fun updateQuestionAtPosition_MovesQuestionToIncorrectPosition() {
        // Arrange
        val newPos = 3;
        val question = "Question4"

        // Set up the initial state of the adapter
        editQuizAdapter.questionList = mutableListOf("Question1", "Question2", "Question3")

        //Update
        editQuizAdapter.updateQuestionAtPosition(newPos, question)

        // Assert
        assertEquals(question, editQuizAdapter.questionList[3])
    }


}
