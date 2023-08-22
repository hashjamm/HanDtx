package org.techtown.handtxver1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GoingToMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_going_to_main)

        val fragment = BottomMenuBar(0)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, fragment).commit()

        val goToMainButton =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.goToMain)
        goToMainButton.setOnClickListener {
            val intentGoToMainButton = Intent(this, MainPage::class.java)
            startActivity(intentGoToMainButton)
        }

    }

    override fun onResume() {
        super.onResume()

        val fragment = BottomMenuBar(0)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, fragment).commit()
    }

}