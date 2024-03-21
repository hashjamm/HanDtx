package org.techtown.handtxver1.emotionDiary

import retrofit2.Call
import retrofit2.http.*

interface GetEmotionDiaryRecordsInterface {

    @GET("hanDtxPrototypeApp/app_get_emotion_diary_records/")
    fun requestGetEmotionDiaryRecords(
        @Query("user_id") user_id:String,
        @Query("date") date: String
    ) : Call<GetEmotionDiaryRecordsOutput>

}