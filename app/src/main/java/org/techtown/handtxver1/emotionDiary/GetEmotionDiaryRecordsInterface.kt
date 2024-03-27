package org.techtown.handtxver1.emotionDiary

import retrofit2.Call
import retrofit2.http.*

interface GetEmotionDiaryRecordsInterface {


    // 감정다이어리를 위한 Retrofit 인터페이스 함수
    @GET("hanDtxPrototypeApp/app_get_emotion_diary_records/")
    fun requestGetEmotionDiaryRecords(
        @Query("user_id") user_id:String,
        @Query("date") date: String
    ) : Call<GetEmotionDiaryRecordsOutput>

    // 매일 감정다이어리를 위한 Retrofit 인터페이스 함수
    @GET("hanDtxPrototypeApp/app_get_emotion_diary_records_monthly/")
    fun requestGetEmotionDiaryRecordsMonthly(
        @Query("user_id") user_id: String,
        @Query("date") date: String
    ) : Call<List<GetEmotionDiaryRecordsOutput>>


}