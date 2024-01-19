package org.techtown.handtxver1.questionnaires.type1

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.techtown.handtxver1.ApplicationClass
import org.techtown.handtxver1.R
import org.techtown.handtxver1.questionnaires.QuestionnaireMainPage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class QuestionnaireType1 : AppCompatActivity() {

    // 로그인한 유저 아이디 지정
    val userID = ApplicationClass.loginSharedPreferences.getString("saveID", "")

    // 현재 날짜 가져오기
    private val currentDate = Calendar.getInstance()

    // 날짜 변수를 string 으로 변환하는 과정에서 사용하는 포매터 : 로컬 시간대를 포함
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // 체크박스 배열 변수 선언, 작업해줄 내용을 하나의 함수로 지정하고 인스턴스를 직접사용하는 것이 필요한 경우를 파라미터로 지정해서 작성

    private lateinit var checkBoxes: Array<CheckBox>

    // 제출 완료 버튼을 누름과 동시에 QuestionnaireSharedPreferences 에 전송해줄 설문 결과 데이터 인스턴스 선언

    private lateinit var surveyResults: MutableList<Boolean>

    // retrofit 객체 생성
    private var retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/") // 연결하고자 하는 서버 주소 입력
        .addConverterFactory(GsonConverterFactory.create()) // gson 을 통한 javaScript 로의 코드 자동 전환 - Gson 장착
        .build() // 코드 마무리

    // 이슈 선택 서비스 interface 를 장착한 Retrofit 객체 생성
    private var updateIssueCheckingSurveyInterface: UpdateIssueCheckingSurveyInterface =
        retrofit.create(UpdateIssueCheckingSurveyInterface::class.java)

    private var getIssueCheckingSurveyInterface: GetIssueCheckingSurveyInterface =
        retrofit.create(GetIssueCheckingSurveyInterface::class.java)

    private fun getData(userID: String, date: String): GetIssueCheckingSurveyOutput? {

        var resultValue: GetIssueCheckingSurveyOutput? = null

        getIssueCheckingSurveyInterface.requestGetIssueCheckingSurvey(userID, date)
            .enqueue(
                object :
                    Callback<GetIssueCheckingSurveyOutput> {

                    override fun onResponse(
                        call: Call<GetIssueCheckingSurveyOutput>,
                        response: Response<GetIssueCheckingSurveyOutput>
                    ) {
                        if (response.isSuccessful) {

                            resultValue = response.body()

                        }

                    }

                    override fun onFailure(call: Call<GetIssueCheckingSurveyOutput>, t: Throwable) {

                        val errorDialog = AlertDialog.Builder(this@QuestionnaireType1)
                        errorDialog.setTitle("통신 오류")
                        errorDialog.setMessage("통신에 실패했습니다 : ${t.message}")

                        errorDialog.show()

                    }


                }

            )

        return resultValue

    }

    // boolean 타입으로 api 서버에 보냈더니 'true' 라는 문자열로 인식함
    // 이를 해결하기 위해 아예 int 형태로 변형해서 보내려고 함
    // boolean 을 int 형태로 전환하는 함수 생성

    fun booleanToInt(boolean: Boolean): Int {
        return if (boolean) 1 else 0
    }

    private fun updateData(
        userID: String,
        date: String,
        surveyResults: MutableList<Boolean>,
        checkBoxText: String
    ) {

        val intent = Intent(this, QuestionnaireMainPage::class.java)

        Log.d("err", "${surveyResults[0]}")
        Log.d("err", "${surveyResults[0] is Boolean}")

        val formattedSurveyResults = mutableListOf<Int>()

        surveyResults.forEach { result ->
            formattedSurveyResults.add(booleanToInt(result))
        }

        updateIssueCheckingSurveyInterface.requestUpdateIssueCheckingSurvey(
            userID,
            date,
            formattedSurveyResults[0],
            formattedSurveyResults[1],
            formattedSurveyResults[2],
            formattedSurveyResults[3],
            formattedSurveyResults[4],
            formattedSurveyResults[5],
            formattedSurveyResults[6],
            formattedSurveyResults[7],
            formattedSurveyResults[8],
            formattedSurveyResults[9],
            formattedSurveyResults[10],
            formattedSurveyResults[11],
            formattedSurveyResults[12],
            formattedSurveyResults[13],
            formattedSurveyResults[14],
            formattedSurveyResults[15],
            formattedSurveyResults[16],
            formattedSurveyResults[17],
            formattedSurveyResults[18],
            formattedSurveyResults[19],
            formattedSurveyResults[20],
            formattedSurveyResults[21],
            checkBoxText
        ).enqueue(object :
            Callback<UpdateIssueCheckingSurveyOutput> {

            override fun onResponse(
                call: Call<UpdateIssueCheckingSurveyOutput>,
                response: Response<UpdateIssueCheckingSurveyOutput>
            ) {

                if (response.isSuccessful) {

                    Toast.makeText(this@QuestionnaireType1, "설문을 완료하였습니다.", Toast.LENGTH_SHORT)
                        .show()

                    startActivity(intent)

                } else {

                    val errorDialog = AlertDialog.Builder(this@QuestionnaireType1)
                    errorDialog.setTitle("서버 응답 오류")
                    errorDialog.setMessage("status code : ${response.code()}")

                    errorDialog.show()

                }

            }

            override fun onFailure(
                call: Call<UpdateIssueCheckingSurveyOutput>,
                t: Throwable
            ) {

                val errorDialog = AlertDialog.Builder(this@QuestionnaireType1)
                errorDialog.setTitle("통신 오류")
                errorDialog.setMessage("통신에 실패했습니다 : ${t.message}")

                errorDialog.show()

            }


        })

    }

// sharedPreferences 를 선언만 함 -> 이후에 onCreate 와 onResume 에서 초기화
// Int 타입인 checkSumChange 는 선언만 해둘 수가 없어서 우선 초기화를 해두었으나 onCreate 와 onResume 에서 다시 초기화해줄 예정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_type1)

        // 날짜를 문자열로 변환
        val formattedDate = dateFormatter.format(currentDate.time)

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

        // 마지막 체크박스는 사용자가 직접 상호작용할 수 없게 설정
        checkBoxes[21].isEnabled = false

        // surveyResults 초기화
        // 사이즈가 22개이고 모든 요소를 0으로 초기화해두어 생성
        // 어차피 모든 체크박스를 체크 해제된 상태로 초기화할 것이라 문제 없음.
        surveyResults = MutableList(22) { false }

        // 해당 설문지 액티비티에 새롭게 들어온 순간 모든 체크박스 상태 초기화

        checkBoxes.forEach {
            it.isChecked = false
        }

        // 설문 페이지 들어왔을 때, editTextView 부분 클릭 횟수를 0회부터 추적 관찰
        // 이는 마지막 체크박스가 editTextView 가 아예 입력되지 않았을 때는 체크 해제되도록 하려고 함

        checkBoxes.forEachIndexed { index, checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                surveyResults[index] = isChecked
            }
        }

        editTextBox.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            // 우리는 Text 가 입력되는 동안, text 입력 여부를 바탕으로 체크박스 체크 상태를 동적으로 변경
            // 또한 commonUserDefinedObject 의 oneSurveyResult 데이터 클래스의 내용을 업데이트

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                checkBoxes[21].isChecked = !p0.isNullOrEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        val submitButton =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.submitButton)

        submitButton.setOnClickListener {

            val checkBoxText = editTextBox.text.toString()

            try {

                updateData(userID!!, formattedDate, surveyResults, checkBoxText)

            } catch (e: NullPointerException) {

                throw IllegalArgumentException("you should input non-null type at userID")

            }

        }
    }

    override fun onResume() {

        super.onResume()

        // 날짜를 문자열로 변환
        val formattedDate = dateFormatter.format(currentDate.time)

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

        // 마지막 체크박스는 사용자가 직접 상호작용할 수 없게 설정
        checkBoxes[21].isEnabled = false

        try {
            val surveyResults = getData(userID!!, formattedDate)

            val surveyOutputList = listOf(
                surveyResults?.checkbox1,
                surveyResults?.checkbox2,
                surveyResults?.checkbox3,
                surveyResults?.checkbox4,
                surveyResults?.checkbox5,
                surveyResults?.checkbox6,
                surveyResults?.checkbox7,
                surveyResults?.checkbox8,
                surveyResults?.checkbox9,
                surveyResults?.checkbox10,
                surveyResults?.checkbox11,
                surveyResults?.checkbox12,
                surveyResults?.checkbox13,
                surveyResults?.checkbox14,
                surveyResults?.checkbox15,
                surveyResults?.checkbox16,
                surveyResults?.checkbox17,
                surveyResults?.checkbox18,
                surveyResults?.checkbox19,
                surveyResults?.checkbox20,
                surveyResults?.checkbox21,
                surveyResults?.inputText
            )

            checkBoxes.forEachIndexed { index, checkBox ->

                if (surveyResults != null) {

                    if (surveyOutputList[index] == false) {
                        checkBox.isChecked = false
                    } else {
                        checkBox.isChecked
                    }

                } else {

                    checkBox.isChecked = false

                }

            }

        } catch (e: NullPointerException) {

            throw IllegalArgumentException("you should input non-null type at userID")

        }

        // 리스너의 경우, onResume 과 onCreate 모두에 적혀있을 경우 리스너 충돌 문제가 발생할 수 있음.
        // 필요한 경우에만 작성해주도록 하자.

    }

}