package org.techtown.handtxver1.emotionDiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import org.techtown.handtxver1.R
import java.util.*

class EachDateRecyclerViewAdapter(
    private var mutableDataList: MutableList<EachDateRecordDataClass>,
    date: Date,
    menuNum: Int
) :
    RecyclerView.Adapter<EachDateRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.each_date_frame_layout_item, parent, false)

        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(mutableDataList[position])

    }

    override fun getItemCount(): Int {

        return mutableDataList.count()

    }

    val eachDateBox1 = EachDateBox1(date, menuNum)
    val eachDateBox2 = EachDateBox2(date, menuNum)

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val frameLayout: FrameLayout = itemView.findViewById(R.id.oneLineFrame)

        fun bind(item: EachDateRecordDataClass) {

            // 트랜잭션에 필요한 코드 일부를 미리 지정해둠
            val fragmentTransaction =
                (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()

            // FrameLayout 에 우선 EachDateBox1 fragment 를 트랜잭션
            fragmentTransaction.add(R.id.oneLineFrame, eachDateBox1).commitNow()

            // 해당 FrameLayout 에 대한 리스너
            itemView.setOnClickListener {

                // 현재 트랜잭션된 fragment 의 클래스 명을 가져옴
                val currentFragment =
                    (itemView.context as FragmentActivity).supportFragmentManager.findFragmentById(R.id.oneLineFrame)

                if (currentFragment is EachDateBox1) {

                    fragmentTransaction.replace(R.id.oneLineFrame, eachDateBox2).commitNow()

                } else {

                    fragmentTransaction.replace(R.id.oneLineFrame, eachDateBox1).commitNow()

                }

            }

        }

    }

}