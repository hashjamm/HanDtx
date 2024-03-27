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
    var viewModel: ViewModelForEachDateViewer,
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

        return OneDateViewHolder(view)

    }

    override fun onBindViewHolder(holder: OneDateViewHolder, position: Int) {

        val positionData = mutableDataList.getOrNull(position)

        if (positionData == null) {

            throw NullPointerException("positionData : null variable")

        } else {

            holder.bind(positionData)

            val line1 = holder.itemView.findViewById<ConstraintLayout>(R.id.line1)
            val line2 = holder.itemView.findViewById<ConstraintLayout>(R.id.line2)
            val line1A = holder.itemView.findViewById<AppCompatEditText>(R.id.line1A)
            val line1B = holder.itemView.findViewById<AppCompatTextView>(R.id.line1B)

            val isExpandable = positionData.isExpandable

            line1A.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //TODO("Not yet implemented")
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //TODO("Not yet implemented")
                }

                override fun afterTextChanged(p0: Editable?) {
                    positionData.inputText = p0.toString()
                }
            })

            line1.visibility =
                if (isExpandable) View.VISIBLE else View.GONE

            // 데이터 업데이트 버튼
            line1B.setOnClickListener {

                positionData.isExpandable = !positionData.isExpandable

                callBackInterface.callBackEachDateEmotionDiary(
                    viewModel,
                    true,
                    holder.adapterPosition + 1,
                    positionData
                )

                mutableDataList[position] = positionData

                notifyItemChanged(position)
            }


            // 기본으로 계속 보여줘야하는 라인
            line2.setOnClickListener {

                positionData.isExpandable = !positionData.isExpandable

                mutableDataList[position] = positionData

                notifyItemChanged(position)
            }

        }

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