package org.techtown.handtxver1.org.techtown.handtxver1.questionnaires

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Layout
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AlignmentSpan
import android.util.Log
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.techtown.handtxver1.*
import org.techtown.handtxver1.org.techtown.handtxver1.CommonUserDefinedObjectSet
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type1.QuestionnaireType1
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type2.QuestionnaireType2
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type3.QuestionnaireType3
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type4.QuestionnaireType4
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type5.QuestionnaireType5
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type6.QuestionnaireType6
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type7.QuestionnaireType7
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type8.QuestionnaireType8

class QuestionnaireMainPage : AppCompatActivity() {

    // QuestionnairePage1 에서 각 설문지에서의 체크박스 선택 점수 합산을 저장하기 위한 SharedPreferences 인스턴스를 선언
    // 반드시 선언만 할 것.

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_page1)

        // CommonUserDefinedObjectSet 클래스 인스턴스 생성
        val commonUserDefinedObjectSet = CommonUserDefinedObjectSet()

        // 체크상태 표기
        for (typeNumber in 1..8) {

            val scoreOfType = commonUserDefinedObjectSet.getOneSurveyResults(typeNumber, commonUserDefinedObjectSet.dateToday)

            val viewId = resources.getIdentifier("qTypeCheckBox$typeNumber", "id", packageName)
            val buttonBox = findViewById<RadioButton>(viewId)

            buttonBox.isEnabled = false

            // 찾은 textView 를 사용하여 원하는 작업을 수행

            if (typeNumber == 8) {
                val scoreOfDrinkingQuestionnaire = commonUserDefinedObjectSet.getOneSurveyResults(80, commonUserDefinedObjectSet.dateToday)

                if (scoreOfType == null && scoreOfDrinkingQuestionnaire == null) {
                    buttonBox.isChecked = false
                } else {
                    buttonBox.isChecked
                }
            } else {
                if (scoreOfType == null) {
                    buttonBox.isChecked = false

                    Log.d("aaaa", "$scoreOfType")
                } else {
                    buttonBox.isChecked
                }
            }
        }

        // 메뉴바 fragment 불러옴
        // 메뉴바 fragment 에 대한 beginTransaction
        val menuBar = BottomMenuBar(4)
        supportFragmentManager.beginTransaction().add(R.id.menuBar, menuBar).commit()

        // 각 QuestionnaireType 으로 이동하는 TextView 들을 미리 불러옴
        val toQuestionnaireType1 =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox1)
        val toQuestionnaireType2 =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox2)
        val toQuestionnaireType3 =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox3)
        val toQuestionnaireType4 =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox4)
        val toQuestionnaireType5 =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox5)
        val toQuestionnaireType6 =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox6)
        val toQuestionnaireType7 =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox7)
        val toQuestionnaireType8 =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox8)

        val toQuestionnaireArray = arrayOf(
            toQuestionnaireType1,
            toQuestionnaireType2,
            toQuestionnaireType3,
            toQuestionnaireType4,
            toQuestionnaireType5,
            toQuestionnaireType6,
            toQuestionnaireType7,
            toQuestionnaireType8
        )

        val questionnaireNameMap = mapOf(
            1 to "정신건강 관련 나의 관심 이슈 고르기",
            2 to "자가진단 테스트",
            3 to "웰빙 척도 검사",
            4 to "우울 검사 (PHQ-9)",
            5 to "불안 검사 (GAD-7)",
            6 to "스트레스 검사 (PSS-10)",
            7 to "건강관리 문진표 (운동)",
            8 to "건강관리 문진표 (금연&절주)"
        )

        // 이미 완료한 설문을 다시 클릭했을 때, 팝업창을 띄우고 긍정과 취소 버튼을 누를 때 발생시킬 이벤트를 설정하는
        // 코드가 재사용이 많이 될 예정이라 이를 함수로 만들어 둠.
        fun startActivityWithAlert(intent: Intent, alertPreviousSurveyCompleted: Boolean = true, clickedSurveyNumber: Int? = null) {

            if (alertPreviousSurveyCompleted) {
                val negativeAnswer = "아니요"
                val positiveAnswer = "네"

                val spannableStringBuilderNo = SpannableStringBuilder(negativeAnswer)
                spannableStringBuilderNo.setSpan(
                    AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                    0,
                    negativeAnswer.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                val spannableStringBuilderYes = SpannableStringBuilder(positiveAnswer)
                spannableStringBuilderYes.setSpan(
                    AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                    0,
                    positiveAnswer.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                val scoreResetPopup = AlertDialog.Builder(this)
                    .setTitle("이미 설문에 응답하였습니다.")
                    .setMessage("다시 설문을 진행하시겠습니까?") // 초기화 하지는 않고 뷰만 초기화되기 때문에 그냥 다시 보겠냐고만 묻기
                    .setNeutralButton(spannableStringBuilderNo, null)
                    .setPositiveButton(spannableStringBuilderYes) { _, _ ->
                        startActivity(intent)

                        Toast.makeText(this, "설문을 다시 진행합니다", Toast.LENGTH_SHORT).show()
                    }

                scoreResetPopup.show()

            } else {

                if (clickedSurveyNumber!! > 1) {
                    val scoreResetPopup = AlertDialog.Builder(this)
                        .setTitle("이전 설문이 완료되지 않았습니다 :")
                        .setMessage("${questionnaireNameMap[clickedSurveyNumber - 1]}")

                    scoreResetPopup.show()
                }

            }

        }

        // 각 설문지 타입으로 넘어가는 텍스트뷰들에 대한 리스너 코드
        toQuestionnaireArray.forEachIndexed { index, view ->

            val surveyNumber = index + 1
            val date = commonUserDefinedObjectSet.dateToday

            val destinationNormalQuestionnaireName = "type$surveyNumber.QuestionnaireType$surveyNumber"
            val drinkingQuestionnaireName = "drinking.DrinkingQuestionnaire"
            val additionalPath = "org.techtown.handtxver1.org.techtown.handtxver1.questionnaires."

            val intent = Intent(this, Class.forName(additionalPath + destinationNormalQuestionnaireName))

            val result = commonUserDefinedObjectSet.getOneSurveyResults(surveyNumber, date)

            view.setOnClickListener{

                if (surveyNumber == 1) {

                    if (result == null) {
                        startActivity(intent)
                    } else {
                        startActivityWithAlert(intent)
                    }

                } else if (surveyNumber == 8) {
                    val previousSurveyResult = commonUserDefinedObjectSet.getOneSurveyResults(surveyNumber - 1, date)
                    val drinkingResult = commonUserDefinedObjectSet.getOneSurveyResults(80, date)

                    if (result == null && drinkingResult == null) {
                        startActivity(intent)
                    } else if (previousSurveyResult == null){
                        startActivityWithAlert(intent, false, surveyNumber)
                    } else {
                        startActivityWithAlert(intent)
                    }

                } else {
                    val previousSurveyResult = commonUserDefinedObjectSet.getOneSurveyResults(surveyNumber - 1, date)

                    if (result == null) {
                        startActivity(intent)
                    } else if (previousSurveyResult == null){
                        startActivityWithAlert(intent, false, surveyNumber)
                    } else {
                        startActivityWithAlert(intent)
                    }
                }

            }

        }
    }


    override fun onResume() {
        super.onResume()

        val menuBar = BottomMenuBar(4)
        supportFragmentManager.beginTransaction().add(R.id.menuBar, menuBar).commit()

    }
}


