package org.techtown.handtxver1.emotionDiary

import android.annotation.SuppressLint
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
import org.techtown.handtxver1.ApplicationClass
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentEmotionDiaryChart2Binding
import retrofit2.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [EmotionDiaryChart1.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmotionDiaryChart2 : Fragment(), View.OnClickListener {

    // loginSharedPreferences -> 로그인 창에서 입력한 내용을 기반으로 user id를 가져오기 위하여 인스턴스 생성
    private val loginSharedPreferences = ApplicationClass.loginSharedPreferences

    // binding 변수 생성
    lateinit var binding: FragmentEmotionDiaryChart2Binding

    // 감정다이어리 구현에서 필요한 객체들 모아둔 클래스 인스턴스 생성
    private val objectSet = EmotionDiaryUserDefinedObjectSet()

    // repository 인스턴스 생성
    private val repository = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, emotionDiaryChart2: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_emotion_diary_chart2,
                emotionDiaryChart2,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()

        // 감정 다이어리의 각 메뉴들이 공유할 날짜를 저장하는 viewModel 인 SharedDateViewModel 에 접근
        val viewModel: SharedDateViewModel by activityViewModels {
            SharedDateViewModelFactory(repository)
        }

        // 그래프를 이루는 라디오버튼들을 배열로 묶어둔 인스턴스 생성
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

        // 현재 상태에 맞도록 그래프와 날짜 표기칸을 최적화하는 함수
        fun optimizingGraphByScore(score: Int?) {

            score?.let {

                if (it in 0..9) {

                    // 그래프 형태를 점수에 맞게 반영
                    binding.radioButtonsBackground.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            objectSet.graphBackgroundArray[it]
                        )
                    binding.howRU.text = objectSet.graphTextArray2[it]

                    // 라디오 그룹 기능 구현
                    radioGroup.forEachIndexed { index, radioButton ->
                        radioButton.isChecked = index == score

                    }

                } else {

                    // 사실상 db 에서 직접적으로 데이터를 잘못 생성하지 않는 한 일어날 수 없는 오류.
                    throw ArrayIndexOutOfBoundsException("에러 발생 : score range error")

                }
            } ?: run {

                // 그래프 형태를 default 형태로 변경
                binding.radioButtonsBackground.background =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.emotion_graph_image_0
                    )
                binding.howRU.text = "오늘 얼마나 불안했나요?"

                // 라디오 그룹 전체 버튼 초기화
                radioGroup.forEach { radioButton ->
                    radioButton.isChecked = false
                }

            }
        }

        // 뒤에 updateTextView 메서드 구현
        updateTextView(
            viewModel.dateString.value.toString(),
            viewModel.weekdayString.value.toString()
        )

        // userID를 로그인 최근 기록 저장 데이터 파일에서 가져오는게 논리적으로 문제가 없을지 판단할 필요 있음
        val userID = loginSharedPreferences.getString("saveID", null)

        // 일단 현재 상태에서 그래프 최적화
        viewModel.getEmotionDiaryData(userID!!, viewModel.apiServerDateString.value!!, 2)

        viewModel.score.observe(this) { newData ->

            optimizingGraphByScore(newData)

        }

        // 이전 날짜로 가는 버튼 누를 때 발생시킬 이벤트
        binding.prevButton.setOnClickListener {

            // viewModel 에 저장된 날짜를 하루 차감 및 관련 변수들 업데이트
            viewModel.subtractDate()
            viewModel.observeDate(viewLifecycleOwner)

            // 날짜 표기 부분을 차감된 viewModel 내부의 날짜로 업데이트
            updateTextView(
                viewModel.dateString.value.toString(),
                viewModel.weekdayString.value.toString()
            )

            viewModel.getEmotionDiaryData(userID, viewModel.apiServerDateString.value!!, 2)

        }

        // 이후 날짜로 가는 버튼 후를 때 발생시킬 이벤트
        binding.nextButton.setOnClickListener {

            viewModel.addDate()
            viewModel.observeDate(viewLifecycleOwner)

            updateTextView(
                viewModel.dateString.value.toString(),
                viewModel.weekdayString.value.toString()
            )

            viewModel.getEmotionDiaryData(userID, viewModel.apiServerDateString.value!!, 2)

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

                        val updateValue = UpdateEmotionDiaryRecordsInput(
                            userID,
                            viewModel.apiServerDateString.value!!,
                            index,
                            null,
                            2
                        )

                        repository.updateData(updateValue)
                        optimizingGraphByScore(index)

                    }
                }
            }
        }


    }

    @SuppressLint("SetTextI18n")
// textView 의 text 부분을 재정의하는 메서드인 updateTextView 작성
    private fun updateTextView(dateText: String, weekdayText: String) {
        binding.date.text =
            "$dateText (${weekdayText})"
    }

    private fun setOnClickListener() {
        val chartSequence = binding.emotionDiaryChart2.children
        chartSequence.forEach { chart ->
            chart.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {

    }

}