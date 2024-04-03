package org.techtown.handtxver1.emotionDiary

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ViewModelForEachDateViewer(private val repository: Repository) : ViewModel() {

    val objectSet = EmotionDiaryUserDefinedObjectSet()

    var dateString = String()
    var apiServerDateString = String()
    var daysInMonth: Int = 0

    // Recycler View 사용을 위한 데이터 클래스 리스트를 생성하기 위해 빈 라이브데이터 리스트 선언
    var mutableDataList = MutableLiveData<MutableList<EachDateRecordDataClass>>()

    fun setData(
        dateString: String,
        apiServerDateString: String,
        daysInMonth: Int
    ) {
        this.dateString = dateString
        this.apiServerDateString = apiServerDateString
        this.daysInMonth = daysInMonth
    }

    private fun dateFormatChanger(
        previousPattern: String,
        subsequentPattern: String,
        dateString: String,
        dayOfMonth: Int? = null
    ): String? {
        val standardFormat = SimpleDateFormat(previousPattern, Locale.KOREA)

        try {

            val primaryDate = standardFormat.parse(dateString)

            val calendar = Calendar.getInstance()
            calendar.time = primaryDate!!

            if (dayOfMonth != null) {
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }

            val formatter = SimpleDateFormat(subsequentPattern, Locale.KOREA)

            return formatter.format(calendar.time)

        } catch (e: ParseException) {
            // dateString의 형태와 포맷이 일치하지 않는 경우에 대한 처리
            e.printStackTrace()
            return null
        }
    }

    private fun getDaysInMonthList(): MutableList<String> {

        val dateList = mutableListOf<String>()

        for (dayOfMonth in 1..daysInMonth) {

            val currentDate =
                dateFormatChanger(
                    "yyyy.MM.dd",
                    "dd일 E",
                    dateString,
                    dayOfMonth
                )

            dateList.add(currentDate!!)

        }

        return dateList
    }


    fun getEmotionDiaryData(userID: String, type: Int) {
        viewModelScope.launch {

            val newData = repository.fetchEmotionDiaryDataMonthly(userID, apiServerDateString)
                ?: throw NullPointerException("response NullPointerException error")

            if (newData.size != daysInMonth) {

                throw IllegalStateException("response size error")

            } else {

                val dateList = getDaysInMonthList()

                val newDataList = mutableListOf<EachDateRecordDataClass>()

                when (type) {

                    1 -> {
                        dateList.forEachIndexed { idx, oneDateString ->

                            val score = newData[idx].score1
                            val inputText = newData[idx].inputText1

                            val textByScore =
                                if (score == null) {
                                    "오늘 하루 어땠나요?"
                                } else {
                                    objectSet.graphTextArray1[score]
                                }

                            val oneDateData =
                                EachDateRecordDataClass(oneDateString, textByScore, inputText)

                            newDataList.add(oneDateData)

                        }
                    }

                    2 -> {
                        dateList.forEachIndexed { idx, oneDateString ->

                            val score = newData[idx].score2
                            val inputText = newData[idx].inputText2

                            val textByScore =
                                if (score == null) {
                                    "오늘 얼마나 불안했나요?"
                                } else {
                                    objectSet.graphTextArray2[score]
                                }

                            val oneDateData =
                                EachDateRecordDataClass(oneDateString, textByScore, inputText)

                            newDataList.add(oneDateData)

                        }
                    }

                    3 -> {
                        dateList.forEachIndexed { idx, oneDateString ->

                            val score = newData[idx].score3
                            val inputText = newData[idx].inputText3

                            val textByScore =
                                if (score == null) {
                                    "오늘 식욕은 어땠나요?"
                                } else {
                                    objectSet.graphTextArray3[score]
                                }

                            val oneDateData =
                                EachDateRecordDataClass(oneDateString, textByScore, inputText)

                            newDataList.add(oneDateData)

                        }
                    }

                    else -> {
                        throw IllegalArgumentException("type range error")
                    }

                }

                mutableDataList.postValue(newDataList)

            }

        }

    }
}