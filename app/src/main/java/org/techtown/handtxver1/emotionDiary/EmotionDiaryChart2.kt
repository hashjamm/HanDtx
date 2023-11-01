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
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentEmotionDiaryChart2Binding
import org.techtown.handtxver1.org.techtown.handtxver1.ApplicationClass
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [EmotionDiaryChart1.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmotionDiaryChart2 : Fragment(), View.OnClickListener {

    // 감정 다이어리의 각 메뉴들이 공유할 날짜를 저장하는 viewModel 인 SharedDateViewModel 에 접근
    private val viewModel: SharedDateViewModel by activityViewModels()

    // loginSharedPreferences -> 로그인 창에서 입력한 내용을 기반으로 user id를 가져오기 위하여 인스턴스 생성
    private val loginSharedPreferences = ApplicationClass.loginSharedPreferences

    // binding 변수 생성
    internal lateinit var binding: FragmentEmotionDiaryChart2Binding

    // retrofit 객체 생성
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://3.37.133.233") // 연결하고자 하는 서버 주소 입력
        .addConverterFactory(GsonConverterFactory.create()) // gson 을 통한 javaScript 로의 코드 자동 전환 - Gson 장착
        .build() // 코드 마무리

    // 감정다이어리 서비스 interface 를 장착한 Retrofit 객체 생성
    private var getEmotionDiaryRecordsInterface: GetEmotionDiaryRecordsInterface =
        retrofit.create(GetEmotionDiaryRecordsInterface::class.java)

    private var updateEmotionDiaryRecordsInterface: UpdateEmotionDiaryRecordsInterface =
        retrofit.create(UpdateEmotionDiaryRecordsInterface::class.java)

    // 레이아웃 버튼 요소들 변수 선언
    private lateinit var textView: androidx.appcompat.widget.AppCompatTextView
    private lateinit var prevButton: androidx.appcompat.widget.AppCompatImageView
    private lateinit var nextButton: androidx.appcompat.widget.AppCompatImageView

    // 감정다이어리 구현에서 필요한 객체들 모아둔 클래스 인스턴스 생성
    private val objectSet = EmotionDiaryUserDefinedObjectSet()

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
    private fun optimizingGraphByScore(score: Int?) {

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
        } ?: run {

            throw IllegalStateException("에러 발생 : 통신을 통한 score 값을 얻지 못했습니다.")

        }
    }

    private fun optimizingGraph() {

        // userID를 로그인 최근 기록 저장 데이터 파일에서 가져오는게 논리적으로 문제가 없을지 판단할 필요 있음
        val userID = loginSharedPreferences.getString("saveID", "")
        val date: Date? = viewModel.date.value?.time

        getEmotionDiaryRecordsInterface.requestGetEmotionDiaryRecords(userID!!, date!!)
            .enqueue(
                object :
                    Callback<GetEmotionDiaryRecordsOutput> {

                    override fun onResponse(
                        call: Call<GetEmotionDiaryRecordsOutput>,
                        response: Response<GetEmotionDiaryRecordsOutput>
                    ) {
                        val resultValue = response.body()
                        val score = resultValue?.score2

                        optimizingGraphByScore(score)

                    }

                    override fun onFailure(call: Call<GetEmotionDiaryRecordsOutput>, t: Throwable) {

                        val errorDialog = AlertDialog.Builder(context)
                        errorDialog.setTitle("통신 오류")
                        errorDialog.setMessage("통신에 실패했습니다 : type1")
                        errorDialog.show()

                        throw IllegalStateException("에러 발생 : 통신이 이루어지지 않습니다.")

                    }

                })
    }

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

        textView = view.findViewById(R.id.date)
        prevButton = view.findViewById(R.id.prevButton)
        nextButton = view.findViewById(R.id.nextButton)

        // 뒤에 updateTextView 메서드 구현
        updateTextView()

        // 초기화가 필요할 때만 실행할 것
        // val editor = sharedPreferences.edit()
        // editor.clear()
        // editor.apply()

        // 일단 현재 상태에서 그래프 최적화
        optimizingGraph()

        // 이전 날짜로 가는 버튼 누를 때 발생시킬 이벤트
        prevButton.setOnClickListener {

            // viewModel 에 저장된 날짜를 하루 차감 및 관련 변수들 업데이트
            viewModel.substractDate()
            viewModel.observeDate(viewLifecycleOwner)

            // 날짜 표기 부분을 차감된 viewModel 내부의 날짜로 업데이트
            updateTextView()

            // 그래프 최적화
            optimizingGraph()

        }


        // 이후 날짜로 가는 버튼 후를 때 발생시킬 이벤트
        nextButton.setOnClickListener {

            viewModel.addDate()
            viewModel.observeDate(viewLifecycleOwner)

            updateTextView()

            optimizingGraph()

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

                        // userID를 로그인 최근 기록 저장 데이터 파일에서 가져오는게 논리적으로 문제가 없을지 판단할 필요 있음
                        val userID = loginSharedPreferences.getString("saveID", "")
                        val date: Date? = viewModel.date.value?.time

                        var resultValue: GetEmotionDiaryRecordsOutput?
                        var updateValue: GetEmotionDiaryRecordsOutput?

                        getEmotionDiaryRecordsInterface.requestGetEmotionDiaryRecords(
                            userID!!,
                            date!!
                        )
                            .enqueue(object :
                                Callback<GetEmotionDiaryRecordsOutput> {

                                override fun onResponse(
                                    call: Call<GetEmotionDiaryRecordsOutput>,
                                    response: Response<GetEmotionDiaryRecordsOutput>
                                ) {
                                    resultValue = response.body()

                                    // updateValue = resultValue -> 이러면 resultValue 바꾸면 updateValue도 바뀜
                                    updateValue = resultValue?.copy()
                                    updateValue?.score2 = index

                                    updateEmotionDiaryRecordsInterface.requestUpdateEmotionDiaryRecords(
                                        userID,
                                        date,
                                        updateValue?.score1,
                                        updateValue?.inputText1,
                                        updateValue?.score2,
                                        updateValue?.inputText2,
                                        updateValue?.score3,
                                        updateValue?.inputText3
                                    )
                                        .enqueue(object :
                                            Callback<UpdateEmotionDiaryRecordsOutput> {

                                            override fun onResponse(
                                                call: Call<UpdateEmotionDiaryRecordsOutput>,
                                                response: Response<UpdateEmotionDiaryRecordsOutput>
                                            ) {


                                            }

                                            override fun onFailure(
                                                call: Call<UpdateEmotionDiaryRecordsOutput>,
                                                t: Throwable
                                            ) {
                                                val errorDialog = AlertDialog.Builder(context)
                                                errorDialog.setTitle("통신 오류")
                                                errorDialog.setMessage("통신에 실패했습니다 : type2")
                                                errorDialog.show()
                                            }

                                        })

                                }

                                override fun onFailure(
                                    call: Call<GetEmotionDiaryRecordsOutput>,
                                    t: Throwable
                                ) {

                                    val errorDialog = AlertDialog.Builder(context)
                                    errorDialog.setTitle("통신 오류")
                                    errorDialog.setMessage("통신에 실패했습니다 : type3")
                                    errorDialog.show()
                                }

                            })

                        // 방금 업데이트 된 값을 다시 받아와서 그래프와 말풍선 텍스트 업데이트
                        optimizingGraph()

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
        val chartSequence = binding.emotionDiaryChart2.children
        chartSequence.forEach { chart ->
            chart.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {

    }

    override fun onResume() {
        super.onResume()

        // 일단 현재 상태에서 그래프 최적화
        optimizingGraph()

    }

}