package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

/**
* ViewModel containing the app data and methods to process the data
*/
class GameViewModel : ViewModel() {
    private var _score = 0
    private var _currentWordCount = 0
    private lateinit var _currentScrambledWord: String
    //List of words used in the game
    private var wordList: MutableList<String> = mutableListOf(" ")
    private lateinit var currentWord: String

    // Backing property
    val score: Int get() = _score
    val currentWordCount: Int get() = _currentWordCount
    val currentScrambledWord: String get() = _currentScrambledWord

    init {
        Log.d("GameFragment", "GameViewModel created!")
        // get a scrambled word during instantiation and initialization of this class
        getNextWord()
    }

    //update currentWord and currentScrambledWord with the next word
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        // shuffle until the tempWord is not the same ordered characters as currentWord
        while (String(tempWord).equals(currentWord, false)) {
            // shuffle the characters
            tempWord.shuffle()
        }
        //check if a word has been used already
        if (wordList.contains(currentWord)) {
            getNextWord()
        } else {
            //update _currentScrambledWord
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordList.add(currentWord)
        }
    }

    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, false)) {
            increaseScore()
            return true
        }
        return false
    }
    /*
    Reinitialize the game data to restart the game
     */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordList.clear()
        getNextWord()
    }

    // This callback is called right before the viewModel is destroyed
    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }
}