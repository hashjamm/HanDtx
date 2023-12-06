package org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type7

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentQType7ContentPage2Binding
import org.techtown.handtxver1.questionnaires.type7.ViewModelForQType7

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [QType7ContentPage1.newInstance] factory method to
 * create an instance of this fragment.
 */
class QType7ContentPage2 : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentQType7ContentPage2Binding

    private val viewModel: ViewModelForQType7 by activityViewModels()

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
                R.layout.fragment_q_type7_content_page2,
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

        // 원 중심에 위치한 text view 에 viewModel 에 저장된 횟수에 해당하는 값이 있다면 가져오고, 없다면 비워두는 코드

        if (viewModel.responseSequence[1] != null) {
            binding.circleAnchor.text = "${viewModel.responseSequence[1]} 회"
        }

        binding.button1.setOnClickListener { onRadioButtonClicked(it) }
        binding.button2.setOnClickListener { onRadioButtonClicked(it) }
        binding.button3.setOnClickListener { onRadioButtonClicked(it) }
        binding.button4.setOnClickListener { onRadioButtonClicked(it) }
        binding.button5.setOnClickListener { onRadioButtonClicked(it) }
        binding.button6.setOnClickListener { onRadioButtonClicked(it) }
        binding.button7.setOnClickListener { onRadioButtonClicked(it) }
        binding.button8.setOnClickListener { onRadioButtonClicked(it) }

    }

    @SuppressLint("SetTextI18n")
    private fun onRadioButtonClicked(view: View) {
        // onRadioButtonClicked 메서드를 직접 구현해주는 코드를 작성.
        // 각 radioButton 에 대하여 클릭된 버튼의 글씨 색만을 하얀색, 나머지는 검은색으로 지정.
        // 이후 ViewModel 에 체크상태 전달을 위한 장소이기도 함.

        val radioButtonMap = mapOf(
            R.id.button1 to 0,
            R.id.button2 to 1,
            R.id.button3 to 2,
            R.id.button4 to 3,
            R.id.button5 to 4,
            R.id.button6 to 5,
            R.id.button7 to 6,
            R.id.button8 to 7
        )

        if (view is RadioButton) {
            val checked = view.isChecked
            val responseValue = radioButtonMap[view.id]

            if (checked && responseValue != null) {

                // 나머지 버튼들의 체크 상태를 해제
                for ((buttonId, _) in radioButtonMap) {
                    if (buttonId != view.id) {
                        val radioButton = binding.root.findViewById<RadioButton>(buttonId)
                        radioButton?.isChecked = false
                    }
                }

                binding.circleAnchor.text = "${responseValue}회"
                viewModel.updateResponse(2, responseValue)
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
         * @return A new instance of fragment QType7ContentPage1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            QType7ContentPage2().apply {
                arguments = Bundle().apply {

                }
            }
    }
}