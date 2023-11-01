package org.techtown.handtxver1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.techtown.handtxver1.login.Login

class FirstPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        val toLogin = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.toLogin)
        toLogin.setOnClickListener {
            val intentToLogin = Intent(this, Login::class.java)
            startActivity(intentToLogin)
        }

    }
}