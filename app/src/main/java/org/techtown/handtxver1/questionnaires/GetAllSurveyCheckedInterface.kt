package org.techtown.handtxver1.questionnaires

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GetAllSurveyCheckedInterface {

    @FormUrlEncoded
    @POST("hanDtxPrototypeApp/app_get_all_survey_checked/")
    fun requestGetAllSurveyChecked(
        @Field("user_id") user_id:String,
        @Field("date") date: String
    ) : Call<GetAllSurveyCheckedOutput>

}