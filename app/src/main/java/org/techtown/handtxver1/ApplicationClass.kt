package org.techtown.handtxver1

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class ApplicationClass: Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var loginSharedPreferences: SharedPreferences
        // -> retrofit 사용 과정에서 새로 만든 로그인 자동완성 데이터 저장소
        // -> 구조를 바꿨기 때문에 editor 초기화 필수
        lateinit var questionnaireSharedPreferences: SharedPreferences
        lateinit var emotionDiarySharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        loginSharedPreferences = getSharedPreferences("LoginDataSharedPreferences", Context.MODE_PRIVATE)
        questionnaireSharedPreferences = getSharedPreferences("QuestionnaireSharedPreferences", Context.MODE_PRIVATE)
        emotionDiarySharedPreferences = getSharedPreferences("EmotionDiarySharedPreferences", Context.MODE_PRIVATE)
    }
}