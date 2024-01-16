package org.techtown.handtxver1.questionnaires

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
import androidx.lifecycle.ViewModelProvider
import org.techtown.handtxver1.BottomMenuBar
import org.techtown.handtxver1.R
import org.techtown.handtxver1.questionnaires.results.QuestionnaireResultSummary
import org.techtown.handtxver1.questionnaires.type1.QuestionnaireType1
import org.techtown.handtxver1.questionnaires.type10.QuestionnaireType10
import org.techtown.handtxver1.questionnaires.type2.QuestionnaireType2
import org.techtown.handtxver1.questionnaires.type3.QuestionnaireType3
import org.techtown.handtxver1.questionnaires.type4.QuestionnaireType4
import org.techtown.handtxver1.questionnaires.type5.QuestionnaireType5
import org.techtown.handtxver1.questionnaires.type6.QuestionnaireType6
import org.techtown.handtxver1.questionnaires.type7.QuestionnaireType7
import org.techtown.handtxver1.questionnaires.type8.QuestionnaireType8
import org.techtown.handtxver1.questionnaires.type9.QuestionnaireType9

class QuestionnaireMainPage : AppCompatActivity() {

    // CommonUserDefinedObjectSet 클래스 인스턴스 생성
    val objectSet = QuestionnaireUserDefinedObjectSet()

    // 체크상태 표기

    private val checkBox1 = findViewById<RadioButton>(R.id.qTypeCheckBox1)
    private val checkBox2 = findViewById<RadioButton>(R.id.qTypeCheckBox2)
    private val checkBox3 = findViewById<RadioButton>(R.id.qTypeCheckBox3)
    private val checkBox4 = findViewById<RadioButton>(R.id.qTypeCheckBox4)
    private val checkBox5 = findViewById<RadioButton>(R.id.qTypeCheckBox5)
    private val checkBox6 = findViewById<RadioButton>(R.id.qTypeCheckBox6)
    private val checkBox7 = findViewById<RadioButton>(R.id.qTypeCheckBox7)
    private val checkBox8 = findViewById<RadioButton>(R.id.qTypeCheckBox8)
    private val checkBox9 = findViewById<RadioButton>(R.id.qTypeCheckBox9)
    private val checkBox10 = findViewById<RadioButton>(R.id.qTypeCheckBox10)
    private val checkBox11 = findViewById<RadioButton>(R.id.qTypeCheckBox11)

    // 각 QuestionnaireType 으로 이동하는 TextView 들을 미리 불러옴
    private val toQuestionnaireType1 =
        findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox1)
    private val toQuestionnaireType2 =
        findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox2)
    private val toQuestionnaireType3 =
        findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox3)
    private val toQuestionnaireType4 =
        findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox4)
    private val toQuestionnaireType5 =
        findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox5)
    private val toQuestionnaireType6 =
        findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox6)
    private val toQuestionnaireType7 =
        findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox7)
    private val toQuestionnaireType8 =
        findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox8)
    private val toQuestionnaireType9 =
        findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox9)
    private val toQuestionnaireType10 =
        findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox10)
    private val toQuestionnaireResult =
        findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.qTypeBox11)

    // 메뉴바 fragment 불러옴
    val menuBar = BottomMenuBar(4)

    // 각 설문지 타입으로 넘어가는 텍스트뷰들에 대한 리스너 코드
    // 인텐트에 실어줄 액티비티 이름을 string 을 사용하여 Class.forName 매서드를 통해 지정함에 있어서
    // 전체 주소를 입력해줘야하는 문제가 있음
    // 보다 일반적인 코드를 위하여 intentMap 을 사용하는 코드로 변경

    private val intent1 = Intent(this, QuestionnaireType1::class.java)
    private val intent2 = Intent(this, QuestionnaireType2::class.java)
    private val intent3 = Intent(this, QuestionnaireType3::class.java)
    private val intent4 = Intent(this, QuestionnaireType4::class.java)
    private val intent5 = Intent(this, QuestionnaireType5::class.java)
    private val intent6 = Intent(this, QuestionnaireType6::class.java)
    private val intent7 = Intent(this, QuestionnaireType7::class.java)
    private val intent8 = Intent(this, QuestionnaireType8::class.java)
    private val intent9 = Intent(this, QuestionnaireType9::class.java)
    private val intent10 = Intent(this, QuestionnaireType10::class.java)
    private val intent11 = Intent(this, QuestionnaireResultSummary::class.java)

    private val checkBoxes = arrayOf(
        checkBox1,
        checkBox2,
        checkBox3,
        checkBox4,
        checkBox5,
        checkBox6,
        checkBox7,
        checkBox8,
        checkBox9,
        checkBox10,
        checkBox11
    )

    private val toQuestionnaireArray = arrayOf(
        toQuestionnaireType1,
        toQuestionnaireType2,
        toQuestionnaireType3,
        toQuestionnaireType4,
        toQuestionnaireType5,
        toQuestionnaireType6,
        toQuestionnaireType7,
        toQuestionnaireType8,
        toQuestionnaireType9,
        toQuestionnaireType10,
        toQuestionnaireResult
    )

    private val questionnaireNameArray = arrayOf(
        "정신건강 관련 나의 관심 이슈 고르기",
        "자가진단 테스트",
        "웰빙 척도 검사",
        "우울 검사 (PHQ-9)",
        "불안 검사 (GAD-7)",
        "스트레스 검사 (PSS-10)",
        "건강관리 문진표 (운동)",
        "건강관리 문진표 (금연&절주)",
        "건강관리 문진표 (스트레스)",
        "건강관리 문진표 (영양)",
        "건강관리 문진표 종합 결과"
    )

    private val intentArray = arrayOf(
        intent1,
        intent2,
        intent3,
        intent4,
        intent5,
        intent6,
        intent7,
        intent8,
        intent9,
        intent10,
        intent11
    )

    // ViewModel 에 접근 및 로딩
    val viewModel = ViewModelProvider(this)[ViewModelForQMain::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_page1)

        // 메뉴바 fragment 에 대한 beginTransaction
        supportFragmentManager.beginTransaction().add(R.id.menuBar, menuBar).commit()

        viewModel.fetchData()

        fun errorPopupDuringGetData(message:String?) {
            val errorDialog = AlertDialog.Builder(this)
            errorDialog.setTitle("통신 오류")
            errorDialog.setMessage("설문 데이터를 가져올 수 없습니다 : $message")
            errorDialog.show()
        }

        if (viewModel.networkFailureCounting > 0) {
            errorPopupDuringGetData(viewModel.networkFailureMessage)
        }

        val surveyDataArray = arrayOf(
            viewModel.issueCheckingSurveyData,
            viewModel.selfDiagnosisSurveyData,
            viewModel.wellBeingScaleSurveyData,
            viewModel.phq9SurveyData,
            viewModel.gad7SurveyData,
            viewModel.pss10SurveyData,
            viewModel.exerciseSurveyData,
            viewModel.smokingDrinkingSurveyData,
            viewModel.stressSurveyData,
            viewModel.nutritionSurveyData
        )

        checkBoxes.forEachIndexed { index, buttonBox ->

            if (index != checkBoxes.size - 1) {

                buttonBox.isEnabled = false

                buttonBox.isChecked = surveyDataArray[index] != null

            }

        }

        fun scoreResetPopup(index: Int) {

            if (0 <= index && index < surveyDataArray.size) {

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

                val popup = AlertDialog.Builder(this)
                    .setTitle("오늘 이미 설문에 응답하였습니다.")
                    .setMessage("다시 설문을 진행하시겠습니까?") // 초기화 하지는 않고 뷰만 초기화되기 때문에 그냥 다시 보겠냐고만 묻기
                    .setNeutralButton(spannableStringBuilderNo, null)
                    .setPositiveButton(spannableStringBuilderYes) { _, _ ->
                        startActivity(intentArray[index])

                        Toast.makeText(this, "설문을 다시 진행합니다", Toast.LENGTH_SHORT).show()
                    }

                popup.show()

            } else {

                throw ArrayIndexOutOfBoundsException("index range error : scoreResetPopup")

            }
        }

        fun previousSurveyAlertPopup(index: Int) {

            if (0 < index && index < surveyDataArray.size) {

                val message = questionnaireNameArray[index - 1]

                val centeredMessage = SpannableStringBuilder(message)
                centeredMessage.setSpan(
                    AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                    0,
                    centeredMessage.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                val popup = AlertDialog.Builder(this)
                    .setTitle("오늘의 이전 설문이 완료되지 않았습니다 :")
                    .setMessage(centeredMessage)

                popup.show()

            } else {

                throw ArrayIndexOutOfBoundsException("index range error : PreviousSurveyAlertPopup")

            }
        }


        // 이미 완료한 설문을 다시 클릭했을 때, 팝업창을 띄우고 긍정과 취소 버튼을 누를 때 발생시킬 이벤트를 설정하는
        // 코드가 재사용이 많이 될 예정이라 이를 함수로 만들어 둠.
        fun startActivityWithAlert(
            index: Int
        ) {

            val currentSurveyData =
                if (0 <= index && index < surveyDataArray.size) {
                    surveyDataArray[index]
                } else {
                    null
                }

            val previousSurveyData =
                if (0 < index && index <= surveyDataArray.size) {
                    surveyDataArray[index - 1]
                } else {
                    null
                }

            if (0 < index && index < surveyDataArray.size) {

                if (previousSurveyData != null) {

                    if (currentSurveyData != null) {

                        scoreResetPopup(index)

                    } else {

                        startActivity(intentArray[index])

                    }

                } else {

                    previousSurveyAlertPopup(index)

                }

            } else if (index == 0) {

                if (currentSurveyData != null) {

                    scoreResetPopup(index)

                } else {

                    startActivity(intentArray[index])

                }

            } else if (index == surveyDataArray.size) {

                if (previousSurveyData != null) {

                    startActivity(intentArray[index])

                } else {

                    previousSurveyAlertPopup(index)

                }

            } else {

                throw ArrayIndexOutOfBoundsException("index range error")

            }

        }

        toQuestionnaireArray.forEachIndexed { index, view ->

            view.setOnClickListener {

                startActivityWithAlert(index)

            }

        }

    }


    override fun onResume() {
        super.onResume()

        supportFragmentManager.beginTransaction().add(R.id.menuBar, menuBar).commit()

        // 다른 페이지에서 뒤로가기 버튼을 눌렀을 때, 만약 날짜가 바뀐 경우라면 해당 fetchData 매서드 적용이 필요
        viewModel.fetchData()

        val surveyDataArray = arrayOf(
            viewModel.issueCheckingSurveyData,
            viewModel.selfDiagnosisSurveyData,
            viewModel.wellBeingScaleSurveyData,
            viewModel.phq9SurveyData,
            viewModel.gad7SurveyData,
            viewModel.pss10SurveyData,
            viewModel.exerciseSurveyData,
            viewModel.smokingDrinkingSurveyData,
            viewModel.stressSurveyData,
            viewModel.nutritionSurveyData
        )

        checkBoxes.forEachIndexed { index, buttonBox ->

            if (index != checkBoxes.size - 1) {

                buttonBox.isEnabled = false

                buttonBox.isChecked = surveyDataArray[index] != null

            }

        }

    }

}


