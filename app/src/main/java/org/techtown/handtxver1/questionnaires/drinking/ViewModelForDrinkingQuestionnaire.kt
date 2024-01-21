package org.techtown.handtxver1.questionnaires.drinking

import androidx.lifecycle.ViewModel

class ViewModelForDrinkingQuestionnaire : ViewModel() {

    var responseSequence = Array<Int?>(10) { null }

    fun updateResponse(
        questionNumber: Int,
        response: Int
    ) {
        if (questionNumber >= 1 && questionNumber <= responseSequence.size) {
            responseSequence[questionNumber - 1] = response
        }
    }

}