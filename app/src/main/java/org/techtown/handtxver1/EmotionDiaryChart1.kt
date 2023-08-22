package org.techtown.handtxver1

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.techtown.handtxver1.databinding.FragmentEmotionDiaryChart1Binding
import org.techtown.handtxver1.org.techtown.handtxver1.CommonUserDefinedObjectSet

/**
 * A simple [Fragment] subclass.
 * Use the [EmotionDiaryChart1.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmotionDiaryChart1 : Fragment(), View.OnClickListener {

    // 감정 다이어리의 각 메뉴들이 공유할 날짜를 저장하는 viewModel 인 SharedDateViewModel 에 접근
    private val viewModel: SharedDateViewModel by activityViewModels()

    // 감정 다이어리에서 날짜마다 점수를 기억하고, 이에 맞는 그래프를 그리게 함과 동시에, 일별 기록 부분에서도 접근 가능한
    // SharedPreferences 파일을 생성

    // SharedPreferences 추가 내용 : 일별기록부분에서 날짜 각각에 대하여 입력한 내용또한 저장해야하는 것으로 나타남
    // 이에 따라, 날짜를 key 로, 사용자가 입력한 점수(Int)와 일별기록 부분에서 입력한 내용(String)을 json 포맷으로 묶어둔 문자열을 value 로 갖도록 설정

    private lateinit var sharedPreferences: SharedPreferences

    // sharedPreferences 에 넣어줄 데이터 클래스 선언. score 와 inputString 두 개를 하나로 묶어서 가지고 있도록 설정.
    // JSON 직렬화 가능하도록 해당 클래스를 설정

    internal lateinit var binding: FragmentEmotionDiaryChart1Binding

    // private lateinit var dateFormat: SimpleDateFormat
    // private lateinit var weekdayFormat: SimpleDateFormat
    private lateinit var textView: androidx.appcompat.widget.AppCompatTextView
    private lateinit var prevButton: androidx.appcompat.widget.AppCompatImageView
    private lateinit var nextButton: androidx.appcompat.widget.AppCompatImageView

    // sharedPreferences 에 이후 초기화될 key 를 바탕으로 값을 올리고 내려받는 함수들을 생성
    // -> CommonUserDefinedObjectSet 에서 가져옴

    // sharedPreferences 에서 default 값으로 가져올 string 은 MixedData 클래스의 default 인스턴스(MixedData())를
    // serialization 한 json string (defaultJsonMixedData()) 를 생성
    // -> CommonUserDefinedObjectSet 에서 가져옴

    // CommonUserDefinedObjectSet 클래스 인스턴스를 생성하고, 해당 클래스에 보관하는 함수들과 데이터를 사용할 준비

    private val commonUserDefinedObjectSet = CommonUserDefinedObjectSet()

    // textView 내부의 날짜 부분을 key 로 갖는 value 가 sharedPreferences 에 등록이 되어있는 경우
    // 그래프를 해당 값에 맞게 구현해주는 코드 작성
    // default 값을 100 로 갖고 오게 함. 음수는 사용자에 의해 점수가 부여되지 않음을 의미한다고 약속하자.
    // -1 로 하려고 했는데, 그러니까 배열의 음수 인덱스가 적용되는 오류가 있는 것으로 보였음.
    // 단순하게 10 으로만 하려고 하니까, 라디오 버튼 개수를 10개로 할 경우도 있을 수 있어서 우선 절대 할 일 없는 100 으로 설정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, emotionDiaryChart1: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_emotion_diary_chart1,
                emotionDiaryChart1,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()

        // Calendar 클래스는 abstract Class 로, 다양한 캘린더 시스템 구현에 사용됨.
        // Calendar 클래스의 getInstance() 메서드는 호출되는 시점의 날짜와 시간을 가진 Calendar 객체를 반환함
        // 파라미터로 Locale.KOREA 등을 입력하면 특정 지역에 대한 시간대를 가져올 수 있음.

//        dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
//        weekdayFormat = SimpleDateFormat("E", Locale.KOREA)


        textView = view.findViewById(R.id.date)
        prevButton = view.findViewById(R.id.prevButton)
        nextButton = view.findViewById(R.id.nextButton)

        // 뒤에 updateTextView 메서드 구현
        updateTextView()

        // SharedPreferences 인스턴스를 생성함
        // 프래그먼트 자체에서 requireContext() 메서드를 사용하여 프래그먼트의 Context 를 가져와서 SharedPreferences 를 생성
        sharedPreferences = requireContext().getSharedPreferences(
            "EmotionDiarySharedPreferences", Context.MODE_PRIVATE
        )

        // 초기화가 필요할 때만 실행할 것
        // val editor = sharedPreferences.edit()
        // editor.clear()
        // editor.apply()

        val radioGroup = arrayOf(
            binding.button0,
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button4,
            binding.button5,
            binding.button6,
            binding.button7,
            binding.button8,
            binding.button9
        )

        // 이후에 동일한 코드가 반복되는 것을 막기 위해 함수를 생성하여 코드 간결화
        // 현재 viewModel 에 입력된 날짜를 가져와서, sharedPreferences 에 해당 날짜에 입력된 점수를 가져온 후
        // 점수에 맞도록 그래프 배경과 말풍선 텍스트를 변경하는 함수

        fun optimizingGraph(menuNum: Int) {

            val score = commonUserDefinedObjectSet.getScore(sharedPreferences, menuNum, viewModel)

            if (score != null) {

                if (score in 0..9) {

                    // 그래프 형태를 점수에 맞게 반영
                    binding.radioButtonsBackground.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            commonUserDefinedObjectSet.graphBackgroundArray[score]
                        )
                    binding.howRU.text = commonUserDefinedObjectSet.graphTextArray1[score]

                    // 라디오 그룹 기능 구현
                    radioGroup.forEachIndexed { index, radioButton ->
                        if (index == score) {
                            radioButton.isChecked = true
                        } else {
                            radioButton.isChecked = false
                        }
                    }
                }
            } else {

                // 그래프 형태를 default 형태로 변경
                binding.radioButtonsBackground.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.emotion_graph_image_0)
                binding.howRU.text =
                    when (menuNum) {
                        1 -> "오늘 하루 어땠나요?"
                        2 -> "오늘 얼마나 불안했나요?"
                        3 -> "오늘 식욕은 어땠나요?"
                        else -> null
                    }

                // 라디오 그룹 전체 버튼 초기화
                radioGroup.forEach {
                    it.isChecked = false
                }
            }
        }

        // 일단 현재 상태에서 그래프 최적화
        optimizingGraph(1)

        // 이전 날짜로 가는 버튼 누를 때 발생시킬 이벤트
        prevButton.setOnClickListener {

            // viewModel 에 저장된 날짜를 하루 차감 및 관련 변수들 업데이트
            viewModel.substractDate()
            viewModel.observeDate(viewLifecycleOwner)

            // 날짜 표기 부분을 차감된 viewModel 내부의 날짜로 업데이트
            updateTextView()

            // 그래프 최적화
            optimizingGraph(1)

        }


        // 이후 날짜로 가는 버튼 후를 때 발생시킬 이벤트
        nextButton.setOnClickListener {

            viewModel.addDate()
            viewModel.observeDate(viewLifecycleOwner)

            updateTextView()

            optimizingGraph(1)

        }

        // 라디오 버튼들을 누를 때 라디오 그룹 기능을 구현하고, sharedPreferences 에 관련 값을 업데이트한 후, 이에 맞추어 그래프 최적화

        radioGroup.forEachIndexed { index, button ->
            button.setOnClickListener {
                if (it is RadioButton) {
                    if (it.isChecked) {

                        // 라디오 그룹 기능 구현
                        radioGroup.forEachIndexed { num, radioButton ->
                            if (index != num) {
                                radioButton.isChecked = false
                            }
                        }

                        // sharedPreferences 업데이트
                        // viewModel 에 업데이트된 날짜를 key 로 갖고, 선택된 점수를 value 로 갖도록 editor 에 데이터 저장
                        // 예) "2023.08.03" : 2

                        commonUserDefinedObjectSet.updateScore(
                            index,
                            sharedPreferences,
                            1,
                            viewModel
                        )

                        // sharedPreferences 에 방금 업데이트 된 값을 바탕으로 그래프와 말풍선 텍스트 업데이트
                        optimizingGraph(1)

                    }
                }
            }
        }


    }

    @SuppressLint("SetTextI18n")
    // textView 의 text 부분을 재정의하는 메서드인 updateTextView 작성
    private fun updateTextView() {
        textView.text =
            "${viewModel.dateString.value.toString()} (${viewModel.weekdayString.value.toString()})"
    }

    private fun setOnClickListener() {
        val chartSequence = binding.emotionDiaryChart1.children
        chartSequence.forEach { chart ->
            chart.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {

    }

    override fun onResume() {
        super.onResume()

        val radioGroup = arrayOf(
            binding.button0,
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button4,
            binding.button5,
            binding.button6,
            binding.button7,
            binding.button8,
            binding.button9
        )

        // 이후에 동일한 코드가 반복되는 것을 막기 위해 함수를 생성하여 코드 간결화
        // 현재 viewModel 에 입력된 날짜를 가져와서, sharedPreferences 에 해당 날짜에 입력된 점수를 가져온 후
        // 점수에 맞도록 그래프 배경과 말풍선 텍스트를 변경하는 함수

        fun optimizingGraph(menuNum: Int) {

            val score = commonUserDefinedObjectSet.getScore(sharedPreferences, menuNum, viewModel)

            if (score != null) {

                if (score in 0..9) {

                    // 그래프 형태를 점수에 맞게 반영
                    binding.radioButtonsBackground.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            commonUserDefinedObjectSet.graphBackgroundArray[score]
                        )
                    binding.howRU.text = commonUserDefinedObjectSet.graphTextArray1[score]

                    // 라디오 그룹 기능 구현
                    radioGroup.forEachIndexed { index, radioButton ->
                        if (index == score) {
                            radioButton.isChecked = true
                        } else {
                            radioButton.isChecked = false
                        }
                    }
                }
            } else {

                // 그래프 형태를 default 형태로 변경
                binding.radioButtonsBackground.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.emotion_graph_image_0)
                binding.howRU.text =
                    when (menuNum) {
                        1 -> "오늘 하루 어땠나요?"
                        2 -> "오늘 얼마나 불안했나요?"
                        3 -> "오늘 식욕은 어땠나요?"
                        else -> null
                    }

                // 라디오 그룹 전체 버튼 초기화
                radioGroup.forEach {
                    it.isChecked = false
                }
            }
        }

        // 일단 현재 상태에서 그래프 최적화
        optimizingGraph(1)

    }

}