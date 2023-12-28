package org.techtown.handtxver1.login

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginInterface {

    @FormUrlEncoded
    @POST("app_login/")
    fun requestLogin(
        @Field("user_id") user_id:String,
        @Field("user_pw") user_pw:String
    ) : Call<LoginOutput>

}