package org.techtown.handtxver1.org.techtown.handtxver1.questionnaires

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AlignmentSpan
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.techtown.handtxver1.R
import org.techtown.handtxver1.org.techtown.handtxver1.ApplicationClass
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.drinking.DrinkingQuestionnaire
import org.techtown.handtxver1.questionnaires.type1.QuestionnaireType1
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type2.QuestionnaireType2
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type3.QuestionnaireType3
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type4.QuestionnaireType4
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type5.QuestionnaireType5
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type6.QuestionnaireType6
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type7.QuestionnaireType7
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type8.QuestionnaireType8
import org.techtown.handtxver1.questionnaires.QuestionnaireMainPage
import org.techtown.handtxver1.questionnaires.type10.QuestionnaireType10
import org.techtown.handtxver1.questionnaires.type9.QuestionnaireType9
import java.text.SimpleDateFormat
import java.util.*

class QuestionnaireUserDefinedObjectSet {

    // 각 DB 파일로 사용된 sharedPreferences 에서 userID 밑으로 주요 분류 값으로 사용될 오늘 날짜에 대한 값을 미리 작성
    val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
    val dateToday = dateFormat.format(Calendar.getInstance().time)

    // munuNum 에 따라 아래와 같이 정의
    // 1 : "기분" 텝
    // 2 : "불안" 텝
    // 3 : "식이" 텝
    // 이외 : null

    // json String 으로 encoding 하기 위해 데이터를 묶기 위한 데이터 클래스 생성
    // 유저 로그인 정보
    @Serializable
    data class LoginInfo(
        val userID: String,
        val userPW: String,
        val loginTime: String
    )

    // json String 으로 encoding 하기 위해 데이터를 묶기 위한 데이터 클래스 생성
    // 설문지 부분

    @Serializable
    data class OneSurveyResult(
        @SerialName("results") val results: MutableList<Int>? = null,
        @SerialName("issueComment") val issueComment: String? = null, // 설문 타입 1을 제외하고는 모두 null
        @SerialName("checkedStateArray") val checkedStateArray: MutableList<Int>? = null, // 설문 타입 7을 제외하고는 모두 null
        @SerialName("snackResponse") val snackResponse: MutableList<String>? = null, // 설문 타입 10을 제외하고는 모두 null
    )

    @Serializable
    data class OneDateSurveyData(
        @SerialName("surveyData") val surveyData: Map<Int, OneSurveyResult>? = null
    )

    @Serializable
    data class OneUserSurveyData(
        @SerialName("dates") val dates: Map<String, OneDateSurveyData>? = null
    )

    // 유저의 id와 pw를 Login 클래스에서 LoginInfo sharedPreferences 에 저장하고, 이를 여기에서도 불러오는 함수
    // sharedPreferences 는 user info 를 담고 잇는 sharedPreferences 파일 명을 적어줘야 함
    fun getUserInfo(): LoginInfo? {

        val sharedPreferences = ApplicationClass.loginSharedPreferences

        val jsonUserInfo = sharedPreferences.getString("userInfo", null)

        return if (jsonUserInfo != null) {
            Json.decodeFromString<LoginInfo>(jsonUserInfo)
        } else {
            null
        }

    }

    // QuestionnaireSharedPreferences 에 설문 데이터 결과를 Json String 으로 업데이트하는 함수

    fun updateSurveyData(
        surveyNumber: Int, // 절주 설문지 번호는 금연 설문지(8)에서 연결되기 때문에 80으로 함
        date: String,
        results: MutableList<Int>,
        issueComment: String? = null,
        checkedStateArray: MutableList<Int>? = null,
        snackResponse: MutableList<String>? = null
    ) {

        // sharedPreferences 를 ApplicationClass 에서 가져옴
        val sharedPreferences = ApplicationClass.questionnaireSharedPreferences

        // editor 생성
        val editor = sharedPreferences.edit()

        // userID 가져옴
        val jsonUserInfo = ApplicationClass.loginSharedPreferences.getString("userInfo", null)

        val userID =
            if (jsonUserInfo != null) {
                Json.decodeFromString<LoginInfo>(jsonUserInfo).userID
            } else {
                null
            }

        // QuestionnaireSharedPreferences 파일 내부에서 유저의 기존 데이터를 가져옴
        val getOneUserSurveyData = sharedPreferences.getString(userID, "{}")

        if (getOneUserSurveyData != null && getOneUserSurveyData != "{}") {

            val obtainedOneUserSurveyData =
                Json.decodeFromString<OneUserSurveyData>(getOneUserSurveyData)

            val obtainedOneDateSurveyData = obtainedOneUserSurveyData.dates?.get(date)

            if (obtainedOneDateSurveyData != null) {

                val obtainedOneSurveyResult =
                    obtainedOneDateSurveyData.surveyData?.get(surveyNumber)

                val updateOneSurveyResult =
                    obtainedOneSurveyResult?.copy(
                        results = results,
                        issueComment = issueComment,
                        checkedStateArray = checkedStateArray,
                        snackResponse = snackResponse
                    )
                        ?: OneSurveyResult(
                            results,
                            issueComment,
                            checkedStateArray,
                            snackResponse
                        )

                // 해당 번호의 설문 데이터가 있을 때를 가정하고 있기 때문에 null safe 가 아닌 non null 처리함.
                // 명시해주길 코틀린이 바라고 있었음.

                val updateOneDateSurveyData =
                    obtainedOneDateSurveyData.surveyData!!.toMutableMap()

                updateOneDateSurveyData[surveyNumber] = updateOneSurveyResult

                val classModifiedUpdateOneDateSurveyData =
                    OneDateSurveyData(updateOneDateSurveyData)

                val updateOneUserSurveyData = obtainedOneUserSurveyData.dates.toMutableMap()
                updateOneUserSurveyData[date] = classModifiedUpdateOneDateSurveyData
                val classModifiedUpdateOneUserSurveyData =
                    OneUserSurveyData(updateOneUserSurveyData)

                val jsonUpdateData = Json.encodeToString(classModifiedUpdateOneUserSurveyData)

                editor.putString(userID, jsonUpdateData)
                editor.apply()

            } else {

                val updateOneSurveyData = OneSurveyResult(
                    results,
                    issueComment,
                    checkedStateArray,
                    snackResponse
                )
                val updateOneDateSurveyData =
                    OneDateSurveyData(mapOf(surveyNumber to updateOneSurveyData))

                // 해당 유저의 설문 데이터가 있을 때를 가정하고 있기 때문에 null safe 가 아닌 non null 처리함.
                // 명시해주길 코틀린이 바라고 있었음.

                val updateOneUserSurveyData = obtainedOneUserSurveyData.dates!!.toMutableMap()
                updateOneUserSurveyData[date] = updateOneDateSurveyData
                val classModifiedUpdateOneUserSurveyData =
                    OneUserSurveyData(updateOneUserSurveyData)

                val jsonUpdateData = Json.encodeToString(classModifiedUpdateOneUserSurveyData)

                editor.putString(userID, jsonUpdateData)
                editor.apply()

            }


        } else {

            val updateOneSurveyData = OneSurveyResult(
                results,
                issueComment,
                checkedStateArray,
                snackResponse
            )
            val updateOneDateSurveyData =
                OneDateSurveyData(mapOf(surveyNumber to updateOneSurveyData))
            val updateOneUserSurveyData = OneUserSurveyData(mapOf(date to updateOneDateSurveyData))

            val jsonUpdateData = Json.encodeToString(updateOneUserSurveyData)

            editor.putString(userID, jsonUpdateData)
            editor.apply()

        }


    }

    // QuestionnaireSharedPreferences 에서 특정 설문지의 설문 데이터 결과를 가져오는 함수

    fun getOneSurveyResults(
        surveyNumber: Int,
        date: String
    ): OneSurveyResult? {

        // sharedPreferences 를 ApplicationClass 에서 가져옴
        val sharedPreferences = ApplicationClass.questionnaireSharedPreferences

        // userID 가져옴
        val jsonUserInfo = ApplicationClass.loginSharedPreferences.getString("userInfo", null)

        val userID =
            if (jsonUserInfo != null) {
                Json.decodeFromString<LoginInfo>(jsonUserInfo).userID
            } else {
                null
            }

        // QuestionnaireSharedPreferences 파일 내부에서 유저의 기존 데이터를 가져옴
        val getOneUserSurveyData = sharedPreferences.getString(userID, "{}")

        if (getOneUserSurveyData != null && getOneUserSurveyData != "{}") {

            val obtainedOneUserSurveyData =
                Json.decodeFromString<OneUserSurveyData>(getOneUserSurveyData)

            val obtainedOneDateSurveyData = obtainedOneUserSurveyData.dates?.get(date)

            return if (obtainedOneDateSurveyData != null) {

                obtainedOneDateSurveyData.surveyData?.get(surveyNumber)

            } else {

                null

            }


        } else {

            return null

        }

    }

    // QuestionnaireSharedPreferences 에서 특정 설문지의 설문 데이터 결과를 가져오는 함수

    fun getOneDateSurveyData(
        date: String
    ): OneDateSurveyData? {

        // sharedPreferences 를 ApplicationClass 에서 가져옴
        val sharedPreferences = ApplicationClass.questionnaireSharedPreferences

        // userID 가져옴
        val jsonUserInfo = ApplicationClass.loginSharedPreferences.getString("userInfo", null)

        val userID =
            if (jsonUserInfo != null) {
                Json.decodeFromString<LoginInfo>(jsonUserInfo).userID
            } else {
                null
            }

        // QuestionnaireSharedPreferences 파일 내부에서 유저의 기존 데이터를 가져옴
        val getOneUserSurveyData = sharedPreferences.getString(userID, "{}")

        return if (getOneUserSurveyData != null && getOneUserSurveyData != "{}") {

            val obtainedOneUserSurveyData =
                Json.decodeFromString<OneUserSurveyData>(getOneUserSurveyData)

            val obtainedOneDateSurveyData = obtainedOneUserSurveyData.dates?.get(date)

            obtainedOneDateSurveyData

        } else {

            null

        }

    }

    // 설문 (2번 타입부터)) 각 페이지 컨트롤을 위한 함수 작성

    // 이전페이지로 가는 버튼 drawable 파일 가져오기
    private fun getPreviousPageDrawable(context: Context): Drawable? {
        return ContextCompat.getDrawable(context, R.drawable.previous_page)
    }

    // 이후페이지로 가는 버튼 drawable 파일 가져오기
    private fun getNextPageDrawable(context: Context): Drawable? {
        return ContextCompat.getDrawable(context, R.drawable.next_page)
    }

    // 제출 완료 버튼 drawable 파일 가져오기
    private fun getSubmitButtonDrawable(context: Context): Drawable? {
        return ContextCompat.getDrawable(context, R.drawable.submit_button)
    }

    // 이전 및 이후페이지로 가는 버튼을 활성화하는 함수
    private fun buttonDrawableOn(
        context: Context,
        textView: AppCompatTextView,
        type: Int
    ) {
        textView.background =
            when (type) {
                -1 -> getPreviousPageDrawable(context)
                1 -> getNextPageDrawable(context)
                else -> null
            }

    }

    // 이전 및 이후페이지로 가는 버튼을 비활성화하는 함수
    private fun buttonDrawableOff(textView: AppCompatTextView) {
        textView.setBackgroundColor(Color.parseColor("#00FF0000"))
    }

    // 설문 완료 버튼을 기능과 함께 활성화하는 함수
    // 1번 설문을 제외한 설문지에서 사용

    private fun submitButtonOn(
        context: Context,
        textView: AppCompatTextView,
        surveyNumber: Int,
        responseSequence: Array<Int?>,
        checkedStateArray: Array<Int>? = null,
        snackResponse: Array<String>? = null
    ) {
        textView.background = getSubmitButtonDrawable(context)

        textView.setText("제출 완료")

        textView.setOnClickListener {

            if (responseSequence.any { it == null }) {

                val nullIndices = responseSequence.indices.filter { responseSequence[it] == null }
                val missingQuestions =
                    nullIndices.map { (it + 1).toString() + "번" }.joinToString(", ", "", " 질문")

                // 기획팀 요청 : 팝업 메세지 중앙 정렬을 위한 spannableStringBuilder 객체 사용 코드
                val title = "작성되지 않은 문항이 있습니다."
                val message = "모든 문항에 대하여 응답해주십시오. \n $missingQuestions"

                val spannableStringBuilderTitle = SpannableStringBuilder(title)
                spannableStringBuilderTitle.setSpan(
                    AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                    0,
                    title.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                val spannableStringBuilderMessage = SpannableStringBuilder(message)
                spannableStringBuilderMessage.setSpan(
                    AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                    0,
                    message.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                val dialogOfResponses = AlertDialog.Builder(context)
                    .setTitle(spannableStringBuilderTitle)
                    .setMessage(spannableStringBuilderMessage)

                dialogOfResponses.show()

            } else {

                val messageBuilder = StringBuilder()

                for (i in responseSequence.indices) {
                    val response = responseSequence[i]
                    messageBuilder.append("${i + 1}번 : $response\n")
                }

                Log.d("here3", "${responseSequence.toMutableList()}")
                snackResponse?.joinToString(", ")?.let { it1 -> Log.d("here4", it1) }

                val dialogOfResponses = AlertDialog.Builder(context)
                    .setTitle("응답한 내용")
                    .setMessage(messageBuilder.toString())
                    .setPositiveButton("완료") { _, _ ->
                        Toast.makeText(context, "설문을 완료하였습니다.", Toast.LENGTH_SHORT).show()

                        val intent = Intent(context, QuestionnaireMainPage::class.java)

                        Log.d("tracking2", "$snackResponse")

                        updateSurveyData(
                            surveyNumber,
                            dateToday,
                            responseSequence.filterNotNull().toMutableList(),
                            null,
                            checkedStateArray?.toMutableList(),
                            snackResponse?.toMutableList()
                        )

                        context.startActivity(intent)

                    }
                    .setNeutralButton("수정", null)

                dialogOfResponses.show()

            }

        }
    }

    // 설문 완료 버튼을 비활성화하는 함수

    private fun submitButtonOff(textView: AppCompatTextView) {
        textView.setBackgroundColor(Color.parseColor("#00FF0000"))
        textView.setText("")
        textView.setOnClickListener(null)
    }

    // pageBar 를 동적으로 설정해주기 위한 함수

    private fun pageBarLengthSetting(
        pageBar: ConstraintLayout,
        presentPageBar: androidx.appcompat.widget.AppCompatImageView,
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

    private fun pageNumberBoxSetting(
        pageNumberBox: AppCompatTextView,
        pageNum: Int,
        wholePageNum: Int
    ) {
        pageNumberBox.setText("$pageNum of $wholePageNum")
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