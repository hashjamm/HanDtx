package org.techtown.handtxver1.questionnaires.type5

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface UpdateGAD7SurveyInterface {

    @FormUrlEncoded
    @POST("app_update_gad7_survey/")
    fun requestUpdateGAD7Survey(
        @Field("user_id") user_id: String,
        @Field("date") date: Date,
        @Field("result1") result1: Int,
        @Field("result2") result2: Int,
        @Field("result3") result3: Int,
        @Field("result4") result4: Int,
        @Field("result5") result5: Int,
        @Field("result6") result6: Int,
        @Field("result7") result7: Int
    ): Call<UpdateGAD7SurveyOutput>

}