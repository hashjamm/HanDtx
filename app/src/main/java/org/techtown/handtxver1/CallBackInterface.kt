package org.techtown.handtxver1

import org.techtown.handtxver1.emotionDiary.EachDateRecordDataClass
import org.techtown.handtxver1.emotionDiary.ViewModelForEachDateViewer

interface CallBackInterface {

    fun callBackEachDateEmotionDiary(
        viewModel: ViewModelForEachDateViewer,
        success: Boolean = false,
        dateNum: Int? = null,
        positionData: EachDateRecordDataClass? = null
    )

}