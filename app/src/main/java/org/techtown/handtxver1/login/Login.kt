package org.techtown.handtxver1.login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.techtown.handtxver1.ApplicationClass
import org.techtown.handtxver1.CheckIn
import org.techtown.handtxver1.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

class Login : AppCompatActivity() {

    // retrofit 객체 생성
    private var retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/") // 연결하고자 하는 서버 주소 입력
        .addConverterFactory(GsonConverterFactory.create()) // gson 을 통한 javaScript 로의 코드 자동 전환 - Gson 장착
        .build() // 코드 마무리

    // 로그인 서비스 interface 를 장착한 Retrofit 객체 생성
    private var loginInterface: LoginInterface = retrofit.create(LoginInterface::class.java)

    fun findTLSVersion() {
        try {
            // TLS를 사용하는 SSLSocket 생성
            val sslSocketFactory: SSLSocketFactory? =
                SSLSocketFactory.getDefault() as? SSLSocketFactory
            if (sslSocketFactory != null) {
                val sslSocket: SSLSocket = sslSocketFactory.createSocket() as SSLSocket

                // 현재 TLS 버전 확인
                val tlsVersion: String? = sslSocket.session?.protocol
                if (tlsVersion != null) {
                    Log.d("LE", "Current TLS version : $tlsVersion")
                } else {
                    Log.d("LE", "Unable to determine TLS version.")
                }
            } else {
                Log.d("LE", "SSLSocketFactory is null.")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

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

        val editor = ApplicationClass.loginSharedPreferences.edit()

        // loginSharedPreferences 라는 동명의 데이터 저장소 파일을 이전에 사용했었는데, 이를 초기화 해줘야함 한 번
        // editor.clear()
        // editor.apply()

        saveButton.isChecked = ApplicationClass.loginSharedPreferences.getBoolean("save", false)

        if (saveButton.isChecked) {
            userID.setText(ApplicationClass.loginSharedPreferences.getString("saveID", ""))
            userPW.setText(ApplicationClass.loginSharedPreferences.getString("savePW", ""))
        } else {
            userID.setText("")
            userPW.setText("")
        }

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
        userID.addTextChangedListener(object : TextWatcher {
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
        userPW.addTextChangedListener(object : TextWatcher {
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
            loginInterface.requestLogin(userIdText, userPwText)
                .enqueue(object : Callback<LoginOutput> {

                    // 통신에 성공한 경우
                    override fun onResponse(
                        call: Call<LoginOutput>,
                        response: Response<LoginOutput>
                    ) {

                        val statusCode = response.code()

                        if (response.isSuccessful) {

                            // body 메서드를 통해 응답받은 내용을 가져올 수 있음
                            val resultValue = response.body()

                            val loginDialog = AlertDialog.Builder(this@Login)

                            if (statusCode == 200) {

                                Toast.makeText(this@Login, resultValue?.message, Toast.LENGTH_SHORT)
                                    .show()

                                val intentToCheckIn = Intent(this@Login, CheckIn::class.java)
                                startActivity(intentToCheckIn)

                                editor.putString("saveID", userIdText)
                                editor.putString("savePW", userPwText)
                                editor.apply()

                            } else {

                                loginDialog.setTitle("로그인 오류 :")
                                loginDialog.setMessage(resultValue?.message)

                                loginDialog.show()

                            }

                        } else {

                            val loginDialog = AlertDialog.Builder(this@Login)

                            loginDialog.setTitle("로그인 오류 :")
                            loginDialog.setMessage("등록되지 않은 사용자 계정입니다.")

                            loginDialog.show()

                        }

                    }


                    // 통신에 실패한 경우
                    override fun onFailure(call: Call<LoginOutput>, t: Throwable) {

                        findTLSVersion()

                        val loginDialog = AlertDialog.Builder(this@Login)
                        loginDialog.setTitle("통신 오류")
                        loginDialog.setMessage("통신에 실패했습니다 : " + t.message)
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

        val saveButton =
            findViewById<CheckBox>(R.id.saveButton)

        saveButton.isChecked = ApplicationClass.loginSharedPreferences.getBoolean("save", false)

        if (saveButton.isChecked) {
            userID.setText(ApplicationClass.loginSharedPreferences.getString("saveID", ""))
            userPW.setText(ApplicationClass.loginSharedPreferences.getString("savePW", ""))
        } else {
            userID.setText("")
            userPW.setText("")
        }

    }
}