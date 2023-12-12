package org.techtown.handtxver1.questionnaires

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AlignmentSpan
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.techtown.handtxver1.BottomMenuBar
import org.techtown.handtxver1.R
import org.techtown.handtxver1.questionnaires.type1.QuestionnaireType1
import org.techtown.handtxver1.questionnaires.type2.QuestionnaireType2
import org.techtown.handtxver1.questionnaires.type3.QuestionnaireType3
import org.techtown.handtxver1.questionnaires.type4.QuestionnaireType4
import org.techtown.handtxver1.questionnaires.type5.QuestionnaireType5
import org.techtown.handtxver1.questionnaires.type6.QuestionnaireType6
import org.techtown.handtxver1.questionnaires.type7.QuestionnaireType7
import org.techtown.handtxver1.questionnaires.type8.QuestionnaireType8
import org.techtown.handtxver1.questionnaires.type10.QuestionnaireType10
import org.techtown.handtxver1.questionnaires.type9.QuestionnaireType9

class QuestionnaireMainPage : AppCompatActivity() {

    // QuestionnairePage1 에서 각 설문지에서의 체크박스 선택 점수 합산을 저장하기 위한 SharedPreferences 인스턴스를 선언
    // 반드시 선언만 할 것.

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_page1)

//        ApplicationClass.questionnaireSharedPreferences.edit().clear().apply()

        // CommonUserDefinedObjectSet 클래스 인스턴스 생성
        val objectSet = QuestionnaireUserDefinedObjectSet()

        // 체크상태 표기

        val checkBox1 = findViewById<RadioButton>(R.id.qTypeCheckBox1)
        val checkBox2 = findViewById<RadioButton>(R.id.qTypeCheckBox2)
        val checkBox3 = findViewById<RadioButton>(R.id.qTypeCheckBox3)
        val checkBox4 = findViewById<RadioButton>(R.id.qTypeCheckBox4)
        val checkBox5 = findViewById<RadioButton>(R.id.qTypeCheckBox5)
        val checkBox6 = findViewById<RadioButton>(R.id.qTypeCheckBox6)
        val checkBox7 = findViewById<RadioButton>(R.id.qTypeCheckBox7)
        val checkBox8 = findViewById<RadioButton>(R.id.qTypeCheckBox8)
        val checkBox9 = findViewById<RadioButton>(R.id.qTypeCheckBox9)
        val checkBox10 = findViewById<RadioButton>(R.id.qTypeCheckBox10)

        val checkBoxes = arrayOf(
            checkBox1,
            checkBox2,
            checkBox3,
            checkBox4,
            checkBox5,
            checkBox6,
            checkBox7,
            checkBox8,
            checkBox9,
            checkBox10
        )

        checkBoxes.forEachIndexed { index, buttonBox ->

            val typeNumber = index + 1
            val scoreOfType = objectSet.getOneSurveyResults(typeNumber, objectSet.dateToday)

            buttonBox.isEnabled = false

            if (typeNumber == 8) {
                val scoreOfDrinkingQuestionnaire = objectSet.getOneSurveyResults(80, objectSet.dateToday)

                buttonBox.isChecked = !(scoreOfType == null && scoreOfDrinkingQuestionnaire == null)
            } else {
                buttonBox.isChecked = scoreOfType != null
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
        val toQuestionnaireType9 =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox9)
        val toQuestionnaireType10 =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox10)

        val toQuestionnaireArray = arrayOf(
            toQuestionnaireType1,
            toQuestionnaireType2,
            toQuestionnaireType3,
            toQuestionnaireType4,
            toQuestionnaireType5,
            toQuestionnaireType6,
            toQuestionnaireType7,
            toQuestionnaireType8,
            toQuestionnaireType9,
            toQuestionnaireType10
        )

        val questionnaireNameMap = mapOf(
            1 to "정신건강 관련 나의 관심 이슈 고르기",
            2 to "자가진단 테스트",
            3 to "웰빙 척도 검사",
            4 to "우울 검사 (PHQ-9)",
            5 to "불안 검사 (GAD-7)",
            6 to "스트레스 검사 (PSS-10)",
            7 to "건강관리 문진표 (운동)",
            8 to "건강관리 문진표 (금연&절주)",
            9 to "건강관리 문진표 (스트레스)",
            10 to "건강관리 문진표 (영양)"
        )

        // 이미 완료한 설문을 다시 클릭했을 때, 팝업창을 띄우고 긍정과 취소 버튼을 누를 때 발생시킬 이벤트를 설정하는
        // 코드가 재사용이 많이 될 예정이라 이를 함수로 만들어 둠.
        fun startActivityWithAlert(intent: Intent, PreviousSurveyCompleted: Boolean = true, clickedSurveyNumber: Int? = null) {

            if (PreviousSurveyCompleted) {
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
                    .setTitle("오늘 이미 설문에 응답하였습니다.")
                    .setMessage("다시 설문을 진행하시겠습니까?") // 초기화 하지는 않고 뷰만 초기화되기 때문에 그냥 다시 보겠냐고만 묻기
                    .setNeutralButton(spannableStringBuilderNo, null)
                    .setPositiveButton(spannableStringBuilderYes) { _, _ ->
                        startActivity(intent)

                        Toast.makeText(this, "설문을 다시 진행합니다", Toast.LENGTH_SHORT).show()
                    }

                scoreResetPopup.show()

            } else {

                if (clickedSurveyNumber!! > 1) {

                    val message = "${questionnaireNameMap[clickedSurveyNumber - 1]}"

                    val centeredMessage = SpannableStringBuilder(message)
                    centeredMessage.setSpan(
                        AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0,
                        centeredMessage.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    val scoreResetPopup = AlertDialog.Builder(this)
                        .setTitle("오늘의 이전 설문이 완료되지 않았습니다 :")
                        .setMessage(centeredMessage)

                    scoreResetPopup.show()
                }

            }

        }

        // 각 설문지 타입으로 넘어가는 텍스트뷰들에 대한 리스너 코드
        // 인텐트에 실어줄 액티비티 이름을 string 을 사용하여 Class.forName 매서드를 통해 지정함에 있어서
        // 전체 주소를 입력해줘야하는 문제가 있음
        // 보다 일반적인 코드를 위하여 intentMap 을 사용하는 코드로 변경

        val intent1 = Intent(this, QuestionnaireType1::class.java)
        val intent2 = Intent(this, QuestionnaireType2::class.java)
        val intent3 = Intent(this, QuestionnaireType3::class.java)
        val intent4 = Intent(this, QuestionnaireType4::class.java)
        val intent5 = Intent(this, QuestionnaireType5::class.java)
        val intent6 = Intent(this, QuestionnaireType6::class.java)
        val intent7 = Intent(this, QuestionnaireType7::class.java)
        val intent8 = Intent(this, QuestionnaireType8::class.java)
        val intent9 = Intent(this, QuestionnaireType9::class.java)
        val intent10 = Intent(this, QuestionnaireType10::class.java)

        val intentMap = mapOf<Int, Intent>(
            1 to intent1,
            2 to intent2,
            3 to intent3,
            4 to intent4,
            5 to intent5,
            6 to intent6,
            7 to intent7,
            8 to intent8,
            9 to intent9,
            10 to intent10
        )

        toQuestionnaireArray.forEachIndexed { index, view ->

            val surveyNumber = index + 1
            val date = objectSet.dateToday

            val intent = intentMap[surveyNumber]

            val result = objectSet.getOneSurveyResults(surveyNumber, date)

            view.setOnClickListener{

                if (surveyNumber == 1) {

                    if (result == null) {
                        startActivity(intent)
                    } else {
                        startActivityWithAlert(intent!!)
                    }

                } else if (surveyNumber == 8) {
                    val previousSurveyResult = objectSet.getOneSurveyResults(surveyNumber - 1, date)
                    val drinkingResult = objectSet.getOneSurveyResults(80, date)

                    if (result == null && drinkingResult == null) {

                        if (previousSurveyResult == null) {
                            startActivityWithAlert(intent!!, false, surveyNumber)
                        } else {
                            startActivity(intent)
                        }

                    } else if (previousSurveyResult == null){
                        startActivityWithAlert(intent!!, false, surveyNumber)
                    } else {
                        startActivityWithAlert(intent!!)
                    }

                } else if (surveyNumber == 9) {
                    val previousSurveyResult = objectSet.getOneSurveyResults(surveyNumber - 1, date)
                    val drinkingResult = objectSet.getOneSurveyResults(80, date)

                    if (result == null) {

                        if (previousSurveyResult == null && drinkingResult == null) {
                            startActivityWithAlert(intent!!, false, surveyNumber)
                        } else {
                            startActivity(intent)
                        }

                    } else if (previousSurveyResult == null && drinkingResult == null){

                        startActivityWithAlert(intent!!, false, surveyNumber)

                    } else {

                        startActivityWithAlert(intent!!)

                    }

                } else {
                    val previousSurveyResult = objectSet.getOneSurveyResults(surveyNumber - 1, date)

                    if (result == null) {

                        if (previousSurveyResult == null) {
                            startActivityWithAlert(intent!!, false, surveyNumber)
                        } else {
                            startActivity(intent)
                        }

                    } else if (previousSurveyResult == null){

                        startActivityWithAlert(intent!!, false, surveyNumber)

                    } else {

                        startActivityWithAlert(intent!!)

                    }
                }

            }

        }
    }


    override fun onResume() {
        super.onResume()

        val menuBar = BottomMenuBar(4)
        supportFragmentManager.beginTransaction().add(R.id.menuBar, menuBar).commit()

        // CommonUserDefinedObjectSet 클래스 인스턴스 생성
        val objectSet = QuestionnaireUserDefinedObjectSet()

        // 체크상태 표기

        val checkBox1 = findViewById<RadioButton>(R.id.qTypeCheckBox1)
        val checkBox2 = findViewById<RadioButton>(R.id.qTypeCheckBox2)
        val checkBox3 = findViewById<RadioButton>(R.id.qTypeCheckBox3)
        val checkBox4 = findViewById<RadioButton>(R.id.qTypeCheckBox4)
        val checkBox5 = findViewById<RadioButton>(R.id.qTypeCheckBox5)
        val checkBox6 = findViewById<RadioButton>(R.id.qTypeCheckBox6)
        val checkBox7 = findViewById<RadioButton>(R.id.qTypeCheckBox7)
        val checkBox8 = findViewById<RadioButton>(R.id.qTypeCheckBox8)
        val checkBox9 = findViewById<RadioButton>(R.id.qTypeCheckBox9)
        val checkBox10 = findViewById<RadioButton>(R.id.qTypeCheckBox10)

        val checkBoxes = arrayOf(
            checkBox1,
            checkBox2,
            checkBox3,
            checkBox4,
            checkBox5,
            checkBox6,
            checkBox7,
            checkBox8,
            checkBox9,
            checkBox10
        )

        checkBoxes.forEachIndexed { index, buttonBox ->

            val typeNumber = index + 1
            val scoreOfType = objectSet.getOneSurveyResults(typeNumber, objectSet.dateToday)

            buttonBox.isEnabled = false

            if (typeNumber == 8) {
                val scoreOfDrinkingQuestionnaire = objectSet.getOneSurveyResults(80, objectSet.dateToday)

                buttonBox.isChecked = !(scoreOfType == null && scoreOfDrinkingQuestionnaire == null)
            } else {
                buttonBox.isChecked = scoreOfType != null
            }
        }

    }
}


