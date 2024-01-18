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
        @Field("checkbox1") checkBox1: Boolean,
        @Field("checkbox2") checkBox2: Boolean,
        @Field("checkbox3") checkBox3: Boolean,
        @Field("checkbox4") checkBox4: Boolean,
        @Field("checkbox5") checkBox5: Boolean,
        @Field("checkbox6") checkBox6: Boolean,
        @Field("checkbox7") checkBox7: Boolean,
        @Field("checkbox8") checkBox8: Boolean,
        @Field("checkbox9") checkBox9: Boolean,
        @Field("checkbox10") checkBox10: Boolean,
        @Field("checkbox11") checkBox11: Boolean,
        @Field("checkbox12") checkBox12: Boolean,
        @Field("checkbox13") checkBox13: Boolean,
        @Field("checkbox14") checkBox14: Boolean,
        @Field("checkbox15") checkBox15: Boolean,
        @Field("checkbox16") checkBox16: Boolean,
        @Field("checkbox17") checkBox17: Boolean,
        @Field("checkbox18") checkBox18: Boolean,
        @Field("checkbox19") checkBox19: Boolean,
        @Field("checkbox20") checkBox20: Boolean,
        @Field("checkbox21") checkBox21: Boolean,
        @Field("checkbox22") checkBox22: Boolean,
        @Field("inputText") inputText: String
    ): Call<UpdateIssueCheckingSurveyOutput>

}