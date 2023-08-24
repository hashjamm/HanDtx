package org.techtown.handtxver1.org.techtown.handtxver1

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class ApplicationClass: Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var loginSharedPreferences: SharedPreferences
        lateinit var questionnaireSharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        loginSharedPreferences = getSharedPreferences("LoginDataSharedPreferences", Context.MODE_PRIVATE)
        questionnaireSharedPreferences = getSharedPreferences("QuestionnaireSharedPreferences", Context.MODE_PRIVATE)
    }
}