package org.techtown.handtxver1.questionnaires.type7

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface GetExerciseSurveyInterface {

    @FormUrlEncoded
    @POST("hanDtxPrototypeApp/app_get_exercise_survey/")
    fun requestGetExerciseSurvey(
        @Field("user_id") user_id:String,
        @Field("date") date: String
    ) : Call<GetExerciseSurveyOutput>

}