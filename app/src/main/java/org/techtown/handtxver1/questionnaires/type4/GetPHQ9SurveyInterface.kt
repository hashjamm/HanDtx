package org.techtown.handtxver1.questionnaires.type4

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface GetPHQ9SurveyInterface {

    @FormUrlEncoded
    @POST("app_get_phq9_survey/")
    fun requestGetPHQ9Survey(
        @Field("user_id") user_id:String,
        @Field("date") date: Date
    ) : Call<GetPHQ9SurveyOutput>

}