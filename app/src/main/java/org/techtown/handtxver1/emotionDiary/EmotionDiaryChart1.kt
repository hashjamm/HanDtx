package org.techtown.handtxver1.emotionDiary

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import org.techtown.handtxver1.databinding.FragmentEmotionDiaryChart1Binding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [EmotionDiaryChart1.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmotionDiaryChart1 : Fragment(), View.OnClickListener {

    // 오류 팝업 창들에 대하여, 가장 먼저 발생한 오류 팝업 창만을 유지하기 위한 Boolean 변수
    private var isPopupShowing = false

    // loginSharedPreferences -> 로그인 창에서 입력한 내용을 기반으로 user id를 가져오기 위하여 인스턴스 생성
    private val loginSharedPreferences = ApplicationClass.loginSharedPreferences

    // binding 변수 생성
    lateinit var binding: FragmentEmotionDiaryChart1Binding

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

        // 기존의 UI 최적화 함수(optimizingGraph)에서는 데이터를 서버에서 받아오고,
        // 받아온 점수를 바탕으로 UI 를 구현하는 코드로 되어있었음
        // 하지만 데이터를 받아오고(네트워크 호출) UI 를 구현하는 코드가 함께 있으면, 동기화의 문제가 있을 수 있음
        // 또한 코드 중복 최소화를 위해 네트워크 호출 함수, UI 최적화 함수를 분리

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
                    binding.howRU.text = objectSet.graphTextArray1[it]

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
                binding.howRU.text = "오늘 하루 어땠나요?"

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

        // 초기화가 필요할 때만 실행할 것
        // val editor = sharedPreferences.edit()
        // editor.clear()
        // editor.apply()

        // userID를 로그인 최근 기록 저장 데이터 파일에서 가져오는게 논리적으로 문제가 없을지 판단할 필요 있음
        val userID = loginSharedPreferences.getString("saveID", null)

        // 일단 현재 상태에서 그래프 최적화
        viewModel.getEmotionDiaryData(userID!!, viewModel.apiServerDateString.value!!)

        viewModel.score1.observe(this) { newData ->

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

            viewModel.getEmotionDiaryData(userID, viewModel.apiServerDateString.value!!)

        }


        // 이후 날짜로 가는 버튼 후를 때 발생시킬 이벤트
        binding.nextButton.setOnClickListener {

            viewModel.addDate()
            viewModel.observeDate(viewLifecycleOwner)

            updateTextView(
                viewModel.dateString.value.toString(),
                viewModel.weekdayString.value.toString()
            )

            viewModel.getEmotionDiaryData(userID, viewModel.apiServerDateString.value!!)

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

                        viewModel.getEmotionDiaryData(userID, viewModel.apiServerDateString.value!!)

                        viewModel.obtainedDataNullStatus.observe(this) { newData ->

                            val updateValue: GetEmotionDiaryRecordsOutput?

                            try {
                                if (newData == 1) {

                                    updateValue = GetEmotionDiaryRecordsOutput(
                                        index,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null
                                    )

                                    viewModel.obtainedDataNullStautsReset()

                                } else {

                                    updateValue = viewModel.obtainedData.value
                                    updateValue!!.score1 = index

                                }

                                repository.updateData(
                                    userID,
                                    viewModel.apiServerDateString.value!!, updateValue
                                )

                            } catch (e: NullPointerException) {

                                throw IllegalArgumentException("you should input non-null type at userID, searchDate")

                            }
                        }

                        // 방금 업데이트 된 값을 다시 받아와서 그래프와 말풍선 텍스트 업데이트
                        // optimizingGraph() -> getData 를 중첩해야하기에 직접 구현부분만 적음
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
        //"${viewModel.dateString.value.toString()} (${viewModel.weekdayString.value.toString()})"
    }

    private fun setOnClickListener() {
        val chartSequence = binding.emotionDiaryChart1.children
        chartSequence.forEach { chart ->
            chart.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {

    }

}