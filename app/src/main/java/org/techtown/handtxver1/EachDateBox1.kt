package org.techtown.handtxver1

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.techtown.handtxver1.databinding.FragmentEachDateBox1Binding
import org.techtown.handtxver1.org.techtown.handtxver1.CommonUserDefinedObjectSet
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [EachDateBox1.newInstance] factory method to
 * create an instance of this fragment.
 */
class EachDateBox1(val key: String, val dayOfWeek: String, val menuNum: Int) : Fragment() {
    // TODO: Rename and change types of parameters

    internal lateinit var binding: FragmentEachDateBox1Binding

    // 감정다이어리에서 생성한 sharedPreferences 에서 날짜에 해당하는 점수를 넘겨받기 위해 해당 파일에 접근

    private lateinit var sharedPreferences: SharedPreferences

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

        // shaeredPreferenes 인스턴스 초기화

        val sharedPreferences = requireContext().getSharedPreferences(
            "EmotionDiarySharedPreferences", Context.MODE_PRIVATE
        )


        // sharedPreferences 에 이후 초기화될 key 를 바탕으로 값을 올리고 내려받는 함수들을 생성
        // -> CommonUserDefinedObjectSet 에서 가져옴

        // sharedPreferences 에서 default 값으로 가져올 string 은 MixedData 클래스의 default 인스턴스(MixedData())를
        // serialization 한 json string (defaultJsonMixedData()) 를 생성
        // -> CommonUserDefinedObjectSet 에서 가져옴

        // CommonUserDefinedObjectSet 클래스 인스턴스를 생성하고, 해당 클래스에 보관하는 함수들과 데이터를 사용할 준비

        val commonUserDefinedObjectSet = CommonUserDefinedObjectSet()

        // sharedPreferences 에서 해당 날짜를 key 로 갖는 점수 값을 받아옴
        // 그리고 이 점수를 기반으로 문구를 끌어오기 위한 배열을 생성하고
        // 그 배열에서 text 내용을 찾아와서 작성

        val score = commonUserDefinedObjectSet.getScore(sharedPreferences, menuNum, givenKey = key)

        val inputText = commonUserDefinedObjectSet.getInputText(sharedPreferences, menuNum, givenKey = key)

        binding.dayTextView1.text = dayOfWeek
        binding.dayTextView1.setTextColor(Color.GRAY)
        binding.dayTextView1.textSize = toDp(5).toFloat()

        val graphTextArray =
            when (menuNum) {
                1 -> commonUserDefinedObjectSet.graphTextArray1
                2 -> commonUserDefinedObjectSet.graphTextArray2
                3 -> commonUserDefinedObjectSet.graphTextArray3
                else -> emptyArray()
            }

        binding.dayTextView2.text = if (score != null) {
            graphTextArray[score]
        } else {
            "오늘 하루 어땠나요?"
        }
        binding.dayTextView1.setTextColor(Color.GRAY)
        binding.dayTextView2.textSize = toDp(5).toFloat()

        binding.dayTextLine2.text = inputText
        binding.dayTextLine2.setTextColor(Color.GRAY)
        binding.dayTextLine2.textSize = toDp(5).toFloat()

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
        fun newInstance(key: String, dayOfWeek: String, menuNum: Int) =
            EachDateBox1(key, dayOfWeek, menuNum).apply {
                arguments = Bundle().apply {

                }
            }
    }
}