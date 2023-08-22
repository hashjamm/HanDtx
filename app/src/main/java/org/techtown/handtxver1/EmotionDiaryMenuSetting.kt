package org.techtown.handtxver1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EmotionDiaryMenuSetting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotion_diary_menu_setting)

        val menuBarFragment = BottomMenuBar(1)

        val backArrow = findViewById<androidx.appcompat.widget.AppCompatImageView>(R.id.backArrow)
        val backWord = findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.backWord)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, menuBarFragment).commit()

        backArrow.setOnClickListener{
            finish()
        }

        backWord.setOnClickListener {
            finish()
        }

    }

    override fun onResume() {
        super.onResume()

        val menuBarFragment = BottomMenuBar(1)
        supportFragmentManager.beginTransaction().add(R.id.menuBar, menuBarFragment).commit()

    }

}