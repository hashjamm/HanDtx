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

    // 모든 데이터 베이스에 대한 key 값 생성
    private val dateFormat1: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
    private val dateFormat2: SimpleDateFormat = SimpleDateFormat("MM.dd 기준", Locale.KOREA)
    private val dateString = dateFormat1.format(Calendar.getInstance().time)
    private val emotionDiaryDateString = dateFormat2.format(Calendar.getInstance().time)

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

    }
}