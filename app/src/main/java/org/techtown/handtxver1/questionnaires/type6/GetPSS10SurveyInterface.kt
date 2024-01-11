package org.techtown.handtxver1.questionnaires.type6

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface GetPSS10SurveyInterface {

    @FormUrlEncoded
    @POST("hanDtxPrototypeApp/app_get_pss10_survey/")
    fun requestGetPSS10Survey(
        @Field("user_id") user_id:String,
        @Field("date") date: Date
    ) : Call<GetPSS10SurveyOutput>

}