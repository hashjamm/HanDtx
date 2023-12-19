package org.techtown.handtxver1.questionnaires.type10

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface GetNutritionSurveyInterface {

    @FormUrlEncoded
    @POST("app_get_nutrition_survey/")
    fun requestGetNutritionSurvey(
        @Field("user_id") user_id:String,
        @Field("date") date: Date
    ) : Call<GetNutritionSurveyOutput>

}