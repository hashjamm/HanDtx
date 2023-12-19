package org.techtown.handtxver1.questionnaires

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface GetSmokingDrinkingSurveyInterface {

    @FormUrlEncoded
    @POST("app_get_smoking_drinking_survey/")
    fun requestGetSmokingDrinkingSurvey(
        @Field("user_id") user_id:String,
        @Field("date") date: Date
    ) : Call<GetSmokingDrinkingSurveyOutput>

}