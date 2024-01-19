package org.techtown.handtxver1.questionnaires.type1

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface UpdateIssueCheckingSurveyInterface {

    @FormUrlEncoded
    @POST("hanDtxPrototypeApp/app_update_issue_checking_survey/")
    fun requestUpdateIssueCheckingSurvey(
        @Field("user_id") user_id: String,
        @Field("date") date: String,
        @Field("checkbox1") checkBox1: Int,
        @Field("checkbox2") checkBox2: Int,
        @Field("checkbox3") checkBox3: Int,
        @Field("checkbox4") checkBox4: Int,
        @Field("checkbox5") checkBox5: Int,
        @Field("checkbox6") checkBox6: Int,
        @Field("checkbox7") checkBox7: Int,
        @Field("checkbox8") checkBox8: Int,
        @Field("checkbox9") checkBox9: Int,
        @Field("checkbox10") checkBox10: Int,
        @Field("checkbox11") checkBox11: Int,
        @Field("checkbox12") checkBox12: Int,
        @Field("checkbox13") checkBox13: Int,
        @Field("checkbox14") checkBox14: Int,
        @Field("checkbox15") checkBox15: Int,
        @Field("checkbox16") checkBox16: Int,
        @Field("checkbox17") checkBox17: Int,
        @Field("checkbox18") checkBox18: Int,
        @Field("checkbox19") checkBox19: Int,
        @Field("checkbox20") checkBox20: Int,
        @Field("checkbox21") checkBox21: Int,
        @Field("checkbox22") checkBox22: Int,
        @Field("inputText") inputText: String
    ): Call<UpdateIssueCheckingSurveyOutput>

}