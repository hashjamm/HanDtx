package org.techtown.handtxver1.login

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
import org.techtown.handtxver1.CheckIn
import org.techtown.handtxver1.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {

    // retrofit 객체 생성
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://3.37.133.233") // 연결하고자 하는 서버 주소 입력
        .addConverterFactory(GsonConverterFactory.create()) // gson 을 통한 javaScript 로의 코드 자동 전환 - Gson 장착
        .build() // 코드 마무리

    // 로그인 서비스 interface 를 장착한 Retrofit 객체 생성
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

        // 자동 완성 버튼 체크 여부 및 기록된 id, pw 값을 저장하기 위한 데이터 파일
        sharedPreferences = getSharedPreferences(
            "loginSharedPreferences", Context.MODE_PRIVATE
        )

        val editor = sharedPreferences.edit()

        // loginSharedPreferences 라는 동명의 데이터 저장소 파일을 이전에 사용했었는데, 이를 초기화 해줘야함 한 번
        // editor.clear()
        // editor.apply()

        userID.setText(sharedPreferences.getString("saveID", ""))
        userPW.setText(sharedPreferences.getString("savePW", ""))

        // save 버튼의 클릭 상태가 변경될 때마다 체크 여부를 editor 에 업데이트
        saveButton.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                editor.putBoolean("save", isChecked)
                editor.apply()
            } else {
                editor.putBoolean("save", isChecked)
                editor.apply()
            }

        }

        // userID 부분의 텍스트가 변경됨에 따라 editor 에 해당 텍스트 내용 저장
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

        // userPW 부분의 텍스트가 변경됨에 따라 editor 에 해당 텍스트 내용 저장
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

            // login 을 위한 인터페이스 사용 과정
            loginInterface.requestLogin(userIdText, userPwText).enqueue(object: Callback<LoginOutput>{

                // 통신에 성공한 경우
                override fun onResponse(call: Call<LoginOutput>, response: Response<LoginOutput>) {

                    // body 메서드를 통해 응답받은 내용을 가져올 수 있음
                    val resultValue = response.body()

                    val loginDialog = AlertDialog.Builder(this@Login)

                    // 통신에 성공하더라도, 로그인 자체가 성공할 때와 아닐때에 대하여 세부적인 동작을 구현
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


                // 통신에 실패한 경우
                override fun onFailure(call: Call<LoginOutput>, t: Throwable) {

                    val loginDialog = AlertDialog.Builder(this@Login)
                    loginDialog.setTitle("통신 오류")
                    loginDialog.setMessage("통신에 실패했습니다")
                    loginDialog.show()
                }
            })

        }

    }

    override fun onResume() {
        super.onResume()

        val userID =
            findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.userID)

        val userPW =
            findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.userPW)

        // 자동 완성 버튼 체크 여부 및 기록된 id, pw 값을 저장하기 위한 데이터 파일
        sharedPreferences = getSharedPreferences(
            "LoginSharedPreferences", Context.MODE_PRIVATE
        )

        userID.setText(sharedPreferences.getString("saveID", ""))
        userPW.setText(sharedPreferences.getString("savePW", ""))

    }
}