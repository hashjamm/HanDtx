package org.techtown.handtxver1

import androidx.lifecycle.ViewModel

class ViewModelForQType2 : ViewModel() {

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