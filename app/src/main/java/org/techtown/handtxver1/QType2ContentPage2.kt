package org.techtown.handtxver1

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.techtown.handtxver1.databinding.FragmentQType2ContentPage2Binding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [QType2ContentPage2.newInstance] factory method to
 * create an instance of this fragment.
 */
class QType2ContentPage2 : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentQType2ContentPage2Binding

    private val viewModel: ViewModelForQType2 by activityViewModels()

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
                R.layout.fragment_q_type2_content_page2,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // onViewCreated 메서드를 재정의 하되, onRadioButtonClicked 가 실행되도록 재정의
        binding.button1.setOnClickListener { onRadioButtonClicked(it) }
        binding.button2.setOnClickListener { onRadioButtonClicked(it) }
        binding.button3.setOnClickListener { onRadioButtonClicked(it) }
        binding.button4.setOnClickListener { onRadioButtonClicked(it) }
        binding.button5.setOnClickListener { onRadioButtonClicked(it) }
    }

    // 클릭 이벤트에 대한 처리를 위해 onClick 관련 속성을 사용하기 위해서는 Fragment 에서는 아래와 같이
    // 직접 작성해주는 작업이 필요함. 우선 View.OnClickListener 를 상속받는 코드를 상단에서 작성하고, 아래의 코드로 재정의 해준다.

    private fun onRadioButtonClicked(view: View) {
        // setOnClickListener 메서드를 직접 구현해주는 코드를 작성.
        // 여기에서 우리는 inflate 한 view 객체인 binding 에서 menubar 라는 ViewGroup 에 접근하여
        // 이들의 자식 View 들을 Sequence 로서 가져올 수 있음
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.id) {
                R.id.button1 -> {
                    if (checked) {
                        view.setTextColor(Color.parseColor("#FFFFFF"))
                        binding.button2.setTextColor(Color.parseColor("#000000"))
                        binding.button3.setTextColor(Color.parseColor("#000000"))
                        binding.button4.setTextColor(Color.parseColor("#000000"))
                        binding.button5.setTextColor(Color.parseColor("#000000"))
                        viewModel.updateResponse(2, 1)
                    }
                }

                R.id.button2 -> {
                    if (checked) {
                        view.setTextColor(Color.parseColor("#FFFFFF"))
                        binding.button1.setTextColor(Color.parseColor("#000000"))
                        binding.button3.setTextColor(Color.parseColor("#000000"))
                        binding.button4.setTextColor(Color.parseColor("#000000"))
                        binding.button5.setTextColor(Color.parseColor("#000000"))
                        viewModel.updateResponse(2, 2)
                    }
                }

                R.id.button3 -> {
                    if (checked) {
                        view.setTextColor(Color.parseColor("#FFFFFF"))
                        binding.button1.setTextColor(Color.parseColor("#000000"))
                        binding.button2.setTextColor(Color.parseColor("#000000"))
                        binding.button4.setTextColor(Color.parseColor("#000000"))
                        binding.button5.setTextColor(Color.parseColor("#000000"))
                        viewModel.updateResponse(2, 3)
                    }
                }

                R.id.button4 -> {
                    if (checked) {
                        view.setTextColor(Color.parseColor("#FFFFFF"))
                        binding.button1.setTextColor(Color.parseColor("#000000"))
                        binding.button2.setTextColor(Color.parseColor("#000000"))
                        binding.button3.setTextColor(Color.parseColor("#000000"))
                        binding.button5.setTextColor(Color.parseColor("#000000"))
                        viewModel.updateResponse(2, 4)
                    }
                }

                R.id.button5 -> {
                    if (checked) {
                        view.setTextColor(Color.parseColor("#FFFFFF"))
                        binding.button1.setTextColor(Color.parseColor("#000000"))
                        binding.button2.setTextColor(Color.parseColor("#000000"))
                        binding.button3.setTextColor(Color.parseColor("#000000"))
                        binding.button4.setTextColor(Color.parseColor("#000000"))
                        viewModel.updateResponse(2, 5)
                    }
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
         * @return A new instance of fragment QType2ContentPage2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            QType2ContentPage2().apply {
                arguments = Bundle().apply {

                }
            }
    }
}