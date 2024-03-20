package org.techtown.handtxver1.emotionDiary

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface UpdateEmotionDiaryRecordsInterface {

    @FormUrlEncoded
    @POST("hanDtxPrototypeApp/app_update_emotion_diary_records/")
    fun requestUpdateEmotionDiaryRecords(
        @Field("user_id") user_id:String,
        @Field("date") date: String,
        @Field("score") score: Int?,
        @Field("input_text") input_text: String?,
        @Field("type") type: Int
    ) : Call<UpdateEmotionDiaryRecordsOutput>

}