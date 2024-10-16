package com.bignerdranch.android.geoquiz
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

private const val TAG = "NinoIT"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView

//    var originalButtonColor = ContextCompat.getColor(this, R.color.light_grey)

    private var marksInPercentage: Double = 0.0

    // by lazy: ??
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0 // if no value or null sets 0 otherwise that value
        quizViewModel.currentIndex = currentIndex // currentIndex now will not change when OS destroys app

        // Fourth Chapter
        /*
        This version is deprecated!
        val provider: ViewModelProvider = ViewModelProviders.of(this)
        val quizViewModel = provider.get(QuizViewModel::class.java)*/

        /*val quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        Log.d(TAG, "Got a quizViewModel: $quizViewModel")*/

        /*
            For a Fragment:
            val quizViewModel = ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)
            Here, this refers to the Activity, and requireActivity() is used to get the activity when you're in a Fragment.
            Yet to know about Fragment
        */

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        updateQuestion()

        questionTextView.setOnClickListener {
//            currentIndex = (currentIndex + 1) % questionBank.size
            quizViewModel.moveToNext()
            updateQuestion()
        }
        trueButton.setOnClickListener {
            Log.d(TAG, "true button pressed")

            checkAnswer(true)
            disableButtons(trueButton, falseButton)

            // to change color
//            trueButton.setBackgroundColor(ContextCompat.getColor(this, R.color.darker_grey))
        }
        falseButton.setOnClickListener {
            Log.d(TAG, "false button pressed")

            checkAnswer(false)
            disableButtons(trueButton, falseButton)

//            falseButton.setBackgroundColor(ContextCompat.getColor(this, R.color.darker_grey))
        }
//        Log.d(TAG, "I am here!")

        ////

        nextButton.setOnClickListener {
            Log.d(TAG, "next button pressed")

            /*if(currentIndex == (questionBank.size - 1)) {
                marksInPercentage = (marksInPercentage / 6.0) * 100.0
                Toast.makeText(this, "Total percentage of marks is ${marksInPercentage}", Toast.LENGTH_SHORT).show()
                marksInPercentage = 0.0
            }*/

//            currentIndex = (currentIndex + 1) % questionBank.size
            quizViewModel.moveToNext()
            updateQuestion()
            enableButtons(trueButton, falseButton)

            // resetButtonColor
//            resetButton(trueButton)
        }
        prevButton.setOnClickListener {
            Log.d(TAG, "prev button pressed")

//            resetButton(falseButton)

//            currentIndex = ((currentIndex - 1) + questionBank.size) % questionBank.size
            quizViewModel.moveToPrevious()
            updateQuestion()
            enableButtons(trueButton, falseButton)
        }
    }

    private fun disableButtons(trueButton: Button, falseButton: Button): Unit {
        Log.d(TAG, "both buttons are disabled")
        trueButton.isEnabled = false
        falseButton.isEnabled = false
    }
    private fun enableButtons(trueButton: Button, falseButton: Button): Unit {
        Log.d(TAG, "both buttons are enabled")

        trueButton.isEnabled = true
        falseButton.isEnabled = true
    }

   /* private fun resetButton(button: Button) {
        button.postDelayed({
            button.setBackgroundColor(originalButtonColor)
        }, 0)
    }*/

    // this override ensures that class has the function I am overriding
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState()")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex) // Saving an integer which is current index in path death
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart() called")
    }

    private fun updateQuestion() {
//        val questionTextResId = questionBank[currentIndex].textResId
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId) // setting new text view/ new quiz question to this text view
    }
    private fun checkAnswer(userAnswer: Boolean) {
//        val correctAnswer = questionBank[currentIndex].answer
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId: Int = if(correctAnswer == userAnswer) {
            marksInPercentage += 1.0
            R.string.correct_toast
        }
        else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}