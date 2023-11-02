package org.techtown.handtxver1.emotionDiary

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentEachDateBox2Binding
import org.techtown.handtxver1.org.techtown.handtxver1.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [EachDateBox2.newInstance] factory method to
 * create an instance of this fragment.
 */
class EachDateBox2(val date: Date, val menuNum: Int) : Fragment() {

    internal lateinit var binding: FragmentEachDateBox2Binding

    // 감정다이어리에서 생성한 sharedPreferences 에서 날짜에 해당하는 점수를 넘겨받기 위해 해당 파일에 접근

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

    private var updateEmotionDiaryRecordsInterface: UpdateEmotionDiaryRecordsInterface =
        retrofit.create(UpdateEmotionDiaryRecordsInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_each_date_box2, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val objectSet = EmotionDiaryUserDefinedObjectSet()

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
                        val updateValue = resultValue?.copy()

                        val inputText =
                            when (menuNum) {
                                1 -> resultValue?.inputText1
                                2 -> resultValue?.inputText2
                                3 -> resultValue?.inputText3
                                else -> throw IllegalStateException("에러 발생 : menuNum 변수의 허용 범위가 아닙니다.")
                            }

                        binding.inputText.setText(inputText)

                        binding.inputText.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {

                            }

                            override fun onTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {

                            }

                            // 변경된 모든 text 를 동적으로 서버에 업데이트
                            override fun afterTextChanged(p0: Editable?) {

                                when (menuNum) {
                                    1 -> updateValue?.inputText1 = p0.toString()
                                    2 -> updateValue?.inputText2 = p0.toString()
                                    3 -> updateValue?.inputText3 = p0.toString()
                                    else -> throw IllegalStateException("에러 발생 : menuNum 변수의 허용 범위가 아닙니다.")
                                }

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

                                            throw IllegalStateException("에러 발생 : 통신이 이루어지지 않습니다.")
                                        }

                                    })

                            }

                        })

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
         * @return A new instance of fragment EachDateBox.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(date: Date, menuNum: Int) =
            EachDateBox2(date, menuNum).apply {
                arguments = Bundle().apply {

                }
            }
    }
}