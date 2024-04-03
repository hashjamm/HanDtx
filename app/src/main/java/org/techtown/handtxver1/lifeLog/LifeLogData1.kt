package org.techtown.handtxver1.lifeLog

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.techtown.handtxver1.BottomMenuBar
import org.techtown.handtxver1.R

class LifeLogData1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_log_data1)

        val menuBarFragment = BottomMenuBar(2)
        val lifeLogData1Frag1 = LifeLogData1Frag1()
        val lifeLogData1Frag2 = LifeLogData1Frag2()
        val lifeLogData1Frag3 = LifeLogData1Frag3()

        // 기분, 불안, 비만 버튼 클릭시, framelayout 에 3개의 fragment 의 view를 장착하기 위하여 버튼에 대한 객체 생성
        val toPart1 = findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.toPart1)
        val toPart2 = findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.toPart2)
        val toPart3 = findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.toPart3)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, menuBarFragment).commit()

        supportFragmentManager.beginTransaction()
            .add(R.id.life_log_data1_parts_container, lifeLogData1Frag1).commit()

        toPart1.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.life_log_data1_parts_container, lifeLogData1Frag1)
                .commitNow()

            toPart1.setTextColor(Color.parseColor("#000000"))
            toPart2.setTextColor(Color.parseColor("#737373"))
            toPart3.setTextColor(Color.parseColor("#737373"))

//            lifeLogData1Frag1.binding.emotionDiaryChart2.post {
//                emotionDiaryChartFragment2.binding.emotionDiaryChart2.scrollTo(0, 0)
//            }

        }

        toPart2.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.life_log_data1_parts_container, lifeLogData1Frag2)
                .commitNow()

            toPart1.setTextColor(Color.parseColor("#737373"))
            toPart2.setTextColor(Color.parseColor("#000000"))
            toPart3.setTextColor(Color.parseColor("#737373"))

//            lifeLogData1Frag2.binding.emotionDiaryChart2.post {
//                emotionDiaryChartFragment2.binding.emotionDiaryChart2.scrollTo(0, 0)
//            }

        }

        toPart3.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.life_log_data1_parts_container, lifeLogData1Frag3)
                .commitNow()

            toPart1.setTextColor(Color.parseColor("#737373"))
            toPart2.setTextColor(Color.parseColor("#737373"))
            toPart3.setTextColor(Color.parseColor("#000000"))

//            lifeLogData1Frag1.binding.emotionDiaryChart2.post {
//                emotionDiaryChartFragment2.binding.emotionDiaryChart2.scrollTo(0, 0)
//            }

        }

    }
}