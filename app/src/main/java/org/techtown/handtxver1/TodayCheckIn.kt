package org.techtown.handtxver1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TodayCheckIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_check_in)

        val fragment = BottomMenuBar(0)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, fragment).commit()

        val nextButton =
            findViewById<androidx.appcompat.widget.AppCompatImageView>(R.id.nextButton)
        nextButton.setOnClickListener {
            val intentNextButton = Intent(this, GoingToMain::class.java)
            startActivity(intentNextButton)

        }
    }

    override fun onResume() {
        super.onResume()

        val fragment = BottomMenuBar(0)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, fragment).commit()

    }
}
