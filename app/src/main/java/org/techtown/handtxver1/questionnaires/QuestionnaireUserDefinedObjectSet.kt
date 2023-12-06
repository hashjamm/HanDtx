package org.techtown.handtxver1.questionnaires

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.techtown.handtxver1.R
import org.techtown.handtxver1.org.techtown.handtxver1.ApplicationClass
import org.techtown.handtxver1.questionnaires.type2.QuestionnaireType2
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.drinking.DrinkingQuestionnaire
import org.techtown.handtxver1.questionnaires.type3.QuestionnaireType3
import org.techtown.handtxver1.questionnaires.type4.QuestionnaireType4
import org.techtown.handtxver1.questionnaires.type5.QuestionnaireType5
import org.techtown.handtxver1.questionnaires.type6.QuestionnaireType6
import org.techtown.handtxver1.questionnaires.type7.QuestionnaireType7
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type8.QuestionnaireType8
import org.techtown.handtxver1.questionnaires.type1.QuestionnaireType1
import org.techtown.handtxver1.questionnaires.type10.QuestionnaireType10
import org.techtown.handtxver1.questionnaires.type9.QuestionnaireType9
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

    // 제출 완료 버튼 drawable 파일 가져오기
    private fun getSubmitButtonDrawable(context: Context): Drawable? {
        return ContextCompat.getDrawable(context, R.drawable.submit_button)
    }

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

    // 설문 페이지 액티비티 전체에 대해서 기능을 모두 구현하는 함수 생성
    // 1번 설문을 제외한 설문지에서 사용

    fun questionnaireActivityFunction(
        context: AppCompatActivity,
        frameLayoutID: Int,
        pageSequence: Array<Fragment>,
        pageBar: ConstraintLayout,
        presentPageBar: AppCompatImageView,
        pageNumberBox: AppCompatTextView,
        toPreviousPage: AppCompatTextView,
        toNextPage: AppCompatTextView,
        submitButton: AppCompatTextView,
        surveyNumber: Int,
        responseSequence: Array<Int?>,
        checkedStateArray: Array<Int>? = null, // 7번 설문지 13번 질문을 위한 파라미터
        switchActivityPageIndex: Int? = null, // 8번 설문지에서 절주 습관 평가 설문지로 전환하기 위한 파라미터
        newSurveyNumber: Int? = null, // 8번 설문지에서 전환에 필요한 새로운 절주 습관 액티비티 클래스 명 = 클래스 풀 네임을 적어야 함 -> 풀 네임을 적지 않고 코드 번호만 입력하도록 코드를 수정
        snackResponse: Array<String>? = null // 11번 설문지에서 필요
    ) {

        // frameLayoutID = R.id.pageFrame 라는 코드를 함수 외부에서 작성해서 가져올 것

        context.supportFragmentManager.beginTransaction().add(frameLayoutID, pageSequence[0])
            .commitNow()
        pageBarLengthSetting(pageBar, presentPageBar, 1, pageSequence.size)
        pageNumberBoxSetting(pageNumberBox, 1, pageSequence.size)
        buttonDrawableOff(toPreviousPage)

        toNextPage.setOnClickListener {

            val currentPage = context.supportFragmentManager.findFragmentById(frameLayoutID)
            val currentPageIndex = pageSequence.indexOf(currentPage)
            val currentPageNumber = currentPageIndex + 1

            if (switchActivityPageIndex == null || responseSequence[switchActivityPageIndex] != 0) {
                if (currentPageNumber in 1 until pageSequence.size) {

                    val nextPage = pageSequence[currentPageIndex + 1]
                    context.supportFragmentManager.beginTransaction()
                        .replace(frameLayoutID, nextPage)
                        .commitNow()
                    pageBarLengthSetting(
                        pageBar,
                        presentPageBar,
                        currentPageNumber + 1,
                        pageSequence.size
                    )
                    pageNumberBoxSetting(pageNumberBox, currentPageNumber + 1, pageSequence.size)

                    if (currentPageIndex == 0) {
                        buttonDrawableOn(context, toPreviousPage, -1)
                    }

                    if (currentPageNumber + 1 == pageSequence.size) {

                        submitButtonOn(
                            context,
                            submitButton,
                            surveyNumber,
                            responseSequence,
                            checkedStateArray,
                            snackResponse
                        )

                        Log.d("tracking", "$snackResponse")

                        buttonDrawableOff(toNextPage)
                    }

                }
            } else if (switchActivityPageIndex + 1 in 1 until pageSequence.size) {

                if (currentPageIndex == switchActivityPageIndex && responseSequence[switchActivityPageIndex] == 0) {

                    val intent1 = Intent(context, QuestionnaireType1::class.java)
                    val intent2 = Intent(context, QuestionnaireType2::class.java)
                    val intent3 = Intent(context, QuestionnaireType3::class.java)
                    val intent4 = Intent(context, QuestionnaireType4::class.java)
                    val intent5 = Intent(context, QuestionnaireType5::class.java)
                    val intent6 = Intent(context, QuestionnaireType6::class.java)
                    val intent7 = Intent(context, QuestionnaireType7::class.java)
                    val intent8 = Intent(context, QuestionnaireType8::class.java)
                    val intent80 = Intent(context, DrinkingQuestionnaire::class.java)
                    val intent9 = Intent(context, QuestionnaireType9::class.java)
                    val intent10= Intent(context, QuestionnaireType10::class.java)

                    val intentMap = mapOf<Int, Intent>(
                        1 to intent1,
                        2 to intent2,
                        3 to intent3,
                        4 to intent4,
                        5 to intent5,
                        6 to intent6,
                        7 to intent7,
                        8 to intent8,
                        80 to intent80,
                        9 to intent9,
                        10 to intent10
                    )

                    context.startActivity(intentMap[newSurveyNumber]!!)
                }

            }

        }

        toPreviousPage.setOnClickListener {

            val currentPage = context.supportFragmentManager.findFragmentById(frameLayoutID)
            val currentPageIndex = pageSequence.indexOf(currentPage)
            val currentPageNumber = currentPageIndex + 1

            if (currentPageNumber in 2 until pageSequence.size + 1) {

                val previousPage = pageSequence[currentPageIndex - 1]
                context.supportFragmentManager.beginTransaction()
                    .replace(frameLayoutID, previousPage).commitNow()
                pageBarLengthSetting(
                    pageBar,
                    presentPageBar,
                    currentPageNumber - 1,
                    pageSequence.size
                )
                pageNumberBoxSetting(pageNumberBox, currentPageNumber - 1, pageSequence.size)

                if (currentPageIndex == 1) {
                    buttonDrawableOff(toPreviousPage)
                }

                if (currentPageNumber == pageSequence.size) {
                    submitButtonOff(submitButton)
                    buttonDrawableOn(context, toNextPage, 1)
                }

            }

        }

    }

}