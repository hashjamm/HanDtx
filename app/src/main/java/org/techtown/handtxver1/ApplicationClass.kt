package org.techtown.handtxver1.org.techtown.handtxver1

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import org.techtown.handtxver1.R

class ApplicationClass: Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var loginSharedPreferences: SharedPreferences
        lateinit var questionnaireSharedPreferences: SharedPreferences
        lateinit var previousPageDrawable: Drawable
        lateinit var nextPageDrawable: Drawable
        lateinit var submitButtonDrawable: Drawable
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        loginSharedPreferences = getSharedPreferences("LoginDataSharedPreferences", Context.MODE_PRIVATE)
        questionnaireSharedPreferences = getSharedPreferences("QuestionnaireSharedPreferences", Context.MODE_PRIVATE)
        previousPageDrawable = ContextCompat.getDrawable(this, R.drawable.previous_page)!!
        nextPageDrawable = ContextCompat.getDrawable(this, R.drawable.next_page)!!
        submitButtonDrawable = ContextCompat.getDrawable(this, R.drawable.submit_button)!!
    }
}