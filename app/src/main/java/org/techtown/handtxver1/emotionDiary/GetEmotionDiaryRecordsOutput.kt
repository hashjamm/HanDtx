package org.techtown.handtxver1.emotionDiary

data class GetEmotionDiaryRecordsOutput(

    // 기분 관련 점수 및 입력 내용
    var score1: Int?,
    val inputText1: String?,
    // 불안 관련 점수 및 입력 내용
    val score2: Int?,
    val inputText2: String?,
    // 식이 관련 점수 및 입력 내용
    val score3: Int?,
    val inputText3: String?

)
