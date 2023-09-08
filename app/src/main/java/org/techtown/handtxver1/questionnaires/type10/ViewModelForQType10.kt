package org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type10

import androidx.lifecycle.ViewModel

class ViewModelForQType10 : ViewModel() {

    // 1번 ~ 11번 문항에 대한 응답을 Int 형태로 저장하는 배열 생성
    // 11번 문항은 적혀있는지 아닌지를 나타내는 것
    // 반드시 적어야지만 하는 문항이기 때문에 responseSequence 에 완료 여부를 포함하고자 함
    // 11번 문항 완료 여부를 포함해야 나중에 submitButtonOn 함수에서 더 편하게 구현이 가능함
    var responseSequence = Array<Int?>(11) { null }

    // responseSequence 를 업데이트하는 함수 생성
    // 11번 문항에 대해서는 했는지 안했는지를 나타내게금 함
    fun updateResponse(
        questionNumber: Int,
        response: Int
    ) {
        if (questionNumber >= 1 && questionNumber <= responseSequence.size) {
            responseSequence[questionNumber - 1] = response
        }
    }

    fun initializingResponse(
        questionNumber: Int
    ) {
        responseSequence[questionNumber - 1] = null
    }

    var snackResponse = Array(2) { "" }

    fun updateSnackResponse(
        questionNumber: Int,
        response: String
    ) {
        if (questionNumber >= 1 && questionNumber <= responseSequence.size) {
            snackResponse[questionNumber - 1] = response
        }
    }

}