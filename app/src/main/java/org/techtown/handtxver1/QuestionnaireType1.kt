package org.techtown.handtxver1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import org.techtown.handtxver1.org.techtown.handtxver1.CommonUserDefinedObjectSet

class QuestionnaireType1 : AppCompatActivity() {

    // 체크박스 배열 변수 선언, 작업해줄 내용을 하나의 함수로 지정하고 인스턴스를 직접사용하는 것이 필요한 경우를 파라미터로 지정해서 작성

    private lateinit var checkBoxes: Array<CheckBox>

    // 제출 완료 버튼을 누름과 동시에 QuestionnaireSharedPreferences 에 전송해줄 설문 결과 데이터 인스턴스 선언

    private lateinit var surveyResults: MutableList<Int>

    // CommonUserDefinedObjectSet.OneSurveyResult() 데이터 클래스의 디폴트 값을 가져옴
    private val finalSurveyResults = CommonUserDefinedObjectSet.OneSurveyResult()

    // 전체 체크박스에 대하여 체크 상태를 추적하고, 체크된 결과를 하나의 리스트로 업로드하는 함수
    // 마지막 체크박스에 대해서는 별도로 작업 : 유저가 체크박스를 직접 누르는게 아닌, editText 뷰에 내용이 들어가면 자동으로 체크상태로 전환

    private fun checkBoxChangedTracking(
        sharedPreferences: SharedPreferences,
        checkBoxes: Array<CheckBox>
    ) {

        checkBoxes.forEachIndexed { index, it ->

            val real_index = index + 1 // index 가 0부터 시작하기 때문에 햇갈림을 없애기 위한 real_index 변수 생성

            val key =
                "checkBox$real_index" // 각 체크박스의 체크 상태를 sharedPreferences 에 서로 다른 key 이름으로 저장하기 위한 key 변수 생성
            val isChecked =
                sharedPreferences.getBoolean(key, false) // 체크상태에 대하여 따로 입력된 값이 없으면 우선 false 로 가져옴.
            val inputText =
                sharedPreferences.getString("text", "") // 22번 체크박스에 대해서는 string 값을 가져와야하기에 따로 생성.

            it.isChecked = isChecked // sharedPreferences 에 저장된 체크상태를 그대로 유지하기 위해 해당 체크박스의 체크상태를 지정

            findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.box22_text).setText(
                inputText
            ) // sharedPreferences 에 저장된 22번 editText 의 text 부분을 그대로 유지하기 위해 해당 text 내용을 지정

            // setOnCheckedChangeListener 매서드 : 변동된 체크 상태를 추적하여 작성된 내용을 실행
            // isChecked : 채크된 상태에 대한 boolean 값
            it.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    checkSumChange += 1 // isChecked 가 True 가 되면 checkSumChange 에 1을 더함
                } else {
                    checkSumChange -= 1 // isChecked 가 False 가 되면 checkSumChange 에 1을 뺌
                }
                editor.putBoolean(key, isChecked) // 변동된 체크 상태를 지정했던 key 값으로 sharedPreferences 에 재지정
                editor.apply() // 변동내용 적용
            }

            // 22번 체크박스에 대해서는 별도의 작업을 진행해줘야 함.
            // editText 뷰는 addTextChangedListener 라는 매서드의 TextWatcher 로 텍스트가 변동되기 전, 작성 중, 작성 후에 대하여
            // 원하는 작업이 수행될 수 있도록 설정할 수 있다.
            if (real_index == 22) {
                val box22Text =
                    findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.box22_text)
                box22Text.setText(inputText)
                box22Text.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    // 우리는 Text 가 입력되는 동안, 변동되는 모든 text 를 동적으로 sharedPreferences 에 계속 재지정한다.

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        editor.putString(
                            "text", box22Text.text.toString()
                        )
                        editor.apply()
                    }

                    override fun afterTextChanged(p0: Editable?) {

                    }

                })

            }
        }
    }

    // sharedPreferences 를 선언만 함 -> 이후에 onCreate 와 onResume 에서 초기화
    // Int 타입인 checkSumChange 는 선언만 해둘 수가 없어서 우선 초기화를 해두었으나 onCreate 와 onResume 에서 다시 초기화해줄 예정

    private lateinit var sharedPreferences: SharedPreferences

    var checkSumChange = 0 // 체크가 추가되거나 해제된 개수를 QuestionnairePage1 액티비티에 전달해주기 위한 정수 인스턴스 초기화

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_type1)

        // sharedPreferences 초기화
        sharedPreferences = getSharedPreferences(
            "QuestionnaireSharedPreference",
            Context.MODE_PRIVATE
        )

        // editor 생성
        val editor = sharedPreferences.edit()

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

        val checkBoxText = editTextBox.text.toString()

        editTextBox.addTextChangedListener(object : TextWatcher {

            val checkBoxText = editTextBox.text.toString()

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            // 우리는 Text 가 입력되는 동안, text 입력 여부를 바탕으로 체크박스 체트 상태를 동적으로 변경
            // 또한 commonUserDefinedObject 의 oneSurveyResult 데이터 클래스의 내용을 업데이트

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                // 22번째 텍스트 입력내용이 비는 경우 체크박스 해제, 무엇이라도 입력되면 체크 상태 적용
                if (checkBoxText.isEmpty()) {
                    checkBoxes[21].isChecked = false
                } else {
                    checkBoxes[21].isChecked
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // 22번째 텍스트 입력내용을 데이터 클래스에 업데이트
                finalSurveyResults.copy(comment = checkBoxText)
            }
        })

        val submitButton =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.submitButton)

        submitButton.setOnClickListener {
            val intent = Intent(this, QuestionnaireMainPage::class.java)
            intent.putExtra("scoreOfType1", checkSumChange.toString())
            startActivity(intent)

            val checkBoxText = editTextBox.text.toString()

            finalSurveyResults.copy(results = surveyResults, comment = checkBoxText)

            editor.putString()

        }
    }

    override fun onResume() {

        super.onResume()

        sharedPreferences = getSharedPreferences(
            "QType1SharedPreference",
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

        checkSumChange = 0

        checkBoxChangedTracking(sharedPreferences, checkBoxes)

        val submitButton =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.submitButton)

        submitButton.setOnClickListener {
            val intent = Intent(this, QuestionnaireMainPage::class.java)
            intent.putExtra("scoreOfType1", checkSumChange.toString())
            startActivity(intent)
        }
    }

}