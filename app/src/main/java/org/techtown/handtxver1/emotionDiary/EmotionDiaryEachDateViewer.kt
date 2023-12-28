package org.techtown.handtxver1.emotionDiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import org.techtown.handtxver1.BottomMenuBar
import org.techtown.handtxver1.R

class EmotionDiaryEachDateViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotion_diary_each_date_viewer)

        val dateString = intent.getStringExtra("dateString")
        val weekdayString = intent.getStringExtra("weekdayString")
        val dateWeekDayString = intent.getStringExtra("dateWeekDayString")
        val daysInMonth = intent.getStringExtra("daysInMonth")?.toInt()

        // ViewModel 에 접근 및 로딩
        val viewModel = ViewModelProvider(this)[ViewModelForEachDateViewer::class.java]
        viewModel.setData(dateString!!, weekdayString!!, dateWeekDayString!!, daysInMonth!!)

        val menuBar = BottomMenuBar(1)
        val dateRecord1 = EachDateRecyclerViewFragment1()
        val dateRecord2 = EachDateRecyclerViewFragment2()
        val dateRecord3 = EachDateRecyclerViewFragment3()

        val toEmotionDiaryEachDateViewer1 = findViewById<AppCompatTextView>(R.id.toEmotionDiaryEachDateViewer1)
        val toEmotionDiaryEachDateViewer2 = findViewById<AppCompatTextView>(R.id.toEmotionDiaryEachDateViewer2)
        val toEmotionDiaryEachDateViewer3 = findViewById<AppCompatTextView>(R.id.toEmotionDiaryEachDateViewer3)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, menuBar).commit()
        supportFragmentManager.beginTransaction().add(R.id.dateRecord, dateRecord1).commit()

        toEmotionDiaryEachDateViewer1.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.dateRecord, dateRecord1).commit()
        }
        toEmotionDiaryEachDateViewer2.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.dateRecord, dateRecord2).commit()
        }
        toEmotionDiaryEachDateViewer3.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.dateRecord, dateRecord3).commit()
        }

    }

    override fun onResume() {
        super.onResume()

        val menuBar = BottomMenuBar(1)
        supportFragmentManager.beginTransaction().add(R.id.menuBar, menuBar).commit()
    }

}