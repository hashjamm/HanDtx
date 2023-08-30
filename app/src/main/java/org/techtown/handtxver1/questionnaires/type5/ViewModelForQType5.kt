package org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type5

import androidx.lifecycle.ViewModel

class ViewModelForQType5 : ViewModel() {

    var responseSequence = Array<Int?>(7) { null }

    fun updateResponse(
        questionNumber: Int,
        response: Int
    ) {
        if (questionNumber >= 1 && questionNumber <= responseSequence.size) {
            responseSequence[questionNumber - 1] = response
        }
    }

}