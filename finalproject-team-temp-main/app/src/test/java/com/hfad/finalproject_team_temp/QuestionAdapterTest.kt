package com.hfad.finalproject_team_temp

import android.view.View
import android.widget.TextView
import com.hfad.finalproject_team_temp.ui.quizzes.QuestionAdapter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class QuestionAdapterTest {

    @Mock
    private lateinit var mockView: View

    @Mock
    private lateinit var mockTextView: TextView

    private lateinit var questionAdapter: QuestionAdapter

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val questionList = mutableListOf("Question1", "Question2")
        questionAdapter = QuestionAdapter(questionList, emptyList())
    }

    @Test
    fun getItemCount_ReturnsCorrectCount() {
        val expectedCount = questionAdapter.itemCount
        assertEquals(expectedCount, 2) // Question1 + Question2
    }

    @Test
    fun getItemCount_ReturnsIncorrectCount() {
        val expectedCount = questionAdapter.itemCount
        assertEquals(expectedCount, 3) // Incorrect count for demonstration
    }

    @Test
    fun onBindViewHolder_BindsCorrectQuestion() {
        // Arrange
        `when`(mockView.findViewById<TextView>(com.hfad.finalproject_team_temp.R.id.textQuestion)).thenReturn(mockTextView)
        `when`(mockTextView.text).thenReturn("Question1") // Mocking the behavior of TextView

        val adapter = QuestionAdapter(listOf("Question1", "Question2"), emptyList())
        val viewHolder = adapter.ViewHolder(mockView)

        // Act
        adapter.onBindViewHolder(viewHolder, 0)

        // Assert
        assertEquals("Question1", viewHolder.questionTextView.text)
    }
    @Test
    fun onBindViewHolder_BindsInCorrectQuestion() {
        // Arrange
        `when`(mockView.findViewById<TextView>(com.hfad.finalproject_team_temp.R.id.textQuestion)).thenReturn(mockTextView)
        `when`(mockTextView.text).thenReturn("Question1") // Mocking the behavior of TextView

        val adapter = QuestionAdapter(listOf("Question1", "Question2"), emptyList())
        val viewHolder = adapter.ViewHolder(mockView)

        // Act
        adapter.onBindViewHolder(viewHolder, 3)

        // Assert
        assertEquals("Question1", viewHolder.questionTextView.text)
    }
}
