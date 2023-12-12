package org.techtown.handtxver1.questionnaires

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface UpdateSmokingDrinkingSurveyInterface {

    @FormUrlEncoded
    @POST("app_update_smoking_drinking_survey/")
    fun requestUpdateSmokingDrinkingSurvey(
        @Field("user_id") user_id: String,
        @Field("date") date: Date,
        @Field("smoking_result1") smoking_result1: Int? = 0,
        @Field("smoking_result2") smoking_result2: Int? = null,
        @Field("smoking_result3") smoking_result3: Int? = null,
        @Field("smoking_result4") smoking_result4: Int? = null,
        @Field("smoking_result5") smoking_result5: Int? = null,
        @Field("smoking_result6") smoking_result6: Int? = null,
        @Field("smoking_result7") smoking_result7: Int? = null,
        @Field("smoking_result8") smoking_result8: Int? = null,
        @Field("smoking_result9") smoking_result9: Int? = null,
        @Field("drinking_result1") drinking_result1: Int? = null,
        @Field("drinking_result2") drinking_result2: Int? = null,
        @Field("drinking_result3") drinking_result3: Int? = null,
        @Field("drinking_result4") drinking_result4: Int? = null,
        @Field("drinking_result5") drinking_result5: Int? = null,
        @Field("drinking_result6") drinking_result6: Int? = null,
        @Field("drinking_result7") drinking_result7: Int? = null,
        @Field("drinking_result8") drinking_result8: Int? = null,
        @Field("drinking_result9") drinking_result9: Int? = null,
        @Field("drinking_result10") drinking_result10: Int? = null
    ): Call<UpdateSmokingDrinkingSurveyOutput>

}