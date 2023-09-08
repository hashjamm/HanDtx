package org.techtown.handtxver1.questionnaires.type10

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentQType10ContentPage11Binding
import org.techtown.handtxver1.org.techtown.handtxver1.ApplicationClass
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type10.ViewModelForQType10

class QType10ContentPage11 : Fragment() {

    private lateinit var binding: FragmentQType10ContentPage11Binding

    private val viewModel: ViewModelForQType10 by activityViewModels()

    val snackDataSharedPreferences = ApplicationClass.snackDataSharedPreferences

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
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_q_type10_content_page11,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.snackType.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            // 우리는 Text 가 입력되는 동안, text 입력 여부를 바탕으로 체크박스 체크 상태를 동적으로 변경
            // 또한 commonUserDefinedObject 의 oneSurveyResult 데이터 클래스의 내용을 업데이트

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                viewModel.updateSnackResponse(p0.toString(), viewModel.snackResponse.num)

                if (viewModel.snackResponse.type == "" || viewModel.snackResponse.num == 0) {
                    viewModel.initializingResponse(11)
                } else {
                    viewModel.updateResponse(11, 1)
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.snackConsumedNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            // 우리는 Text 가 입력되는 동안, text 입력 여부를 바탕으로 체크박스 체크 상태를 동적으로 변경
            // 또한 commonUserDefinedObject 의 oneSurveyResult 데이터 클래스의 내용을 업데이트

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val text = p0.toString()
                val intValue = if (text == "") {
                    0
                } else {
                    text.toInt()
                }
                // viewModel.snackConsumedNumber = intValue

                viewModel.updateSnackResponse(viewModel.snackResponse.type, intValue)

                if (viewModel.snackResponse.type == "" || viewModel.snackResponse.num == 0) {
                    viewModel.initializingResponse(11)
                } else {
                    viewModel.updateResponse(11, 1)
                }

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
         * @return A new instance of fragment QType9ContentPage1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QType10ContentPage11().apply {
                arguments = Bundle().apply {

                }
            }
    }
}