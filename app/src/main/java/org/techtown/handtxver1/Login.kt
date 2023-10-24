package org.techtown.handtxver1

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {

    private var retrofit = Retrofit.Builder()
        .baseUrl("https://3.37.133.233")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var loginInterface: LoginInterface = retrofit.create(LoginInterface::class.java)

    // 체크박스 체크 시, 입력된 user 의 ID 와 PW 를 자동으로 유지하도록 정보 저장 sharedPreferences 생성
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

        val saveButton =
            findViewById<CheckBox>(R.id.saveButton)

        sharedPreferences = getSharedPreferences(
            "LoginSharedPreferences", Context.MODE_PRIVATE
        )

        val editor = sharedPreferences.edit()

        userID.setText(sharedPreferences.getString("saveID", ""))
        userPW.setText(sharedPreferences.getString("savePW", ""))

        saveButton.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                editor.putBoolean("save", isChecked)
                editor.apply()
            } else {
                editor.putBoolean("save", isChecked)
                editor.apply()
            }

        }

        userID.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val saveID = p0.toString()
                editor.putString("saveID", saveID)
                editor.apply()
            }
        })

        userPW.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val savePW = p0.toString()
                editor.putString("savePW", savePW)
                editor.apply()
            }
        })

        loginButton.setOnClickListener {

            // 유저 정보 맞는지 여부 판단
            // 실제로는 user 정보를 db와 비교 대조하는 과정을 담는 조건문이여야 함.
            // 임시로 일단은 null 값이 아니면 맞는걸로 판단

            val userIdText = userID.text.toString()
            val userPwText = userPW.text.toString()

            loginInterface.requestLogin(userIdText, userPwText).enqueue(object: Callback<LoginOutput>{
                override fun onResponse(call: Call<LoginOutput>, response: Response<LoginOutput>) {

                    val resultValue = response.body()

                    val loginDialog = AlertDialog.Builder(this@Login)

                    when (resultValue?.code) {
                        1 -> {

                            Toast.makeText(this@Login, resultValue.message, Toast.LENGTH_SHORT).show()

                            val intentToCheckIn = Intent(this@Login, CheckIn::class.java)
                            startActivity(intentToCheckIn)
                        }
                        else -> {
                            loginDialog.setTitle("로그인 오류")
                            loginDialog.setMessage(resultValue?.message)
                        }
                    }

                    loginDialog.show()

                }

                override fun onFailure(call: Call<LoginOutput>, t: Throwable) {

                    val loginDialog = AlertDialog.Builder(this@Login)
                    loginDialog.setTitle("통신 오류")
                    loginDialog.setMessage("통신에 실패했습니다")
                    loginDialog.show()
                }
            })

        }

    }
}