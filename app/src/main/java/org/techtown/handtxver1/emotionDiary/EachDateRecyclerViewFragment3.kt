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

    // 본 fragment 에서 해당 view model 을 사용
    val viewModel: ViewModelForEachDateViewer by activityViewModels {
        ViewModelForEachDateViewerFactory(repository)
    }

    // 로그인한 유저 아이디 지정
    val userID = ApplicationClass.loginSharedPreferences.getString("saveID", null)

    override fun callBackEachDateEmotionDiary(
        success: Boolean,
        dateNum: Int?,
        positionData: EachDateRecordDataClass?
    ) {

        if (success) {

            val searchDate =
                viewModel.apiServerDateString.substring(0, 8) +
                        dateNum.toString().padStart(2, '0')

            val updateValue = UpdateEmotionDiaryRecordsInput(
                userID!!,
                searchDate,
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

        recyclerView = binding.recyclerView
        adapter = EachDateRecyclerViewAdapter(mutableListOf(), this)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        viewModel.getEmotionDiaryData(userID!!, 3)

        viewModel.mutableDataList.observe(viewLifecycleOwner) { newData ->

            adapter.setData(newData)

        }

    }
}