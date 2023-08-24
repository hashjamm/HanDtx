package org.techtown.handtxver1

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.techtown.handtxver1.org.techtown.handtxver1.CommonUserDefinedObjectSet
import java.text.SimpleDateFormat
import java.util.*

class Login : AppCompatActivity() {

    // 로그인 성공시, user 의 ID 와 PW 를 저장해두어 앱 전체에서 접근할 수 있도록 유저 정보 저장 sharedPreferences 생성
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.loginButton)

        val userID =
            findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.userID)

        val userPW =
            findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.userPW)

        sharedPreferences = getSharedPreferences(
            "LoginDataSharedPreferences", Context.MODE_PRIVATE
        )

        val editor = sharedPreferences.edit()

        loginButton.setOnClickListener {

            // 유저 정보 맞는지 여부 판단
            // 실제로는 user 정보를 db와 비교 대조하는 과정을 담는 조건문이여야 함.
            // 임시로 일단은 null 값이 아니면 맞는걸로 판단

            if (userID.text.toString() != "" && userPW.text.toString() != "") {

                // 현재 시간을 형식에 맞게 포맷팅하여 문자열로 변환

                // 현재 시간을 형식에 맞게 포맷팅하여 문자열로 변환
                val loginTimeFormat: SimpleDateFormat =
                    SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA)
                val loginTime = loginTimeFormat.format(Calendar.getInstance().time)

                val loginInfo =
                CommonUserDefinedObjectSet.LoginInfo(
                    userID.text.toString(),
                    userPW.text.toString(),
                    loginTime
                )

                val serializedLoginInfo = Json.encodeToString(loginInfo)

                editor.putString("userInfo", serializedLoginInfo)
                editor.apply()

                val intentLoginButton = Intent(this, CheckIn::class.java)
                startActivity(intentLoginButton)

            } else {
                val wrongUserInfoWarningPopup =
                    AlertDialog.Builder(this, R.style.PopupGravityCenterStyle)
                        .setTitle("등록되지 않거나 잘못된 유저 정보입니다.")
                        .setMessage("옳바른 정보를 입력해주십시오.")

                wrongUserInfoWarningPopup.show()
            }
        }

    }
}