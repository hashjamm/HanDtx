package org.techtown.handtxver1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CheckIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in)

        val fragment = BottomMenuBar(0)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, fragment).commit()

        val checkInButton =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.checkIn)
        checkInButton.setOnClickListener {
            val intentCheckInButton = Intent(this, TodayCheckIn::class.java)
            startActivity(intentCheckInButton)
        }

    }

    override fun onResume() {
        super.onResume()

        val fragment = BottomMenuBar(0)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, fragment).commit()
    }
}