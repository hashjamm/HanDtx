package org.techtown.handtxver1.emotionDiary

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import org.techtown.handtxver1.CallBackInterface
import org.techtown.handtxver1.R
import org.techtown.handtxver1.databinding.EachDateItemBinding

class EachDateRecyclerViewAdapter(
    private var mutableDataList: MutableList<EachDateRecordDataClass>,
    private var callBackInterface: CallBackInterface
) :
    RecyclerView.Adapter<EachDateRecyclerViewAdapter.OneDateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OneDateViewHolder {

        val view = EachDateItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return OneDateViewHolder(view).also { holder ->

            val line1 = holder.itemView.findViewById<ConstraintLayout>(R.id.line1)
            val line2 = holder.itemView.findViewById<ConstraintLayout>(R.id.line2)
            val line1A = holder.itemView.findViewById<AppCompatEditText>(R.id.line1A)
            val line1B = holder.itemView.findViewById<AppCompatTextView>(R.id.line1B)

            // positionData 가 바뀐다고 실제로 mutableDataList 에 변화를 바로 줄 수 있지는 않음
            // 이후에 아래의 코드를 추가해줘야 함
            // mutableDataList[holder.adapterPosition] = positionData

            val positionData = mutableDataList.getOrNull(holder.adapterPosition)

            val isExpandable = positionData?.isExpandable

            line1A.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //TODO("Not yet implemented")
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //TODO("Not yet implemented")
                }

                override fun afterTextChanged(p0: Editable?) {
                    positionData?.inputText = p0.toString()
                }
            })

            line1.visibility =
                if (isExpandable == true) View.VISIBLE else View.GONE

            // 데이터 업데이트 버튼
            line1B.setOnClickListener {
                if (positionData != null) {
                    positionData.isExpandable = !positionData.isExpandable
                } else {
                    throw IllegalStateException("에러 발생 : there is no data : isExpandable.")
                }

                callBackInterface.onCallBackValueChanged(
                    true,
                    holder.adapterPosition + 1,
                    positionData
                )

                mutableDataList[holder.adapterPosition] = positionData

                notifyItemChanged(holder.adapterPosition)
            }


            // 기본으로 계속 보여줘야하는 라인
            line2.setOnClickListener {

                if (positionData != null) {
                    positionData.isExpandable = !positionData.isExpandable
                } else {
                    throw IllegalStateException("에러 발생 : there is no data : isExpandable.")
                }

                mutableDataList[holder.adapterPosition] = positionData

                notifyItemChanged(holder.adapterPosition)
            }

        }

    }

    override fun onBindViewHolder(holder: OneDateViewHolder, position: Int) {

        holder.bind(mutableDataList[position])

    }

    override fun getItemCount(): Int {

        return mutableDataList.count()

    }

    inner class OneDateViewHolder(private val binding: EachDateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(oneDateRecord: EachDateRecordDataClass) {

            binding.line1A.setText(oneDateRecord.inputText)
            binding.line2A.text = oneDateRecord.dateStringInLine
            binding.line2B.text = oneDateRecord.stringByScore

            val isExpandable = oneDateRecord.isExpandable

            binding.line1.visibility =
                if (isExpandable) View.VISIBLE else View.GONE

        }

    }

}