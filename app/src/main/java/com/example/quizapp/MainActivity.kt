package com.example.quizapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    private val questions = listOf(
        Question(
            question = "What is the capital of France?",
            options = listOf("Berlin", "Madrid", "Paris", "Rome"),
            correctAnswer = 2
        ),
        Question(
            question = "Which planet is known as the Red Planet?",
            options = listOf("Earth", "Mars", "Jupiter", "Venus"),
            correctAnswer = 1
        ),
        Question(
            question = "Who wrote 'Hamlet'?",
            options = listOf("Charles Dickens", "William Shakespeare", "Mark Twain", "J.K. Rowling"),
            correctAnswer = 1
        )
    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadQuestion()

        val submitButton = findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener {
            checkAnswer()
        }
    }

    private fun loadQuestion() {
        if (currentQuestionIndex >= questions.size) {
            showFinalScore()
            return
        }

        val currentQuestion = questions[currentQuestionIndex]
        val questionTextView = findViewById<TextView>(R.id.questionTextView)
        val optionsRadioGroup = findViewById<RadioGroup>(R.id.optionsRadioGroup)

        // Clear any previous selections
        optionsRadioGroup.clearCheck()

        questionTextView.text = currentQuestion.question
        (findViewById<RadioButton>(R.id.option1)).text = currentQuestion.options[0]
        (findViewById<RadioButton>(R.id.option2)).text = currentQuestion.options[1]
        (findViewById<RadioButton>(R.id.option3)).text = currentQuestion.options[2]
        (findViewById<RadioButton>(R.id.option4)).text = currentQuestion.options[3]
    }

    private fun checkAnswer() {
        val optionsRadioGroup = findViewById<RadioGroup>(R.id.optionsRadioGroup)
        val selectedOptionId = optionsRadioGroup.checkedRadioButtonId

        if (selectedOptionId != -1) {
            val selectedAnswer = when (selectedOptionId) {
                R.id.option1 -> 0
                R.id.option2 -> 1
                R.id.option3 -> 2
                R.id.option4 -> 3
                else -> -1
            }

            val currentQuestion = questions[currentQuestionIndex]
            if (selectedAnswer == currentQuestion.correctAnswer) {
                score++
            }

            currentQuestionIndex++
            loadQuestion()
        } else {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showFinalScore() {
        AlertDialog.Builder(this)
            .setTitle("Quiz Completed!")
            .setMessage("Your score is $score out of ${questions.size}")
            .setPositiveButton("OK") { _, _ ->
                // Reset the quiz
                currentQuestionIndex = 0
                score = 0
                loadQuestion()
            }
            .setCancelable(false)
            .show()
    }
}
