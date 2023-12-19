package org.techtown.handtxver1.questionnaires.type2

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface GetSelfDiagnosisSurveyInterface {

    @FormUrlEncoded
    @POST("app_get_self_diagnosis_survey/")
    fun requestGetSelfDiagnosisSurvey(
        @Field("user_id") user_id:String,
        @Field("date") date: Date
    ) : Call<GetSelfDiagnosisSurveyOutput>

}