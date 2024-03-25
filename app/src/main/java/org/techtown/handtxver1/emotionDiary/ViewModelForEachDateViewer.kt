package org.techtown.handtxver1.emotionDiary

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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

    private var score = MutableLiveData<Int?>()
    var inputText = MutableLiveData<String?>()

    // Recycler View 사용을 위한 데이터 클래스 리스트를 생성하기 위해 빈 리스트 선언
    var mutableDataList = mutableListOf<EachDateRecordDataClass>()
    var textByScore = MutableLiveData<String>()

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

            val formattedDate =
                dateFormatChanger(
                    "yyyy-MM-dd",
                    "dd일 E",
                    date
                )

            val oneDateData =
                EachDateRecordDataClass(formattedDate, textByScore.value, inputText.value)

            mutableDataList.add(oneDateData)

            Log.d("innnnneer final", "$mutableDataList")

        }
    }

    fun dateFormatChanger(
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

}