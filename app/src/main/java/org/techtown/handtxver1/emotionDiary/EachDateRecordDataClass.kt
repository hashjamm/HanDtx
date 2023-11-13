package org.techtown.handtxver1.emotionDiary

data class EachDateRecordDataClass(
    val dateStringInLine: String,
    val stringByScore: String?,
    var inputText: String?,
    val additionalText: String?,
    var isExpandable: Boolean = false
)
