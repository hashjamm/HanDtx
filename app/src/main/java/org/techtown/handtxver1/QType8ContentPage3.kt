package org.techtown.handtxver1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.techtown.handtxver1.databinding.FragmentQType8ContentPage3Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [QType7ContentPage1.newInstance] factory method to
 * create an instance of this fragment.
 */
class QType8ContentPage3 : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentQType8ContentPage3Binding

    private val viewModel: ViewModelForQType8 by activityViewModels()

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
                R.layout.fragment_q_type8_content_page3,
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

        if (viewModel.responseSequence[2] != null) {
            binding.circleAnchor.text = "${viewModel.responseSequence[2]} 회"
        }

        binding.button1.setOnClickListener {
            if (it is RadioButton) {
                if (it.isChecked) {
                    viewModel.updateResponse(3, 1)
                    binding.circleAnchor.text = "1회"
                    binding.button2.isChecked = false
                    binding.button3.isChecked = false
                    binding.button4.isChecked = false
                    binding.button5.isChecked = false
                    binding.button6.isChecked = false
                    binding.button7.isChecked = false
                }
            }
        }

        binding.button2.setOnClickListener {
            if (it is RadioButton) {
                if (it.isChecked) {
                    viewModel.updateResponse(3, 2)
                    binding.circleAnchor.text = "2회"
                    binding.button1.isChecked = false
                    binding.button3.isChecked = false
                    binding.button4.isChecked = false
                    binding.button5.isChecked = false
                    binding.button6.isChecked = false
                    binding.button7.isChecked = false
                }
            }
        }

        binding.button3.setOnClickListener {
            if (it is RadioButton) {
                if (it.isChecked) {
                    viewModel.updateResponse(3, 3)
                    binding.circleAnchor.text = "3회"
                    binding.button1.isChecked = false
                    binding.button2.isChecked = false
                    binding.button4.isChecked = false
                    binding.button5.isChecked = false
                    binding.button6.isChecked = false
                    binding.button7.isChecked = false
                }
            }
        }

        binding.button4.setOnClickListener {
            if (it is RadioButton) {
                if (it.isChecked) {
                    viewModel.updateResponse(3, 4)
                    binding.circleAnchor.text = "4회"
                    binding.button1.isChecked = false
                    binding.button2.isChecked = false
                    binding.button3.isChecked = false
                    binding.button5.isChecked = false
                    binding.button6.isChecked = false
                    binding.button7.isChecked = false
                }
            }
        }

        binding.button5.setOnClickListener {
            if (it is RadioButton) {
                if (it.isChecked) {
                    viewModel.updateResponse(3, 5)
                    binding.circleAnchor.text = "5회"
                    binding.button1.isChecked = false
                    binding.button2.isChecked = false
                    binding.button3.isChecked = false
                    binding.button4.isChecked = false
                    binding.button6.isChecked = false
                    binding.button7.isChecked = false
                }
            }
        }

        binding.button6.setOnClickListener {
            if (it is RadioButton) {
                if (it.isChecked) {
                    viewModel.updateResponse(3, 6)
                    binding.circleAnchor.text = "6회"
                    binding.button1.isChecked = false
                    binding.button2.isChecked = false
                    binding.button3.isChecked = false
                    binding.button4.isChecked = false
                    binding.button5.isChecked = false
                    binding.button7.isChecked = false
                }
            }
        }

        binding.button7.setOnClickListener {
            if (it is RadioButton) {
                if (it.isChecked) {
                    viewModel.updateResponse(3, 7)
                    binding.circleAnchor.text = "7회"
                    binding.button1.isChecked = false
                    binding.button2.isChecked = false
                    binding.button3.isChecked = false
                    binding.button4.isChecked = false
                    binding.button5.isChecked = false
                    binding.button6.isChecked = false
                }
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
            QType8ContentPage3().apply {
                arguments = Bundle().apply {

                }
            }
    }
}