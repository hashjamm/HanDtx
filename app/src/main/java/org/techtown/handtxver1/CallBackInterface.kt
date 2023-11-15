package org.techtown.handtxver1

import org.techtown.handtxver1.emotionDiary.EachDateRecordDataClass

interface CallBackInterface {

    fun onCallBackValueChanged(success: Boolean = false, dateNum: Int? = null, positionData: EachDateRecordDataClass? = null)

}