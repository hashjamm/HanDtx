package org.techtown.handtxver1.emotionDiary

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentEachDateRecyclerView1Binding
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
 * Use the [EachDateRecyclerViewFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class EachDateRecyclerViewFragment1 : Fragment() {

    // Fragment 에 사용할 레이아웃 파일 바인딩 선언
    lateinit var binding: FragmentEachDateRecyclerView1Binding

    // 감정다이어리에서 날짜 값을 지정하고 있는 ViewModel 을
    // EmotionDiaryEachDateViewer 액티비티에서 사용할 수 있도록 하고
    // 본 fragment 에서 해당 view model 을 사용
    private val viewModel: ViewModelForEachDateViewer by activityViewModels()

    // Recycler View 사용을 위한 데이터 클래스 리스트를 생성하기 위해 빈 리스트 선언
    val mutableDataList = mutableListOf<EachDateRecordDataClass>()

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
        inflater: LayoutInflater, eachDateRecyclerViewFragment1: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_each_date_recycler_view1,
                eachDateRecyclerViewFragment1,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 로그인한 유저 아이디 지정
        val userID = ApplicationClass.loginSharedPreferences.getString("saveID", "")

        // 각 날짜에 해당하는 데이터 클래스를 생성하는 과정
        // -> 이후 이 데이터 클래스들을 리스트로 묶어서 recycler view 생성 예정
        for (day in 1..viewModel.daysInMonth) {

            // viewModel 의 dateString 을 java.util.Date 형태로 변환한 값으로 서버에서
            // 감정다이어리 결과를 가져오고, 해당 결과의 inputText1 을 추출

            val format = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
            val searchDate = format.parse(viewModel.dateString)

            getEmotionDiaryRecordsInterface.requestGetEmotionDiaryRecords(userID!!, searchDate!!)
                .enqueue(
                    object :
                        Callback<GetEmotionDiaryRecordsOutput> {

                        override fun onResponse(
                            call: Call<GetEmotionDiaryRecordsOutput>,
                            response: Response<GetEmotionDiaryRecordsOutput>
                        ) {
                            val resultValue = response.body()
                            val inputText = resultValue?.inputText1

                            // 데이터 클래스 생성
                            val oneDateData = EachDateRecordDataClass(searchDate, inputText)

                            // 해당 데이터 클래스를 데이터 리스트에 추가
                            mutableDataList.add(oneDateData)

                        }

                        override fun onFailure(
                            call: Call<GetEmotionDiaryRecordsOutput>,
                            t: Throwable
                        ) {

                            val errorDialog = AlertDialog.Builder(context)
                            errorDialog.setTitle("통신 오류")
                            errorDialog.setMessage("통신에 실패했습니다")
                            errorDialog.show()

                            throw IllegalStateException("에러 발생 : 통신이 이루어지지 않습니다.")

                        }

                    })

        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EachDateRecylerViewFragment1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            EachDateRecyclerViewFragment1().apply {
                arguments = Bundle().apply {
                }
            }
    }
}