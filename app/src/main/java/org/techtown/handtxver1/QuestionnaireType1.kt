package org.techtown.handtxver1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import org.techtown.handtxver1.org.techtown.handtxver1.CommonUserDefinedObjectSet

class QuestionnaireType1 : AppCompatActivity() {

    // 체크박스 배열 변수 선언, 작업해줄 내용을 하나의 함수로 지정하고 인스턴스를 직접사용하는 것이 필요한 경우를 파라미터로 지정해서 작성

    private lateinit var checkBoxes: Array<CheckBox>

    // 제출 완료 버튼을 누름과 동시에 QuestionnaireSharedPreferences 에 전송해줄 설문 결과 데이터 인스턴스 선언

    private lateinit var surveyResults: MutableList<Int>

    // CommonUserDefinedObjectSet 데이터 클래스 인스턴스를 가져옴

    private val commonUserDefinedObjectSet = CommonUserDefinedObjectSet()

    // sharedPreferences 를 선언만 함 -> 이후에 onCreate 와 onResume 에서 초기화
    // Int 타입인 checkSumChange 는 선언만 해둘 수가 없어서 우선 초기화를 해두었으나 onCreate 와 onResume 에서 다시 초기화해줄 예정

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_type1)

        // sharedPreferences 초기화
        sharedPreferences = getSharedPreferences(
            "QuestionnaireSharedPreference",
            Context.MODE_PRIVATE
        )

        // editor 생성
        // val editor = sharedPreferences.edit() -> commonUserDefinedObjectSet.updateSurveyData 내부에 에디터 있음

        // editText 뷰 인스턴스 생성
        val editTextBox = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.box22_text)

        // checkBoxes 초기화
        checkBoxes = arrayOf(
            findViewById(R.id.box1),
            findViewById(R.id.box2),
            findViewById(R.id.box3),
            findViewById(R.id.box4),
            findViewById(R.id.box5),
            findViewById(R.id.box6),
            findViewById(R.id.box7),
            findViewById(R.id.box8),
            findViewById(R.id.box9),
            findViewById(R.id.box10),
            findViewById(R.id.box11),
            findViewById(R.id.box12),
            findViewById(R.id.box13),
            findViewById(R.id.box14),
            findViewById(R.id.box15),
            findViewById(R.id.box16),
            findViewById(R.id.box17),
            findViewById(R.id.box18),
            findViewById(R.id.box19),
            findViewById(R.id.box20),
            findViewById(R.id.box21),
            findViewById(R.id.box22)
        )

        // surveyResults 초기화
        // 사이즈가 22개이고 모든 요소를 0으로 초기화해두어 생성
        // 어차피 모든 체크박스를 체크 해제된 상태로 초기화할 것이라 문제 없음.
        surveyResults = MutableList(22) { 0 }

        // 해당 설문지 액티비티에 새롭게 들어온 순간 모든 체크박스 상태 초기화

        checkBoxes.forEach {
            it.isChecked = false
        }

        checkBoxes.forEachIndexed { index, checkBox ->
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    surveyResults[index] = 1
                } else {
                    surveyResults[index] = 0
                }
            }
        }

        editTextBox.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            // 우리는 Text 가 입력되는 동안, text 입력 여부를 바탕으로 체크박스 체트 상태를 동적으로 변경
            // 또한 commonUserDefinedObject 의 oneSurveyResult 데이터 클래스의 내용을 업데이트

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val checkBoxText = editTextBox.text.toString()

                // 22번째 텍스트 입력내용이 비는 경우 체크박스 해제, 무엇이라도 입력되면 체크 상태 적용
                checkBoxes[21].isChecked = checkBoxText.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        val submitButton =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.submitButton)

        submitButton.setOnClickListener {
            val intent = Intent(this, QuestionnaireMainPage::class.java)
            startActivity(intent)

            val checkBoxText = editTextBox.text.toString()

            commonUserDefinedObjectSet.updateSurveyData(
                surveyResults,
                checkBoxText,
                1,
                commonUserDefinedObjectSet.dateToday,
                sharedPreferences
            )

        }
    }

    override fun onResume() {

        super.onResume()

        sharedPreferences = getSharedPreferences(
            "QuestionnaireSharedPreference",
            Context.MODE_PRIVATE
        )

        checkBoxes = arrayOf(
            findViewById(R.id.box1),
            findViewById(R.id.box2),
            findViewById(R.id.box3),
            findViewById(R.id.box4),
            findViewById(R.id.box5),
            findViewById(R.id.box6),
            findViewById(R.id.box7),
            findViewById(R.id.box8),
            findViewById(R.id.box9),
            findViewById(R.id.box10),
            findViewById(R.id.box11),
            findViewById(R.id.box12),
            findViewById(R.id.box13),
            findViewById(R.id.box14),
            findViewById(R.id.box15),
            findViewById(R.id.box16),
            findViewById(R.id.box17),
            findViewById(R.id.box18),
            findViewById(R.id.box19),
            findViewById(R.id.box20),
            findViewById(R.id.box21),
            findViewById(R.id.box22)
        )

        // editText 뷰 인스턴스 생성
        val editTextBox = findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.box22_text)

        val getSurveyResults =
            commonUserDefinedObjectSet.getOneSurveyResults(
                1,
                commonUserDefinedObjectSet.dateToday,
                sharedPreferences
            )?.results

        surveyResults =
            getSurveyResults ?: MutableList(22) { 0 }

        checkBoxes.forEachIndexed { index, checkBox ->

            if (surveyResults != MutableList(22) { 0 }) {

                if (surveyResults[index] == 1) {
                    checkBox.isChecked
                } else {
                    checkBox.isChecked = false
                }

            } else {

                checkBox.isChecked = false

            }

        }

        checkBoxes.forEachIndexed { index, checkBox ->
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    surveyResults[index] = 1
                } else {
                    surveyResults[index] = 0
                }
            }
        }

        checkBoxes.forEachIndexed { index, checkBox ->
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    surveyResults[index] = 1
                } else {
                    surveyResults[index] = 0
                }
            }
        }

        editTextBox.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            // 우리는 Text 가 입력되는 동안, text 입력 여부를 바탕으로 체크박스 체트 상태를 동적으로 변경
            // 또한 commonUserDefinedObject 의 oneSurveyResult 데이터 클래스의 내용을 업데이트

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val checkBoxText = editTextBox.text.toString()

                // 22번째 텍스트 입력내용이 비는 경우 체크박스 해제, 무엇이라도 입력되면 체크 상태 적용
                checkBoxes[21].isChecked = checkBoxText.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        val submitButton =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.submitButton)

        submitButton.setOnClickListener {
            val intent = Intent(this, QuestionnaireMainPage::class.java)
            startActivity(intent)

            val checkBoxText = editTextBox.text.toString()

            commonUserDefinedObjectSet.updateSurveyData(
                surveyResults,
                checkBoxText,
                1,
                commonUserDefinedObjectSet.dateToday,
                sharedPreferences
            )

        }

    }

}