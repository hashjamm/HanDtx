package org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type7

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentQType7ContentPage13Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [QType7ContentPage1.newInstance] factory method to
 * create an instance of this fragment.
 */
class QType7ContentPage13 : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentQType7ContentPage13Binding

    private val viewModel: ViewModelForQType7 by activityViewModels()

    private lateinit var checkBoxes: Array<CheckBox>

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
                R.layout.fragment_q_type7_content_page13,
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

        checkBoxes = arrayOf(
            binding.box1,
            binding.box2,
            binding.box3,
            binding.box4,
            binding.box5,
            binding.box6,
            binding.box7,
            binding.box8,
            binding.box9,
            binding.box10,
            binding.box11,
            binding.box12,
            binding.box13,
            binding.box14,
            binding.box15,
            binding.box16,
            binding.box17,
            binding.box18
        )

        for (index in checkBoxes.indices) {
            when (viewModel.checkedStateArray[index]) {
                1 -> checkBoxes[index].isChecked = true
                0 -> checkBoxes[index].isChecked = false
            }
        }

        checkBoxes.forEachIndexed { index, checkBox ->

            val boxNumber = index + 1

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.updateChecking(boxNumber, 1)

                    if (viewModel.checkedStateArray.sum() > 3) {

                        val dialogOfResponses = AlertDialog.Builder(requireContext())
                            .setTitle("선택 오류 발생")
                            .setMessage(
                                "3개 미만으로 선택해주세요."
                            )

                        dialogOfResponses.show()

                        checkBox.isChecked = false
                        viewModel.updateChecking(boxNumber, 0)
                    }
                } else {
                    viewModel.updateChecking(boxNumber, 0)
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
            QType7ContentPage13().apply {
                arguments = Bundle().apply {

                }
            }
    }
}