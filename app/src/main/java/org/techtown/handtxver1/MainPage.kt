package org.techtown.handtxver1

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.techtown.handtxver1.emotionDiary.EmotionDiaryUserDefinedObjectSet
import java.text.SimpleDateFormat
import java.util.*

class MainPage : AppCompatActivity() {

    // 데이터 관련 공통 도구 클래스에 대한 인스턴스 생성
    private val emotionDiaryUserDefinedObjectSet = EmotionDiaryUserDefinedObjectSet()

    // 감정 다이어리 부분 데이터 베이스 인스턴스 선언
    private lateinit var emotionDiarySharedPreferences: SharedPreferences

    // 모든 데이터 베이스에 대한 key 값 생성
    private val dateFormat1: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
    private val dateFormat2: SimpleDateFormat = SimpleDateFormat("MM.dd 기준", Locale.KOREA)
    private val dateString = dateFormat1.format(Calendar.getInstance().time)
    private val emotionDiaryDateString = dateFormat2.format(Calendar.getInstance().time)

    // 감정 다이어리 부분 데이터 로딩 및 UI 동적 변경 함수 생성
    // onResume 단계에서도 적용해주어야 하기 때문에 onCreate 내부에 작성하지 않음
    private fun optimizingByEmotionScore(menuNum: Int, emotionDiarySharedPreferences: SharedPreferences) {
        val emotionDiaryScore: Int?
        val emotionDiaryText: androidx.appcompat.widget.AppCompatTextView
        val emotionDiaryComplete: androidx.appcompat.widget.AppCompatTextView
        val graphTextArray: Array<String>

        when (menuNum) {
            1 -> {
                emotionDiaryScore = emotionDiaryUserDefinedObjectSet.getScore(
                    emotionDiarySharedPreferences,
                    1,
                    null,
                    dateString
                )

                emotionDiaryText =
                    findViewById(R.id.emotionDiaryText1)
                emotionDiaryComplete =
                    findViewById(R.id.emotionDiaryComplete1)
                graphTextArray = emotionDiaryUserDefinedObjectSet.graphTextArray1
            }
            2 -> {
                emotionDiaryScore = emotionDiaryUserDefinedObjectSet.getScore(
                    emotionDiarySharedPreferences,
                    2,
                    null,
                    dateString
                )

                emotionDiaryText =
                    findViewById(R.id.emotionDiaryText2)
                emotionDiaryComplete =
                    findViewById(R.id.emotionDiaryComplete2)
                graphTextArray = emotionDiaryUserDefinedObjectSet.graphTextArray2
            }
            3 -> {
                emotionDiaryScore = emotionDiaryUserDefinedObjectSet.getScore(
                    emotionDiarySharedPreferences,
                    3,
                    null,
                    dateString
                )

                emotionDiaryText =
                    findViewById(R.id.emotionDiaryText3)
                emotionDiaryComplete =
                    findViewById(R.id.emotionDiaryComplete3)
                graphTextArray = emotionDiaryUserDefinedObjectSet.graphTextArray3
            }
            else -> {
                emotionDiaryScore = null
                emotionDiaryText =
                    androidx.appcompat.widget.AppCompatTextView(this) // 예시로 사용한 TextView 의 생성 방법
                emotionDiaryComplete =
                    androidx.appcompat.widget.AppCompatTextView(this) // 예시로 사용한 TextView 의 생성 방법
                graphTextArray = emptyArray()
            }
        }

        if (emotionDiaryScore != null) {
            emotionDiaryText.text = graphTextArray[emotionDiaryScore]
            emotionDiaryComplete.text = "완료"
        } else {
            emotionDiaryText.text = "아직 입력하지 않았습니다"
            emotionDiaryComplete.text = ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        // 하단 메뉴 바 fragment 클래스 인스턴스 생성 및 트랜잭션

        val fragment = BottomMenuBar(3)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, fragment).commit()

        // 감정 다이어리 부분 네모 칸 오른쪽 상단의 날짜를 나타내는 작은 글씨 표시를 위한 text 지정 코드

        val emotionDiaryDate =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.emotionDiaryDate)

        emotionDiaryDate.text = emotionDiaryDateString

        // 감정다이어리 데이터베이스에서 오늘 날짜로 데이터 접근 및 표시

        val emotionDiarySharedPreferences: SharedPreferences =
            getSharedPreferences("EmotionDiarySharedPreferences", Context.MODE_PRIVATE)

        optimizingByEmotionScore(1, emotionDiarySharedPreferences)
        optimizingByEmotionScore(2, emotionDiarySharedPreferences)
        optimizingByEmotionScore(3, emotionDiarySharedPreferences)

    }

    override fun onResume() {
        super.onResume()

        val fragment = BottomMenuBar(3)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, fragment).commit()

        val emotionDiaryDate =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.emotionDiaryDate)

        val emotionDiarySharedPreferences: SharedPreferences =
            getSharedPreferences("EmotionDiarySharedPreferences", Context.MODE_PRIVATE)

        emotionDiaryDate.text = emotionDiaryDateString

        optimizingByEmotionScore(1, emotionDiarySharedPreferences)
        optimizingByEmotionScore(2, emotionDiarySharedPreferences)
        optimizingByEmotionScore(3, emotionDiarySharedPreferences)

    }
}