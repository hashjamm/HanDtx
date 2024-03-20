package org.techtown.handtxver1.emotionDiary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ViewModelForEachDateViewer(private val repository: Repository) : ViewModel() {

    val objectSet = EmotionDiaryUserDefinedObjectSet()

    var dateString = String()
    private var weekdayString = String()
    private var dateWeekDayString = String()
    var daysInMonth: Int = 0

    fun setData(
        dateString: String,
        weekdayString: String,
        dateWeekDayString: String,
        daysInMonth: Int
    ) {
        this.dateString = dateString
        this.weekdayString = weekdayString
        this.dateWeekDayString = dateWeekDayString
        this.daysInMonth = daysInMonth
    }

    var score = MutableLiveData<Int?>()
    var inputText = MutableLiveData<String?>()

    var oneDateData = MutableLiveData<EachDateRecordDataClass>()
    private var textByScore = MutableLiveData<String>()

    fun getEmotionDiaryData(userID: String, date: String, type: Int) {
        viewModelScope.launch {
            val newData = repository.fetchEmotionDiaryData(userID, date)

            when (type) {
                1 -> {

                    score.value = newData?.score1
                    inputText.value = newData?.inputText1

                    textByScore.value =
                        if (score.value == null) {
                            "오늘 하루 어땠나요?"
                        } else {
                            objectSet.graphTextArray1[score.value!!]
                        }

                }
                2 -> {

                    score.value = newData?.score2
                    inputText.value = newData?.inputText2

                    textByScore.value =
                        if (score.value == null) {
                            "오늘 얼마나 불안했나요?"
                        } else {
                            objectSet.graphTextArray2[score.value!!]
                        }

                }
                3 -> {

                    score.value = newData?.score3
                    inputText.value = newData?.inputText3

                    textByScore.value =
                        if (score.value == null) {
                            "오늘 식욕은 어땠나요?"
                        } else {
                            objectSet.graphTextArray3[score.value!!]
                        }

                }
                else -> {

                    throw IllegalArgumentException("type range error")

                }

            }

            oneDateData.value =
                EachDateRecordDataClass(date, textByScore.value, inputText.value)

        }
    }

}