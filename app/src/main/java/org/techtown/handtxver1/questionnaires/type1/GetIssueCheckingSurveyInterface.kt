package org.techtown.handtxver1.questionnaires.type1

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface GetIssueCheckingSurveyInterface {

    @FormUrlEncoded
    @POST("app_get_issue_checking_survey/")
    fun requestGetIssueCheckingSurvey(
        @Field("user_id") user_id:String,
        @Field("date") date: Date
    ) : Call<GetIssueCheckingSurveyOutput>

}