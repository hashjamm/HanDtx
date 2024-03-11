package org.techtown.handtxver1.emotionDiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.techtown.handtxver1.BottomMenuBar
import org.techtown.handtxver1.R

class EmotionDiary : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotion_diary)

        // repository 인스턴스 생성
        val repository = Repository()

        // ViewModel 에 접근 및 로딩
        val viewModel = ViewModelProvider(this, SharedDateViewModelFactory(repository))[SharedDateViewModel::class.java]

        // Fragment 객체를 선언 및 초기화
        val menuBarFragment = BottomMenuBar(1)
        val emotionDiaryChartFragment1 = EmotionDiaryChart1()
        val emotionDiaryChartFragment2 = EmotionDiaryChart2()
        val emotionDiaryChartFragment3 = EmotionDiaryChart3()

        // 기분, 불안, 비만 버튼 클릭시, framelayout 에 3개의 fragment 의 view를 장착하기 위하여 버튼에 대한 객체 생성
        val toDiary1 = findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.toDiary1)
        val toDiary2 = findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.toDiary2)
        val toDiary3 = findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.toDiary3)
        val toEmotionDiaryMenuSetting =
            findViewById<androidx.appcompat.widget.AppCompatImageView>(R.id.toDiaryMenuSetting)
        val toEmotionDiaryEachDateViewer =
            findViewById<androidx.appcompat.widget.LinearLayoutCompat>(R.id.toEmotionDiaryEachDateViewer)

        // menuBar 로 id 지정된 부분과 emotion_diary_charts_container 로 id 지정된 부분에 fragment 객체의 view 를 집어넣음
        supportFragmentManager.beginTransaction().add(R.id.menuBar, menuBarFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.emotion_diary_charts_container, emotionDiaryChartFragment1).commit()

        // 버튼을 누를 때 마다, emotion_diary_charts_container 로 id 지정된 부분에 각기 다른 fragment 객체의 view 를 집어넣음
        toDiary1.setOnClickListener {

            // fragment 를 transaction 함에 있어서, commit 하는 방식은 크게 4가지 메서드가 존재한다.
            // 1. commit : 기본적으로 권장되는 매서드. 트랜잭션의 커밋을 예약하고 즉시 실행되지 않는다.
            // 2. commitAllowingStateLoss
            // 3. commitNow : 커밋이 즉시 실행된다. 이 때, 기존에 사용하는 방식은 commit 을 사용 후, 대기 되어있는 모든
            // 트랜잭션을 커밋하는 executePendingTransaction() 매서드를 사용했지만 commitNow 는 지정한 트랜잭션만을 즉시 수행하여 더 좋다.
            // 4. commitNowAllowingStateLoss
            // AllowingStateLoss 은 쉽게 말해서 commit 은 FragmentManager 가 자신의 state 를 저장했는지를 확인하는데,
            // 그 과정에서 이미 저장을 했다면 IllegalStateException 을 던지는 것을 우회하는 매서드라 볼 수 있다.

            // post 를 적어준 이유는 scrollTo 는 UI 업데이트 매서드 중 하나로, 이러한 UI 업데이트 진행 내용은 메인 혹은 UI 쓰레드에서
            // 만 작동된다고 한다. post() 메서드는 메인 메써드에서 진행할 내용을 적어주는 메서드다.

            // 스크롤 맨 상단으로 자동 위치 변경이 안된 이유는 아래와 같다
            // 1. commitNow 매서드가 아닌 commit 매서드를 사용
            // 2. post 매서드 사용을 하지 않은 문제

            supportFragmentManager.beginTransaction()
                .replace(R.id.emotion_diary_charts_container, emotionDiaryChartFragment1)
                .commitNow()

            emotionDiaryChartFragment1.binding.emotionDiaryChart1.post {
                emotionDiaryChartFragment1.binding.emotionDiaryChart1.scrollTo(0, 0)
            }

        }
        toDiary2.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.emotion_diary_charts_container, emotionDiaryChartFragment2)
                .commitNow()

            emotionDiaryChartFragment2.binding.emotionDiaryChart2.post {
                emotionDiaryChartFragment2.binding.emotionDiaryChart2.scrollTo(0, 0)
            }

        }
        toDiary3.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.emotion_diary_charts_container, emotionDiaryChartFragment3)
                .commitNow()

            emotionDiaryChartFragment3.binding.emotionDiaryChart3.post {
                emotionDiaryChartFragment3.binding.emotionDiaryChart3.scrollTo(0, 0)
            }

        }
        toEmotionDiaryMenuSetting.setOnClickListener {
            startActivity(Intent(this, EmotionDiaryMenuSetting::class.java))
        }
        toEmotionDiaryEachDateViewer.setOnClickListener {
            val intentToEmotionDiaryEachDateViewer =
                Intent(this, EmotionDiaryEachDateViewer::class.java)
            intentToEmotionDiaryEachDateViewer.putExtra("dateString", viewModel.dateString.value.toString())
            intentToEmotionDiaryEachDateViewer.putExtra(
                "weekdayString",
                viewModel.weekdayString.value.toString()
            )
            intentToEmotionDiaryEachDateViewer.putExtra(
                "dateWeekDayString",
                viewModel.dateWeekDayString.value.toString()
            )
            intentToEmotionDiaryEachDateViewer.putExtra("daysInMonth", viewModel.daysInMonth.value.toString())

            startActivity(intentToEmotionDiaryEachDateViewer)

        }

    }

    override fun onResume() {
        super.onResume()

        val fragment = BottomMenuBar(1)

        supportFragmentManager.beginTransaction().add(R.id.menuBar, fragment).commit()

    }
}