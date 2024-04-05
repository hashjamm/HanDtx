package org.techtown.handtxver1.lifeLog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentLifeLogData1Frag1Frag2Binding

// TODO: Rename parameter arguments, choose names that match


/**
 * A simple [Fragment] subclass.
 * Use the [LifeLogData1Frag1Frag1.newInstance] factory method to
 * create an instance of this fragment.
 */
class LifeLogData1Frag1Frag2 : Fragment() {

    // binding 변수 생성
    lateinit var binding: FragmentLifeLogData1Frag1Frag2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, lifeLog1Frag1Frag2: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_life_log_data1_frag1_frag2,
                lifeLog1Frag1Frag2,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}