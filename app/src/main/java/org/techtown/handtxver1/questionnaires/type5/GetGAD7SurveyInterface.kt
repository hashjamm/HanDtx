package org.techtown.handtxver1.questionnaires.type5

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface GetGAD7SurveyInterface {

    @FormUrlEncoded
    @POST("app_get_gad7_survey/")
    fun requestGetGAD7Survey(
        @Field("user_id") user_id:String,
        @Field("date") date: Date
    ) : Call<GetGAD7SurveyOutput>

}