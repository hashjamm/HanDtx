package org.techtown.handtxver1.lifeLog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentLifeLogData1Frag1Binding

/**
 * A simple [Fragment] subclass.
 * Use the [LifeLogData1Frag1.newInstance] factory method to
 * create an instance of this fragment.
 */
class LifeLogData1Frag1 : Fragment() {

    // binding 변수 생성
    lateinit var binding: FragmentLifeLogData1Frag1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, lifeLog1Frag1: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_life_log_data1_frag1,
                lifeLog1Frag1,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val childFragment1 = LifeLogData1Frag1Frag1()
        val childFragment2 = LifeLogData1Frag1Frag2()

        childFragmentManager.beginTransaction().add(R.id.frame, childFragment1).commitNow()

        binding.frameChanger.setOnClickListener {

            val currentChildFragment = childFragmentManager.findFragmentById(R.id.frame)

            if (currentChildFragment is LifeLogData1Frag1Frag1) {
                binding.frameChanger.text = "확인"
                childFragmentManager.beginTransaction().replace(R.id.frame, childFragment2).commitNow()
            } else {
                binding.frameChanger.text = "그래프 보기"
                childFragmentManager.beginTransaction().replace(R.id.frame, childFragment1).commitNow()
            }

        }

        // 그래프를 이루는 라디오버튼들을 배열로 묶어둔 인스턴스 생성
        val radioGroup = arrayOf(
            binding.type1,
            binding.type2,
            binding.type3,
            binding.type4
        )

        radioGroup.forEachIndexed { index, button ->
            button.setOnClickListener {
                if (it is RadioButton) {
                    if (it.isChecked) {

                        // 라디오 그룹 기능 구현
                        radioGroup.forEachIndexed { num, radioButton ->
                            if (index != num) {
                                radioButton.isChecked = false
                            }
                        }
                    }
                }
            }
        }

    }

}