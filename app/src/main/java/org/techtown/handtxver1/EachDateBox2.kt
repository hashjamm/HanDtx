package org.techtown.handtxver1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.techtown.handtxver1.databinding.FragmentEachDateBox2Binding
import org.techtown.handtxver1.org.techtown.handtxver1.CommonUserDefinedObjectSet
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [EachDateBox2.newInstance] factory method to
 * create an instance of this fragment.
 */
class EachDateBox2(val key: String, val menuNum: Int) : Fragment() {
    // TODO: Rename and change types of parameters

    internal lateinit var binding: FragmentEachDateBox2Binding

    // 감정다이어리에서 생성한 sharedPreferences 에서 날짜에 해당하는 점수를 넘겨받기 위해 해당 파일에 접근

    private lateinit var sharedPreferences: SharedPreferences

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

        binding.inputText.setText(
            commonUserDefinedObjectSet.getInputText(
                sharedPreferences,
                menuNum,
                givenKey = key
            )
        )

        binding.inputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            // 우리는 Text 가 입력되는 동안, 변동되는 모든 text 를 동적으로 sharedPreferences 에 계속 재지정한다.

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                commonUserDefinedObjectSet.updateInputText(
                    binding.inputText.text.toString(),
                    sharedPreferences,
                    menuNum,
                    givenKey = key
                )
            }

            override fun afterTextChanged(p0: Editable?) {

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
        fun newInstance(key: String, menuNum: Int) =
            EachDateBox2(key, menuNum).apply {
                arguments = Bundle().apply {

                }
            }
    }
}