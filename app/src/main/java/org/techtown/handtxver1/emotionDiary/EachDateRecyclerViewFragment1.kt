package org.techtown.handtxver1.emotionDiary

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.handtxver1.CallBackInterface
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentEachDateRecyclerView1Binding
import org.techtown.handtxver1.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalArgumentException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [EachDateRecyclerViewFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class EachDateRecyclerViewFragment1 : Fragment(), CallBackInterface {

    // 오류 팝업 창들에 대하여, 가장 먼저 발생한 오류 팝업 창만을 유지하기 위한 Boolean 변수
    private var isPopupShowing = false

    // Fragment 에 사용할 레이아웃 파일 바인딩 선언
    lateinit var binding: FragmentEachDateRecyclerView1Binding

    // 감정다이어리에서 날짜 값을 지정하고 있는 ViewModel 을
    // EmotionDiaryEachDateViewer 액티비티에서 사용할 수 있도록 하고
    // 본 fragment 에서 해당 view model 을 사용
    private val viewModel: ViewModelForEachDateViewer by activityViewModels()

    // Recycler View 사용을 위한 데이터 클래스 리스트를 생성하기 위해 빈 리스트 선언
    private val mutableDataList = mutableListOf<EachDateRecordDataClass>()

    // Recycler View 인스턴스를 지정
    // 해당 뷰에 적용해줄 Adapter 인스턴스를 지정
    // Adapter 인스턴스 파라미터로 채워줄 CallBack 클래스 인스턴스 생성
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var adapter: EachDateRecyclerViewAdapter
    private lateinit var callBack: CallBackInterface

    // retrofit 객체 생성
    private var retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/") // 연결하고자 하는 서버 주소 입력
        .addConverterFactory(GsonConverterFactory.create()) // gson 을 통한 javaScript 로의 코드 자동 전환 - Gson 장착
        .build() // 코드 마무리

    // 감정다이어리 서비스 interface 를 장착한 Retrofit 객체 생성
    private var getEmotionDiaryRecordsInterface: GetEmotionDiaryRecordsInterface =
        retrofit.create(GetEmotionDiaryRecordsInterface::class.java)

    private var updateEmotionDiaryRecordsInterface: UpdateEmotionDiaryRecordsInterface =
        retrofit.create(UpdateEmotionDiaryRecordsInterface::class.java)


    val objectSet = EmotionDiaryUserDefinedObjectSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, eachDateRecyclerViewFragment1: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_each_date_recycler_view1,
                eachDateRecyclerViewFragment1,
                false
            )

        recyclerView = binding.recyclerView

        adapter = EachDateRecyclerViewAdapter(mutableDataList, callBack)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

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

            val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
            val primaryDate = dateFormat.parse(viewModel.dateString)

            val calendar = Calendar.getInstance()
            calendar.time = primaryDate!!

            calendar.set(Calendar.DAY_OF_MONTH, day)

            val searchDate = calendar.time

            val resultValue = getData(userID!!, searchDate)

            val dateLineFormat = SimpleDateFormat("dd일 E", Locale.KOREA)
            val dateStringInLine = dateLineFormat.format(searchDate)

            val score = resultValue?.score1

            val textByScore = if (score != null) {
                objectSet.graphTextArray1[score]
            } else {
                "오늘 하루 어땠나요?"
            }

            val inputText = resultValue?.inputText1

            // 데이터 클래스 인스턴스 생성
            val oneDateData = EachDateRecordDataClass(
                dateStringInLine,
                textByScore,
                inputText
            )

            // 해당 데이터 클래스 인스턴스를 데이터 리스트에 추가
            mutableDataList.add(oneDateData)

        }

    }

    private fun getData(userID: String, date: Date): GetEmotionDiaryRecordsOutput? {

        var resultValue: GetEmotionDiaryRecordsOutput? = null

        getEmotionDiaryRecordsInterface.requestGetEmotionDiaryRecords(userID, date)
            .enqueue(
                object :
                    Callback<GetEmotionDiaryRecordsOutput> {

                    override fun onResponse(
                        call: Call<GetEmotionDiaryRecordsOutput>,
                        response: Response<GetEmotionDiaryRecordsOutput>
                    ) {

                        if (response.isSuccessful) {

                            resultValue = response.body()

                        }

                    }

                    override fun onFailure(call: Call<GetEmotionDiaryRecordsOutput>, t: Throwable) {

                        if (!isPopupShowing) {

                            val errorDialog = AlertDialog.Builder(context)
                            errorDialog.setTitle("통신 오류")
                            errorDialog.setMessage("통신에 실패했습니다 : ${t.message}")
                            errorDialog.setOnDismissListener {
                                isPopupShowing = false
                            }
                            errorDialog.show()

                            isPopupShowing = true

                        }

                    }

                }
            )

        return resultValue

    }

    private fun updateData(userID: String, date: Date, updateValue: GetEmotionDiaryRecordsOutput?) {

        updateEmotionDiaryRecordsInterface.requestUpdateEmotionDiaryRecords(
            userID,
            date,
            updateValue?.score1,
            updateValue?.inputText1,
            updateValue?.score2,
            updateValue?.inputText2,
            updateValue?.score2,
            updateValue?.inputText3
        )
            .enqueue(object :
                Callback<UpdateEmotionDiaryRecordsOutput> {

                override fun onResponse(
                    call: Call<UpdateEmotionDiaryRecordsOutput>,
                    response: Response<UpdateEmotionDiaryRecordsOutput>
                ) {

                    if (!response.isSuccessful) {

                        if (!isPopupShowing) {
                            val errorDialog = AlertDialog.Builder(context)
                            errorDialog.setTitle("서버 응답 오류")
                            errorDialog.setMessage("status code : ${response.code()}")
                            errorDialog.setOnDismissListener {
                                isPopupShowing = false
                            }
                            errorDialog.show()

                            isPopupShowing = true
                        }

                    }

                }

                override fun onFailure(
                    call: Call<UpdateEmotionDiaryRecordsOutput>,
                    t: Throwable
                ) {

                    if (!isPopupShowing) {
                        val errorDialog = AlertDialog.Builder(context)
                        errorDialog.setTitle("통신 오류")
                        errorDialog.setMessage("통신에 실패했습니다 : ${t.message}")
                        errorDialog.setOnDismissListener {
                            isPopupShowing = false
                        }
                        errorDialog.show()

                        isPopupShowing = true
                    }

                }

            })

    }

    private fun dayChangerForMonth(
        dateString: String,
        dayOfMonth: Int
    ): Date? {

        val standardFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        val primaryDate: Date?

        try {
            primaryDate = standardFormat.parse(dateString)

            val calendar = Calendar.getInstance()
            calendar.time = primaryDate!!

            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            return calendar.time

        } catch (e: ParseException) {
            // dateString 의 형태와 포맷이 일치하지 않는 경우에 대한 처리
            e.printStackTrace()

            return null
        }

    }


    override fun onCallBackValueChanged(
        success: Boolean,
        dateNum: Int?,
        positionData: EachDateRecordDataClass?
    ) {

        if (success) {

            val updateValue: GetEmotionDiaryRecordsOutput?

            // 로그인한 유저 아이디 지정
            val userID = ApplicationClass.loginSharedPreferences.getString("saveID", null)

            val searchDate =
                if (dateNum != null) {
                    dayChangerForMonth(viewModel.dateString, dateNum)
                } else {
                    null
                }

            // userID와 searchDate 가 상식적으로 null 인 상황이 있을 수가 없음. 하지만 발생시 에러 발생시킬 것.
            try {
                val resultValue = getData(userID!!, searchDate!!)

                if (resultValue == null) {

                    updateValue = GetEmotionDiaryRecordsOutput(
                        null,
                        positionData?.inputText,
                        null,
                        null,
                        null,
                        null
                    )

                } else {

                    updateValue = resultValue.copy()
                    updateValue.inputText1 = positionData?.inputText

                }

                updateData(userID, searchDate, updateValue)

            } catch (e: IllegalArgumentException) {
                println("you should input non-null type at userID, searchDate")
            }

        }

    }
}