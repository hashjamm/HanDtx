package org.techtown.handtxver1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Layout
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AlignmentSpan
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class QuestionnaireMainPage : AppCompatActivity() {

    // QuestionnairePage1 에서 각 설문지에서의 체크박스 선택 점수 합산을 저장하기 위한 SharedPreferences 인스턴스를 선언
    // 반드시 선언만 할 것.
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_page1)

        // sharedPreferences 를 초기화(onCreate) + editor 생성 및 각 설문지 타입의 점수 합산을 불러옴
        sharedPreferences =
            getSharedPreferences("QuestionnairesSharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // 지금까지 누적된 score 값을 0으로 초기화하기 위한 작업 코드 : 문제발생시에만 사용하면 되는 코드.
        // 반드시 해당 설문 메인 페이지까지 에뮬레이터/에서 진입을 한 뒤, 아래의 clear 코드를 주석 처리 하고 다시 에뮬레이터를 실행시킬 것.
//        editor.clear()
//        editor.apply()

//        val scoreOfType1 = sharedPreferences.getInt("ScoreOfType1", 0)
//        val scoreOfType2 = sharedPreferences.getInt("ScoreOfType2", 0)
//        val scoreOfType3 = sharedPreferences.getInt("ScoreOfType3", 0)
//        val scoreOfType4 = sharedPreferences.getInt("ScoreOfType4", 0)
//        val scoreOfType5 = sharedPreferences.getInt("ScoreOfType5", 0)
//        val scoreOfType6 = sharedPreferences.getInt("ScoreOfType6", 0)

        val intent = getIntent()

        // getIntExtra 매서드로 가져오면 계속 default 값을 입력하라해서 그냥 String 으로 가져와서 Int 로 수정 + null 값에 대한 코드 추가
        val scoreChangeType1 = intent.getStringExtra("scoreOfType1")
        val scoreChangeType2 = intent.getStringExtra("scoreOfType2")
        val scoreChangeType3 = intent.getStringExtra("scoreOfType3")
        val scoreChangeType4 = intent.getStringExtra("scoreOfType4")
        val scoreChangeType5 = intent.getStringExtra("scoreOfType5")
        val scoreChangeType6 = intent.getStringExtra("scoreOfType6")
        val scoreChangeType7 = intent.getStringExtra("scoreOfType7")
        val scoreChangeType8 = intent.getStringExtra("scoreOfType8")
        val scoreChangeDrinkingQuestionnaire = intent.getStringExtra("scoreOfDrinkingQuestionnaire")



        if (scoreChangeType1 != null) {
            val newScore = sharedPreferences.getInt("ScoreOfType1", 0) + scoreChangeType1.toInt()
            editor.putInt("ScoreOfType1", newScore)
            editor.apply()
        }
        if (scoreChangeType2 != null) {
//            val newScore = sharedPreferences.getInt("ScoreOfType2", 0) + scoreChangeType2.toInt()
            val newScore = scoreChangeType2.toInt()
            editor.putInt("ScoreOfType2", newScore)
            editor.apply()
        }
        if (scoreChangeType3 != null) {
//            val newScore = sharedPreferences.getInt("ScoreOfType3", 0) + scoreChangeType3.toInt()
            val newScore = scoreChangeType3.toInt()
            editor.putInt("ScoreOfType3", newScore)
            editor.apply()
        }
        if (scoreChangeType4 != null) {
//            val newScore = sharedPreferences.getInt("ScoreOfType4", 0) + scoreChangeType4.toInt()
            val newScore = scoreChangeType4.toInt()
            editor.putInt("ScoreOfType4", newScore)
            editor.apply()
        }
        if (scoreChangeType5 != null) {
//            val newScore = sharedPreferences.getInt("ScoreOfType4", 0) + scoreChangeType4.toInt()
            val newScore = scoreChangeType5.toInt()
            editor.putInt("ScoreOfType5", newScore)
            editor.apply()
        }
        if (scoreChangeType6 != null) {
//            val newScore = sharedPreferences.getInt("ScoreOfType6", 0) + scoreChangeType6.toInt()
            val newScore = scoreChangeType6.toInt()
            editor.putInt("ScoreOfType6", newScore)
            editor.apply()
        }
        if (scoreChangeType7 != null) {
//            val newScore = sharedPreferences.getInt("ScoreOfType7", 0) + scoreChangeType7.toInt()
            val newScore = scoreChangeType7.toInt()
            editor.putInt("ScoreOfType7", newScore)
            editor.apply()
        }
        if (scoreChangeType8 != null) {
//            val newScore = sharedPreferences.getInt("ScoreOfType7", 0) + scoreChangeType7.toInt()
            val newScore = scoreChangeType8.toInt()
            editor.putInt("ScoreOfType8", newScore)
            editor.apply()
        }
        if (scoreChangeDrinkingQuestionnaire != null) {
//            val newScore = sharedPreferences.getInt("ScoreOfType7", 0) + scoreChangeType7.toInt()
            val newScore = scoreChangeDrinkingQuestionnaire.toInt()
            editor.putInt("ScoreOfDrinkingQuestionnaire", newScore)
            editor.apply()
        }

        // 완료 여부 알려주는 텍스트박스의 백그라운드를 동적 설정해주기 위한 코드
        val textViewBackGroundDrawable1 =
            ContextCompat.getDrawable(this, R.drawable.questionnaire_check_box_unclicked)
        val textViewBackGroundDrawable2 =
            ContextCompat.getDrawable(this, R.drawable.questionnaire_check_box_clicked)

        for (typeNumber in 1..8) {

            val scoreOfType = sharedPreferences.getInt("ScoreOfType$typeNumber", 0)

            val viewId = resources.getIdentifier("qTypeCheckBox$typeNumber", "id", packageName)
            val textView = findViewById<TextView>(viewId)
            // 찾은 textView 를 사용하여 원하는 작업을 수행

            if (typeNumber == 8) {
                val scoreOfDrinkingQuestionnaire =
                    sharedPreferences.getInt("ScoreOfDrinkingQuestionnaire", 0)

                if (scoreOfType == 0 && scoreOfDrinkingQuestionnaire == 0) {

                    textView.background = textViewBackGroundDrawable1
                } else {

                    textView.background = textViewBackGroundDrawable2

                }
            } else {
                if (scoreOfType == 0) {

                    Log.d("bb", "포인트3")

                    textView.background = textViewBackGroundDrawable1
                } else {

                    Log.d("bb", "포인트4")

                    textView.background = textViewBackGroundDrawable2
                }
            }
        }

        Log.d("aaaaa", "금주 설문 : ${sharedPreferences.getInt("ScoreOfDrinkingQuestionnaire", 0)}")
        Log.d("aaaaa", "------------------------------------")



        Log.d("sss", "--------------------------")
        Log.d("sss", "$scoreChangeType1")
        Log.d("sss", "$scoreChangeType2")
        Log.d("sss", "$scoreChangeType3")
        Log.d("sss", "$scoreChangeType4")
        Log.d("sss", "$scoreChangeType5")
        Log.d("sss", "$scoreChangeType6")
        Log.d("sss", "$scoreChangeType7")
        Log.d("sss", "$scoreChangeType8")
        Log.d("sss", "$scoreChangeDrinkingQuestionnaire")
//        Log.d("sss", "$scoreOfType1")
//        Log.d("sss", "$scoreOfType2")
        Log.d("sss", "${sharedPreferences.getInt("ScoreOfType1", 0)}")
        Log.d("sss", "${sharedPreferences.getInt("ScoreOfType2", 0)}")
        Log.d("sss", "${sharedPreferences.getInt("ScoreOfType3", 0)}")
        Log.d("sss", "${sharedPreferences.getInt("ScoreOfType4", 0)}")
        Log.d("sss", "${sharedPreferences.getInt("ScoreOfType5", 0)}")
        Log.d("sss", "${sharedPreferences.getInt("ScoreOfType6", 0)}")
        Log.d("sss", "${sharedPreferences.getInt("ScoreOfType7", 0)}")
        Log.d("sss", "${sharedPreferences.getInt("ScoreOfType8", 0)}")
        Log.d("sss", "${sharedPreferences.getInt("ScoreOfDrinkingQuestionnaire", 0)}")
        Log.d("sss", "--------------------------")

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

        // 이미 완료한 설문을 다시 클릭했을 때, 팝업창을 띄우고 긍정과 취소 버튼을 누를 때 발생시킬 이벤트를 설정하는
        // 코드가 재사용이 많이 될 예정이라 이를 함수로 만들어 둠.
        fun scoreInitializing(typeNum: Int, intent: Intent) {

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
                .setMessage("이전 기록을 초기화하고 다시 보시겠습니까?")
                .setNeutralButton(spannableStringBuilderNo, null)

            if (typeNum == 8) {
                scoreResetPopup.setPositiveButton(spannableStringBuilderYes) { dialog, which ->
                    editor.putInt("ScoreOfType$typeNum", 0)
                    editor.apply()
                    editor.putInt("ScoreOfDrinkingQuestionnaire", 0)
                    editor.apply()

                    startActivity(intent)

                    Toast.makeText(this, "설문 기록을 초기화합니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                scoreResetPopup.setPositiveButton(spannableStringBuilderYes) { dialog, which ->
                    editor.putInt("ScoreOfType$typeNum", 0)
                    editor.apply()

                    startActivity(intent)

                    Toast.makeText(this, "설문 기록을 초기화합니다.", Toast.LENGTH_SHORT).show()
                }
            }

            scoreResetPopup.show()
        }


        // 각 설문지 타입으로 넘어가는 텍스트뷰들에 대한 리스너 코드
        toQuestionnaireType1.setOnClickListener {
            val intentToType1 = Intent(this, QuestionnaireType1::class.java)
            startActivity(intentToType1)
        }
        toQuestionnaireType2.setOnClickListener {
            if (sharedPreferences.getInt("ScoreOfType2", 0) == 0) {
                val intentToType2 = Intent(this, QuestionnaireType2::class.java)
                startActivity(intentToType2)
            } else {
                val intentToType2 = Intent(this, QuestionnaireType2::class.java)
                scoreInitializing(2, intentToType2)
            }
        }
        toQuestionnaireType3.setOnClickListener {
            if (sharedPreferences.getInt("ScoreOfType3", 0) == 0) {
                val intentToType3 = Intent(this, QuestionnaireType3::class.java)
                startActivity(intentToType3)
            } else {
                val intentToType3 = Intent(this, QuestionnaireType3::class.java)
                scoreInitializing(3, intentToType3)
            }
        }
        toQuestionnaireType4.setOnClickListener {
            if (sharedPreferences.getInt("ScoreOfType4", 0) == 0) {
                val intentToType4 = Intent(this, QuestionnaireType4::class.java)
                startActivity(intentToType4)
            } else {
                val intentToType4 = Intent(this, QuestionnaireType4::class.java)
                scoreInitializing(4, intentToType4)
            }
        }
        toQuestionnaireType5.setOnClickListener {
            if (sharedPreferences.getInt("ScoreOfType5", 0) == 0) {
                val intentToType5 = Intent(this, QuestionnaireType5::class.java)
                startActivity(intentToType5)
            } else {
                val intentToType5 = Intent(this, QuestionnaireType5::class.java)
                scoreInitializing(5, intentToType5)
            }
        }
        toQuestionnaireType6.setOnClickListener {
            if (sharedPreferences.getInt("ScoreOfType6", 0) == 0) {
                val intentToType6 = Intent(this, QuestionnaireType6::class.java)
                startActivity(intentToType6)
            } else {
                val intentToType6 = Intent(this, QuestionnaireType6::class.java)
                scoreInitializing(6, intentToType6)
            }
        }
        toQuestionnaireType7.setOnClickListener {
            if (sharedPreferences.getInt("ScoreOfType7", 0) == 0) {
                val intentToType7 = Intent(this, QuestionnaireType7::class.java)
                startActivity(intentToType7)
            } else {
                val intentToType7 = Intent(this, QuestionnaireType7::class.java)
                scoreInitializing(7, intentToType7)
            }
        }
        toQuestionnaireType8.setOnClickListener {
            if (sharedPreferences.getInt("ScoreOfType8", 0) == 0 &&
                sharedPreferences.getInt("ScoreOfDrinkingQuestionnaire", 0) == 0
            ) {
                val intentToType8 = Intent(this, QuestionnaireType8::class.java)
                startActivity(intentToType8)
            } else {
                val intentToType8 = Intent(this, QuestionnaireType8::class.java)
                scoreInitializing(8, intentToType8)
            }
        }
    }


    override fun onResume() {
        super.onResume()

//        Log.d("st", "--------------------------")
//        Log.d("st", "${sharedPreferences.getInt("ScoreOfType1", 0)}")
//        Log.d("st", "${sharedPreferences.getInt("ScoreOfType2", 0)}")
//        Log.d("st", "--------------------------")

        val menuBar = BottomMenuBar(4)
        supportFragmentManager.beginTransaction().add(R.id.menuBar, menuBar).commit()

        val textViewBackGroundDrawable1 =
            ContextCompat.getDrawable(
                this,
                R.drawable.questionnaire_check_box_unclicked
            )
        val textViewBackGroundDrawable2 =
            ContextCompat.getDrawable(
                this,
                R.drawable.questionnaire_check_box_clicked
            )

        Log.d("bb", "여기도?")

        for (typeNumber in 1..8) {

            val scoreOfType = sharedPreferences.getInt("ScoreOfType$typeNumber", 0)
            val viewId = resources.getIdentifier("qTypeCheckBox$typeNumber", "id", packageName)
            val textView = findViewById<TextView>(viewId)
            // 찾은 textView 를 사용하여 원하는 작업을 수행

            if (typeNumber == 8) {
                val scoreOfDrinkingQuestionnaire =
                    sharedPreferences.getInt("ScoreOfDrinkingQuestionnaire", 0)

                if (scoreOfType == 0 && scoreOfDrinkingQuestionnaire == 0) {
                    textView.background = textViewBackGroundDrawable1
                } else {
                    textView.background = textViewBackGroundDrawable2
                }
            } else {
                if (scoreOfType == 0) {
                    textView.background = textViewBackGroundDrawable1
                } else {
                    textView.background = textViewBackGroundDrawable2
                }
            }
        }
    }
}


