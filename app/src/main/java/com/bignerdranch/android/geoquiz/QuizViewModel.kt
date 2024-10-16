package com.bignerdranch.android.geoquiz
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlin.rem

private const val TAG = "QuizViewModel"

class QuizViewModel: ViewModel() {
    /*init {
        Log.d(TAG, "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance is about to be destroyed")
    }*/


    // storing curr index and questionBank Data

    var currentIndex = 0
    private val questionBank = listOf<Question>(
        Question(R.string.question_australia, true),
        Question(R.string.question_ocean, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        currentIndex = ((currentIndex - 1) + questionBank.size) % questionBank.size
    }
}