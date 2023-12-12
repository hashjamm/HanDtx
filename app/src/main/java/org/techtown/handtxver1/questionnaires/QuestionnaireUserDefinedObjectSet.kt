package org.techtown.handtxver1.questionnaires

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import org.techtown.handtxver1.R
import org.techtown.handtxver1.org.techtown.handtxver1.ApplicationClass
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class QuestionnaireUserDefinedObjectSet {

    // 로그인한 유저 아이디 지정
    val userID = ApplicationClass.loginSharedPreferences.getString("saveID", "")

    // 현재 날짜 가져오기
    private val currentDate = Calendar.getInstance()
    val date: Date = currentDate.time

    // retrofit 객체 생성
    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://112.222.70.85") // 연결하고자 하는 서버 주소 입력
        .addConverterFactory(GsonConverterFactory.create()) // gson 을 통한 javaScript 로의 코드 자동 전환 - Gson 장착
        .build() // 코드 마무리


    // 이전 및 이후페이지로 가는 버튼을 활성화하는 함수
    fun buttonDrawableOn(
        context: Context,
        textView: AppCompatTextView,
        type: Int
    ) {
        textView.background =
            when (type) {
                -1 -> ContextCompat.getDrawable(context, R.drawable.previous_page)
                1 -> ContextCompat.getDrawable(context, R.drawable.next_page)
                else -> null
            }

    }

    // 이전 및 이후페이지로 가는 버튼을 비활성화하는 함수
    fun buttonDrawableOff(textView: AppCompatTextView) {
        textView.setBackgroundColor(Color.parseColor("#00FF0000"))
    }

    // 설문 완료 버튼을 기능과 함께 활성화하는 함수
    // 1번 설문을 제외한 설문지에서 사용

    // 설문 완료 버튼을 비활성화하는 함수

    fun submitButtonOff(textView: AppCompatTextView) {
        textView.setBackgroundColor(Color.parseColor("#00FF0000"))
        textView.text = ""
        textView.setOnClickListener(null)
    }

    // pageBar 를 동적으로 설정해주기 위한 함수

    fun pageBarLengthSetting(
        pageBar: ConstraintLayout,
        presentPageBar: AppCompatImageView,
        pageNum: Int,
        wholePageNum: Int
    ) {

        pageBar.post {
            val pageBarWidth = pageBar.width

            val presentPageBarLayoutParams = LinearLayout.LayoutParams(
                pageBarWidth * pageNum / wholePageNum,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            presentPageBar.layoutParams = presentPageBarLayoutParams
        }
    }

    @SuppressLint("SetTextI18n")
    fun pageNumberBoxSetting(
        pageNumberBox: AppCompatTextView,
        pageNum: Int,
        wholePageNum: Int
    ) {
        pageNumberBox.text = "$pageNum of $wholePageNum"
    }

}