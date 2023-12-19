package org.techtown.handtxver1.questionnaires.type3

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface GetWellBeingScaleSurveyInterface {

    @FormUrlEncoded
    @POST("app_get_well_being_scale_survey/")
    fun requestGetWellBeingScaleSurvey(
        @Field("user_id") user_id:String,
        @Field("date") date: Date
    ) : Call<GetWellBeingScaleSurveyOutput>

}