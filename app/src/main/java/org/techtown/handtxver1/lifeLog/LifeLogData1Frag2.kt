package org.techtown.handtxver1.lifeLog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentLifeLogData1Frag2Binding

/**
 * A simple [Fragment] subclass.
 * Use the [LifeLogData1Frag2.newInstance] factory method to
 * create an instance of this fragment.
 */
class LifeLogData1Frag2 : Fragment() {

    // binding 변수 생성
    lateinit var binding: FragmentLifeLogData1Frag2Binding

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
                R.layout.fragment_life_log_data1_frag2,
                lifeLog1Frag1,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}