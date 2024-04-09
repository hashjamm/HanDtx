package org.techtown.handtxver1.lifeLog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.FragmentLifeLogData1Frag1Frag2Binding
import java.util.*

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

        val valueCount = 6
        val barCount = 5

        // 1. 막대 데이터셋 만들기
        val barEntriesGroup1: ArrayList<BarEntry> = ArrayList()
        val barEntriesGroup2: ArrayList<BarEntry> = ArrayList()

        val valuesGroup1 = arrayListOf(125f, 128f, 120f, 123f, 121f, 119f)
        val valuesGroup2 = arrayListOf(85f, 83f, 75f, 79f, 77f, 75f)

        // 데이터 추가
        for (i in 0 until valueCount) {
            barEntriesGroup1.add(BarEntry(i.toFloat(), valuesGroup1[i]))
            barEntriesGroup2.add(BarEntry(i.toFloat(), valuesGroup2[i]))
        }

        // BarDataSet 생성
        val barDataSet1 = BarDataSet(barEntriesGroup1, "수축기")
        val barDataSet2 = BarDataSet(barEntriesGroup2, "이완기")

        // 막대 색상 설정
        barDataSet1.colors =  ColorTemplate.MATERIAL_COLORS.take(barCount)
        barDataSet2.colors = ColorTemplate.MATERIAL_COLORS.take(barCount)

        // 2. 선 데이터셋 만들기
        val lineEntriesGroup1: ArrayList<Entry> = ArrayList()
        val lineEntriesGroup2: ArrayList<Entry> = ArrayList()

        // 데이터 추가
        for (i in 0 until valueCount) {
            lineEntriesGroup1.add(Entry(i.toFloat(), valuesGroup1[i]))
            lineEntriesGroup2.add(Entry(i.toFloat(), valuesGroup2[i]))
        }

        // LineDataSet 생성
        val lineDataSet1 = LineDataSet(lineEntriesGroup1, "수축기")
        val lineDataSet2 = LineDataSet(lineEntriesGroup2, "이완기")

        // 막대 색상 설정
        lineDataSet1.color =  Color.RED
        lineDataSet2.color = Color.BLUE

        // 3. 각 그룹을 결합 데이터에 추가
        val combinedData = CombinedData()

        val barData = BarData(barDataSet1, barDataSet2)
        combinedData.setData(barData)

        val lineData = LineData(lineDataSet1, lineDataSet2)
        combinedData.setData(lineData)

        // 4. 그룹화 설정
        val groupSpace = 0.12f
        val barSpace = 0.02f
        val barWidth = 0.31f // (barWidth * 2) + barSpace + groupSpace = 1
        barData.barWidth = barWidth

        // 5. 차트 설정
        val combinedChart = binding.combinedChart
        combinedChart.description.isEnabled = false
        combinedChart.setBackgroundColor(Color.WHITE)
        combinedChart.setDrawGridBackground(false)
        combinedChart.setDrawBarShadow(false)
        combinedChart.isHighlightFullBarEnabled = false

        combinedChart.data = combinedData

        combinedChart.xAxis.axisMinimum = 0f
        combinedChart.xAxis.axisMaximum = 0 + combinedChart.barData.getGroupWidth(groupSpace, barSpace) * valueCount
        combinedChart.groupBars(0f, groupSpace, barSpace)

        combinedChart.invalidate() // 차트 갱신

    }

}