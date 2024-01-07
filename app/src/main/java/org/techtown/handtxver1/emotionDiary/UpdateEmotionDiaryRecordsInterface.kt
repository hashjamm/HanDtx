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
        @Field("date") date: Date,
        @Field("score1") score1: Int?,
        @Field("inputText1") inputText1: String?,
        @Field("score2") score2: Int?,
        @Field("inputText2") inputText2: String?,
        @Field("score3") score3: Int?,
        @Field("inputText3") inputText3: String?
    ) : Call<UpdateEmotionDiaryRecordsOutput>

}