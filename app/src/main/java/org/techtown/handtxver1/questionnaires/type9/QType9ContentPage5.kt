package org.techtown.handtxver1.questionnaires.type9

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentQType9ContentPage5Binding
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type9.ViewModelForQType9

class QType9ContentPage5 : Fragment() {

    private lateinit var binding: FragmentQType9ContentPage5Binding

    private val viewModel: ViewModelForQType9 by activityViewModels()

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
                R.layout.fragment_q_type9_content_page5,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // onViewCreated 메서드를 재정의 하되, onRadioButtonClicked 가 실행되도록 재정의
        // onRadioButtonClicked 는 반드시 view 를 파라미터로 가져야 함. 왜냐하면 radioButton 클릭시 실행되는 매서드라
        // 해당 버튼이 파라미터로 와야함. 따라서 뒤에 구현할 때, 반드시 파라미터를 View 인스턴스로 작성
        binding.button1.setOnClickListener { onRadioButtonClicked(it) }
        binding.button2.setOnClickListener { onRadioButtonClicked(it) }
        binding.button3.setOnClickListener { onRadioButtonClicked(it) }
        binding.button4.setOnClickListener { onRadioButtonClicked(it) }
        binding.button5.setOnClickListener { onRadioButtonClicked(it) }

    }

    private fun onRadioButtonClicked(view: View) {
        // onRadioButtonClicked 메서드를 직접 구현해주는 코드를 작성.
        // 각 radioButton 에 대하여 클릭된 버튼의 글씨 색만을 하얀색, 나머지는 검은색으로 지정.
        // 이후 ViewModel 에 체크상태 전달을 위한 장소이기도 함.

        val radioButtonMap = mapOf(
            R.id.button1 to 1,
            R.id.button2 to 2,
            R.id.button3 to 3,
            R.id.button4 to 4,
            R.id.button5 to 5
        )

        if (view is RadioButton) {
            val checked = view.isChecked
            val responseValue = radioButtonMap[view.id]

            if (checked && responseValue != null) {
                viewModel.updateResponse(5, responseValue)
            }

        }

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
            QType9ContentPage5().apply {
                arguments = Bundle().apply {

                }
            }
    }
}