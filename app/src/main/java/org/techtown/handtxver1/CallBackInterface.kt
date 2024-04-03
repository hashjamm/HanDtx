package org.techtown.handtxver1

import org.techtown.handtxver1.emotionDiary.EachDateRecordDataClass

interface CallBackInterface {

    fun callBackEachDateEmotionDiary(
        success: Boolean = false,
        dateNum: Int? = null,
        positionData: EachDateRecordDataClass? = null
    )

}