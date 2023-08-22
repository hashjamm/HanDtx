package org.techtown.handtxver1

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.fragment.app.activityViewModels
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [EmotionDiaryEachDateRecord2.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmotionDiaryEachDateRecord2 : Fragment() {

    // 감정다이어리에서 날짜 값을 지정하고 있는 ViewModel 을 EmotionDiaryEachDateViewer 액티비티에서 사용할 수 있도록 하고
    // 본 fragment 에서 date 를 가져옴

    private val viewModel: ViewModelForEachDateViewer by activityViewModels()

    // 감정다이어리에서 생성한 sharedPreferences 에서 날짜에 해당하는 점수를 넘겨받기 위해 해당 파일에 접근

    // private lateinit var sharedPreferences: SharedPreferences

    // sharedPreferences 에 넣어줄 데이터 클래스 선언. score 와 inputString 두 개를 하나로 묶어서 가지고 있도록 설정.
    // JSON 직렬화 가능하도록 해당 클래스를 설정
//    @Serializable
//    data class MixedData(val score: Int = 100, val inputString: String = "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 특정 길이 단위에 해당하는 매서드를 생성

        fun toDp(heightInDp: Int): Int {
            val density = resources.displayMetrics.density
            val heightInPx = (heightInDp * density).toInt()

            return heightInPx
        }

        // 전체 LinearLayout 뷰를 생성 + id 동적 부여 + orientation 설정
        // 전체 뷰를 Constraint 뷰로 생성했을 때, ConstraintSet 인스턴스 설정 과정에서
        // 내부의 여러 뷰들이 있을 때 뭔가 적용이 잘 안되는 경우가 너무 많이 발생하여 linear 로 진행.

        val wholeLinearLayout = LinearLayout(requireContext())
        wholeLinearLayout.id = View.generateViewId()

        wholeLinearLayout.orientation = LinearLayout.VERTICAL

        // 상단 ConstraintLayout 뷰를 생성 + id 동적 부여

        val upperConstraintLayout = ConstraintLayout(requireContext())
        upperConstraintLayout.id = View.generateViewId()

        // 상단 ConstraintLayout 내부에 넣어줄 TextView 뷰를 생성 + id 동적 부여

        val line1 = TextView(requireContext())
        val line2 = TextView(requireContext())
        val line3 = TextView(requireContext())

        line1.id = View.generateViewId()
        line2.id = View.generateViewId()
        line3.id = View.generateViewId()

        // 상단 ConstraintLayout 하단에 넣어줄 ScrollView 뷰를 생성 + id 동적 부여

        val scrollView = ScrollView(requireContext())
        scrollView.id = View.generateViewId()

        // 각 뷰들의 layout 인스턴스 생성 및 조정

        val wholeLinearLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        wholeLinearLayout.layoutParams = wholeLinearLayoutParams

        val upperConstraintLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            toDp(10)
        )

        upperConstraintLayout.layoutParams = upperConstraintLayoutParams

        // 상단 ConstraintLayout 의 background 를 drawable 파일로 지정

        val upperConstraintLayoutBackgroundDrawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.upper_border)
        upperConstraintLayout.background = upperConstraintLayoutBackgroundDrawable

        val line1LayoutParams = LinearLayout.LayoutParams(
            toDp(120),
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        val line2LayoutParams = LinearLayout.LayoutParams(
            toDp(120),
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        val line3LayoutParams = LinearLayout.LayoutParams(
            toDp(120),
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        line1.layoutParams = line1LayoutParams
        line2.layoutParams = line2LayoutParams
        line3.layoutParams = line3LayoutParams

        // line 의 background 를 drawable 파일로 지정

        val lineBackgroundDrawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.thick_upper_border)
//        val line2BackgroundDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.thick_upper_border)
//        val line3BackgroundDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.thick_upper_border)

//        line1.background = lineBackgroundDrawable
        line2.background = lineBackgroundDrawable
//        line3.background = lineBackgroundDrawable

        // 상단 ConstraintLayout 에 생성된 line 들을 넣어줌

        upperConstraintLayout.addView(line1)
        upperConstraintLayout.addView(line2)
        upperConstraintLayout.addView(line3)

        // ConstraintLayout 의 세부적인 내부 뷰의 제약을 조정하기 위한 ConstraintSet() 인스턴스 생성 및 조정
        // 반드시 clone 매서드를 통해 조절을 원하는 ConstraintLayout 의 layout 속성들을 복재하여 시작하고,
        // 세팅 이후에 applyTo 매서드를 통해 적용해주는 방식 사용

        val constraintSet = ConstraintSet()
        constraintSet.clone(upperConstraintLayout)

        // ConstraintSet 인스턴스에서 chain 을 생성하는 방법

        constraintSet.createHorizontalChain(
            upperConstraintLayout.id,
            ConstraintSet.LEFT,
            upperConstraintLayout.id,
            ConstraintSet.RIGHT,
            intArrayOf(line1.id, line2.id, line3.id),
            null,
            ConstraintSet.CHAIN_SPREAD
        )

        // ConstraintSet 적용

        constraintSet.applyTo(upperConstraintLayout)

        // 전체 LinearLayout 에 상단 ConstraintLayout 을 추가

        wholeLinearLayout.addView(upperConstraintLayout)

        // ScrollView 의 세부 레이아웃 동적 생성

        val scrollViewParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        scrollView.layoutParams = scrollViewParams

        val linearLayout = LinearLayout(requireContext())
        val linearLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayout.layoutParams = linearLayoutParams
        linearLayout.orientation = LinearLayout.VERTICAL

        // ViewModel 에서 저장하고 있는 dayInMonth 를 바탕으로 1일부터 끝일까지 하루씩 dayLinearLayout 을
        // 생성하고 수직으로 쌓아가기 위한 for loop
        // 우리는 한 줄 마다 아래와 같은 계층 구조로 진행
        // dayLinearLayout > frameLayout > dayTextLinearLayout > dayTextView1, dayTextView2
        // 이 때, dayTextView1 에는 text 로는 "OO월 O일 (O요일)" 이라는 값을 적어줄 것임
        // 그리고 frameLayout 에 들어오는 Fragment 는 eachDate1 과 eachDate2 인데,
        // eachDate1 이 기본값이고, 클릭시 두 인스턴스가 번갈아 나오게 설정하였음.
        // eachDate1 은 투명하기 때문에 dayLinearLayout 의 dayTextView1 과 dayTextView2 의 text 가 보임.

        for (day in 1..viewModel.daysInMonth) {

            val dayLinearLayout = LinearLayout(requireContext())
            dayLinearLayout.orientation = LinearLayout.HORIZONTAL

            val dayLinearLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // margin 을 설정해주는 setMargins 매서드 적용

            dayLinearLayoutParams.setMargins(toDp(1), toDp(3), toDp(1), toDp(3))

            dayLinearLayout.layoutParams = dayLinearLayoutParams

            dayLinearLayout.setBackgroundResource(R.drawable.bottom_border)

            // Calendar 클래스 인스턴스를 생성하고, 인스턴스의 time 속성 값을 viewModel 의 dateString 으로
            // 원하는 pattern 에 맞게 지정하고, set 매서드를 사용하여, day 에 맞추어 변형

            val calendar = Calendar.getInstance()
            calendar.time = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).parse(viewModel.dateString)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val dayOfWeek = SimpleDateFormat("MM월 dd일(E)", Locale.KOREA).format(calendar.time)

            // sharedPreferences 의 key 값에 접근하기 위한 변수 생성
            val key = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(calendar.time)

            // FrameLayout 뷰를 생성

            val frameLayout = FrameLayout(requireContext())

            val frameLayoutLayoutParams =
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )

            frameLayoutLayoutParams.gravity = Gravity.CENTER_VERTICAL
            frameLayoutLayoutParams.setMargins(toDp(3))

            frameLayout.layoutParams = frameLayoutLayoutParams

            dayLinearLayout.addView(frameLayout)

            frameLayout.id = View.generateViewId()

            // Fragment 인스턴스 생성

            // val eachDateBox1 = EachDateBox1(key, dayOfWeek)
            // val eachDateBox2 = EachDateBox2(key)

            childFragmentManager.beginTransaction().add(frameLayout.id, EachDateBox1(key, dayOfWeek, 2)).commit()

            // dayLinearLayout 을 누를 때마다 내부의 frameLayout 에 들어오는 Fragment 클래스를 바꿔주는 작업
            // 을 위한 Listener 설정
            // Fragment 파일 내부에서 하부 Fragment 에 접근할 때는 Activity 에서 supportFragmentManager 를
            // 사용하는 것이 아닌, childFragmentManager 를 사용하여야 함.
            // 현재 fragment 를 currentFragment 에 지정하고, null 값이 아닌 경우 트랜잭션된 fragment 를 제거
            // 제거해주지 않으면 fragment 전환이 잘 이루어지지 않았음
            // 그리고 currentFragment 의 클래스 종류에 따라 추가해줄 fragment 클래스 인스턴스를 지정하고
            // 이를 새롭게 트랜잭션 실행

            dayLinearLayout.setOnClickListener {
                val currentFragment = childFragmentManager.findFragmentById(frameLayout.id)

                if (currentFragment != null) {
                    childFragmentManager.beginTransaction().remove(currentFragment).commit()
                }

                if (currentFragment is EachDateBox1) {
                    val fragmentToAdd = EachDateBox2(key, 2)
                    childFragmentManager.beginTransaction().add(frameLayout.id, fragmentToAdd)
                        .commit()
                } else {
                    val fragmentToAdd = EachDateBox1(key, dayOfWeek, 2)
                    childFragmentManager.beginTransaction().add(frameLayout.id, fragmentToAdd)
                        .commit()
                }


            }

            linearLayout.addView(dayLinearLayout)

        }

        scrollView.addView(linearLayout)

        wholeLinearLayout.addView(scrollView)

        return wholeLinearLayout

    }

}