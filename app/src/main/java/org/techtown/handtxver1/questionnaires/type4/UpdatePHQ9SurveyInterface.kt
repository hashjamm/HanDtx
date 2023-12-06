package org.techtown.handtxver1.questionnaires.type4

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface UpdatePHQ9SurveyInterface {

    @FormUrlEncoded
    @POST("app_update_phq9_survey/")
    fun requestUpdatePHQ9Survey(
        @Field("user_id") user_id: String,
        @Field("date") date: Date,
        @Field("result1") result1: Int,
        @Field("result2") result2: Int,
        @Field("result3") result3: Int,
        @Field("result4") result4: Int,
        @Field("result5") result5: Int,
        @Field("result6") result6: Int,
        @Field("result7") result7: Int,
        @Field("result8") result8: Int,
        @Field("result9") result9: Int
    ): Call<UpdatePHQ9SurveyOutput>

}