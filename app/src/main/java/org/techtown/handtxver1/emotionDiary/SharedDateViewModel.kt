package org.techtown.handtxver1.emotionDiary

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

open class SharedDateViewModel(private val repository: Repository) : ViewModel() {
    val date = MutableLiveData<Calendar>()
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
    private val weekdayFormat: SimpleDateFormat = SimpleDateFormat("E", Locale.KOREA)
    private val dateWeekDayFormat: SimpleDateFormat = SimpleDateFormat("MM월 dd일(E)", Locale.KOREA)
    private val apiServerDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    val dateString = MutableLiveData<String>()
    val weekdayString = MutableLiveData<String>()
    val dateWeekDayString = MutableLiveData<String>()
    val apiServerDateString = MutableLiveData<String>()
    val daysInMonth = MutableLiveData<Int>()
    
    var obtainedDataNullStatus = MutableLiveData<Int>()

    var score1 = MutableLiveData<Int?>()
    var score2 = MutableLiveData<Int?>()
    var score3 = MutableLiveData<Int?>()
    var inputText1 = MutableLiveData<String?>()
    var inputText2 = MutableLiveData<String?>()
    var inputText3 = MutableLiveData<String?>()

    init {
        date.value = Calendar.getInstance()
        dateString.value = dateFormat.format(Calendar.getInstance().time)
        weekdayString.value = weekdayFormat.format(Calendar.getInstance().time)
        dateWeekDayString.value = dateWeekDayFormat.format(Calendar.getInstance().time)
        apiServerDateString.value = apiServerDateFormat.format(Calendar.getInstance().time)
        daysInMonth.value = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
        obtainedDataNullStatus.value = 0
    }

    fun observeDate(lifecycleOwner: LifecycleOwner) {
        date.observe(lifecycleOwner) {
            dateString.value = dateFormat.format(it.time)
            weekdayString.value = weekdayFormat.format(it.time)
            dateWeekDayString.value = dateWeekDayFormat.format(it.time)
            apiServerDateString.value = apiServerDateFormat.format(it.time)
            val calendar = Calendar.getInstance()
            calendar.time = it.time
            daysInMonth.value = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        }
    }

    fun addDate() {
        val currentCalendar = date.value
        currentCalendar?.add(Calendar.DAY_OF_YEAR, 1)
        date.value = currentCalendar!!
    }

    fun subtractDate() {
        val currentCalendar = date.value
        currentCalendar?.add(Calendar.DAY_OF_YEAR, -1)
        date.value = currentCalendar!!
    }

    fun getEmotionDiaryData(userID: String, date: String) {
        viewModelScope.launch {
            val newData = repository.fetchEmotionDiaryData(userID, date)
            
            score1.value = newData?.score1
            score2.value = newData?.score2
            score3.value = newData?.score3
            inputText1.value = newData?.inputText1
            inputText2.value = newData?.inputText2
            inputText3.value = newData?.inputText3

            if (newData == null) {
                obtainedDataNullStatus.value = 1
            } else {
                obtainedDataNullStatus.value = 2
            }
        }
    }

}