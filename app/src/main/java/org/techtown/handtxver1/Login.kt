package org.techtown.handtxver1

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.techtown.handtxver1.org.techtown.handtxver1.CommonUserDefinedObjectSet

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.loginButton)

        val userID =
            findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.userID)

        val userPW =
            findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.userPW)

        val commonUserDefinedObjectSet = CommonUserDefinedObjectSet()

        loginButton.setOnClickListener {

            // 유저 정보 맞는지 여부 판단
            // 실제로는 user 정보를 db와 비교 대조하는 과정을 담는 조건문이여야 함.
            // 임시로 일단은 null 값이 아니면 맞는걸로 판단

            if (userID.text.toString() != "" && userPW.text.toString() != "") {

                commonUserDefinedObjectSet.userID = userID.text.toString()

                val intentLoginButton = Intent(this, CheckIn::class.java)
                startActivity(intentLoginButton)

            } else {
                val wrongUserInfoWarningPopup = AlertDialog.Builder(this, R.style.PopupGravityCenterStyle)
                    .setTitle("등록되지 않거나 잘못된 유저 정보입니다.")
                    .setMessage("옳바른 정보를 입력해주십시오.")

                wrongUserInfoWarningPopup.show()
            }
        }

    }
}