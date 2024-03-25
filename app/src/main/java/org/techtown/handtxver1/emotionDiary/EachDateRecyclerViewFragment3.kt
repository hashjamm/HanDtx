package org.techtown.handtxver1.emotionDiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.handtxver1.ApplicationClass
import org.techtown.handtxver1.CallBackInterface
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentEachDateRecyclerView3Binding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [EachDateRecyclerViewFragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class EachDateRecyclerViewFragment3 : Fragment(), CallBackInterface {

    // Fragment 에 사용할 레이아웃 파일 바인딩 선언
    lateinit var binding: FragmentEachDateRecyclerView3Binding

    // repository 인스턴스 생성
    private val repository = Repository()

    // Recycler View 인스턴스를 지정
    // 해당 뷰에 적용해줄 Adapter 인스턴스를 지정
    // Adapter 인스턴스 파라미터로 채워줄 CallBack 클래스 인스턴스 생성
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var adapter: EachDateRecyclerViewAdapter

    val objectSet = EmotionDiaryUserDefinedObjectSet()

    override fun callBackEachDateEmotionDiary(
        viewModel: ViewModelForEachDateViewer,
        success: Boolean,
        dateNum: Int?,
        positionData: EachDateRecordDataClass?
    ) {

        if (success) {

            // 로그인한 유저 아이디 지정
            val userID = ApplicationClass.loginSharedPreferences.getString("saveID", null)

            val searchDate =
                if (dateNum != null) {
                    viewModel.dateFormatChanger(
                        "yyyy.MM.dd",
                        "yyyy-MM-dd",
                        viewModel.dateString,
                        dateNum)
                } else {
                    null
                }

            val updateValue = UpdateEmotionDiaryRecordsInput(
                userID!!,
                searchDate!!,
                null,
                positionData?.inputText,
                3
            )

            repository.updateData(updateValue)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, eachDateRecyclerViewFragment3: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_each_date_recycler_view3,
                eachDateRecyclerViewFragment3,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 감정다이어리에서 날짜 값을 지정하고 있는 ViewModel 을
        // EmotionDiaryEachDateViewer 액티비티에서 사용할 수 있도록 하고
        // 본 fragment 에서 해당 view model 을 사용
        val viewModel: ViewModelForEachDateViewer by activityViewModels() {
            ViewModelForEachDateViewerFactory(repository)
        }

        // Recycler View 사용을 위한 데이터 클래스 리스트를 생성하기 위해 빈 리스트 선언
        val mutableDataList = mutableListOf<EachDateRecordDataClass>()

        // 로그인한 유저 아이디 지정
        val userID = ApplicationClass.loginSharedPreferences.getString("saveID", null)

        // 각 날짜에 해당하는 데이터 클래스를 생성하는 과정
        // -> 이후 이 데이터 클래스들을 리스트로 묶어서 recycler view 생성 예정
        for (day in 1..viewModel.daysInMonth) {

            // viewModel 의 dateString 을 java.util.Date 형태로 변환한 값으로 서버에서
            // 감정다이어리 결과를 가져오고, 해당 결과의 inputText1 을 추출

            val searchDate =
                viewModel.dateFormatChanger(
                    "yyyy.MM.dd",
                    "yyyy-MM-dd",
                    viewModel.dateString,
                    day)

            viewModel.getEmotionDiaryData(userID!!, searchDate!!, 3)

        }

        recyclerView = binding.recyclerView

        adapter = EachDateRecyclerViewAdapter(viewModel, this)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

    }
}