package org.techtown.handtxver1.questionnaires.type8

import androidx.lifecycle.ViewModel

class ViewModelForQType8 : ViewModel() {

    // 1번 ~ 9번 문항에 대한 응답을 Int 형태로 저장하는 배열 생성
    var responseSequence = Array<Int?>(9) { null }

    // responseSequence 를 업데이트하는 함수 생성
    fun updateResponse(
        questionNumber: Int,
        response: Int
    ) {
        if (questionNumber >= 1 && questionNumber <= responseSequence.size) {
            responseSequence[questionNumber - 1] = response
        }
    }
}