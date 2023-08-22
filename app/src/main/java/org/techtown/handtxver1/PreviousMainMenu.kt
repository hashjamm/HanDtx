package org.techtown.handtxver1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import android.content.Intent as Intent

class PreviousMainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_main_menu)

        val emotion_diary = findViewById<AppCompatTextView>(R.id.emotion_diary)

        emotion_diary.setOnClickListener {

            val toEmotionDiary1 = Intent(this, EmotionDiary::class.java)

            startActivity(toEmotionDiary1)

        }


    }
}