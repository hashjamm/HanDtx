package org.techtown.handtxver1.emotionDiary

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GetEmotionDiaryRecordsInterface {

    @FormUrlEncoded
    @POST("hanDtxPrototypeApp/app_get_emotion_diary_records/")
    fun requestGetEmotionDiaryRecords(
        @Field("user_id") user_id:String,
        @Field("date") date: String
    ) : Call<GetEmotionDiaryRecordsOutput>

}