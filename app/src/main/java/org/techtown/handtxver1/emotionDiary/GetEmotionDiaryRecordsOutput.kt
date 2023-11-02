package org.techtown.handtxver1.emotionDiary

data class GetEmotionDiaryRecordsOutput(

    // 기분 관련 점수 및 입력 내용
    var score1: Int?,
    var inputText1: String?,
    // 불안 관련 점수 및 입력 내용
    var score2: Int?,
    var inputText2: String?,
    // 식이 관련 점수 및 입력 내용
    var score3: Int?,
    var inputText3: String?

)
