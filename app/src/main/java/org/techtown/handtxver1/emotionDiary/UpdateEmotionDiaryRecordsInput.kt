package org.techtown.handtxver1.emotionDiary

data class UpdateEmotionDiaryRecordsInput(

    var userID: String,
    var date: String,
    var score: Int?,
    var inputText: String?,
    var type: Int

)
