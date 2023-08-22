package org.techtown.handtxver1

import androidx.lifecycle.ViewModel

class ViewModelForQType7 : ViewModel() {

    // 1번 ~ 12번 문항에 대한 응답을 Int 형태로 저장하는 배열 생성
    var responseSequence = Array<Int?>(12) { null }

    // responseSequence 를 업데이트하는 함수 생성
    fun updateResponse(
        questionNumber: Int,
        response: Int
    ) {
        if (questionNumber >= 1 && questionNumber <= responseSequence.size) {
            responseSequence[questionNumber - 1] = response
        }
    }

    // 13번 문항에 대하여 18개 체크박스의 체크 여부를 0과 1로 저장하기 위한 배열 생성
    var checkedStateArray = IntArray(18) { 0 }

    // checkedStateArray 를 업데이트 하기 위한 함수 생성
    fun updateChecking(
        boxNumber: Int,
        checked: Int
    ) {
        if (checked == 0 || checked == 1) {
            checkedStateArray[boxNumber - 1] = checked
        }
    }

    // 13번 문항의 18개 체크박스 각각에 대한 운동 종목 이름의 배열을 생성
    val exerciseTypeSequence = arrayOf(
        "걷기", "축구", "조깅", "농구", "수영", "배구",
        "에어로비", "골프", "자전거타기", "볼링", "계단오르기", "라켓볼",
        "줄넘기", "게이트볼", "등산", "스쿼시", "배드민턴", "체조"
    )

    // 체크 박스 중 체크된 곳들에 해당하는 운동 종목들을 하나의 string 으로 반환해주는 함수 생성
    fun loadingExerciseTypeByUsingBoxNumber(): String {

        val checkedExercisesArray = ArrayList<String>()

        checkedStateArray.forEachIndexed { index, isChecked ->
            if (isChecked == 1) {
                checkedExercisesArray.add(exerciseTypeSequence[index])
            }
        }

        return checkedExercisesArray.joinToString(", ")
    }

}