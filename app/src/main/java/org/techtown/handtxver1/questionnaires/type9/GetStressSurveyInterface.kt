package org.techtown.handtxver1.questionnaires.type9

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface GetStressSurveyInterface {

    @FormUrlEncoded
    @POST("app_get_stress_survey/")
    fun requestGetStressSurvey(
        @Field("user_id") user_id:String,
        @Field("date") date: Date
    ) : Call<GetStressSurveyOutput>

}