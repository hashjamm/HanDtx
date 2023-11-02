package org.techtown.handtxver1.emotionDiary

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.techtown.handtxver1.databinding.FragmentEachDateBox1Binding
import org.techtown.handtxver1.org.techtown.handtxver1.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [EachDateBox1.newInstance] factory method to
 * create an instance of this fragment.
 */
class EachDateBox1(val date: Date, val menuNum: Int) : Fragment() {

    internal lateinit var binding: FragmentEachDateBox1Binding

    // 로그인한 아이디 가져옴
    val sharedPreferences = ApplicationClass.loginSharedPreferences
    val userID: String? = sharedPreferences.getString("saveID", "")

    // retrofit 객체 생성
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://3.37.133.233") // 연결하고자 하는 서버 주소 입력
        .addConverterFactory(GsonConverterFactory.create()) // gson 을 통한 javaScript 로의 코드 자동 전환 - Gson 장착
        .build() // 코드 마무리

    // 감정다이어리 서비스 interface 를 장착한 Retrofit 객체 생성
    private var getEmotionDiaryRecordsInterface: GetEmotionDiaryRecordsInterface =
        retrofit.create(GetEmotionDiaryRecordsInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, dayTextLinearLayout: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentEachDateBox1Binding.inflate(inflater, dayTextLinearLayout, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 특정 길이 단위에 해당하는 매서드를 생성

        fun toDp(heightInDp: Int): Int {
            val density = resources.displayMetrics.density
            val heightInPx = (heightInDp * density).toInt()

            return heightInPx
        }

        val objectSet = EmotionDiaryUserDefinedObjectSet()

        val graphTextArray =
            when (menuNum) {
                1 -> objectSet.graphTextArray1
                2 -> objectSet.graphTextArray2
                3 -> objectSet.graphTextArray3
                else -> throw IllegalStateException("에러 발생 : menuNum 변수의 허용 범위가 아닙니다.")
            }

        getEmotionDiaryRecordsInterface.requestGetEmotionDiaryRecords(
            userID ?: throw IllegalStateException("로그인된 아이디가 없습니다."), date
        )
            .enqueue(
                object :
                    Callback<GetEmotionDiaryRecordsOutput> {

                    override fun onResponse(
                        call: Call<GetEmotionDiaryRecordsOutput>,
                        response: Response<GetEmotionDiaryRecordsOutput>
                    ) {
                        val resultValue = response.body()

                        val score =
                            when (menuNum) {
                                1 -> resultValue?.score1
                                2 -> resultValue?.score2
                                3 -> resultValue?.score3
                                else -> throw IllegalStateException("에러 발생 : menuNum 변수의 허용 범위가 아닙니다.")
                            }

                        val inputText =
                            when (menuNum) {
                                1 -> resultValue?.inputText1
                                2 -> resultValue?.inputText2
                                3 -> resultValue?.inputText3
                                else -> throw IllegalStateException("에러 발생 : menuNum 변수의 허용 범위가 아닙니다.")
                            }

                        val dayOfWeek = SimpleDateFormat("MM월 dd일(E)", Locale.KOREA).format(date)

                        binding.dayTextView1.text = dayOfWeek
                        binding.dayTextView1.setTextColor(Color.GRAY)
                        binding.dayTextView1.textSize = toDp(5).toFloat()

                        binding.dayTextView2.text = if (score != null) {
                            graphTextArray[score]
                        } else {
                            "오늘 하루 어땠나요?"
                        }

                        binding.dayTextView2.setTextColor(Color.GRAY)
                        binding.dayTextView2.textSize = toDp(5).toFloat()

                        binding.dayTextLine2.text = inputText
                        binding.dayTextLine2.setTextColor(Color.GRAY)
                        binding.dayTextLine2.textSize = toDp(5).toFloat()

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EachDateBox1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(date: Date, menuNum: Int) =
            EachDateBox1(date, menuNum).apply {
                arguments = Bundle().apply {

                }
            }
    }
}