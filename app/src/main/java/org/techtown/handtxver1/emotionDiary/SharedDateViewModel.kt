package org.techtown.handtxver1.emotionDiary

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

open class SharedDateViewModel : ViewModel() {
    val date = MutableLiveData<Calendar>()
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
    private val weekdayFormat: SimpleDateFormat = SimpleDateFormat("E", Locale.KOREA)
    private val dateWeekDayFormat: SimpleDateFormat = SimpleDateFormat("MM월 dd일(E)", Locale.KOREA)
    val dateString = MutableLiveData<String>()
    val weekdayString = MutableLiveData<String>()
    val dateWeekDayString = MutableLiveData<String>()
    val daysInMonth = MutableLiveData<Int>()

    var obtainedScore = MutableLiveData<Int?>()

    init {
        date.value = Calendar.getInstance()
        dateString.value = dateFormat.format(Calendar.getInstance().time)
        weekdayString.value = weekdayFormat.format(Calendar.getInstance().time)
        dateWeekDayString.value = dateWeekDayFormat.format(Calendar.getInstance().time)
        daysInMonth.value = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun observeDate(lifecycleOwner: LifecycleOwner) {
        date.observe(lifecycleOwner) {
            dateString.value = dateFormat.format(it.time)
            weekdayString.value = weekdayFormat.format(it.time)
            dateWeekDayString.value = dateWeekDayFormat.format(it.time)
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

    fun substractDate() {
        val currentCalendar = date.value
        currentCalendar?.add(Calendar.DAY_OF_YEAR, -1)
        date.value = currentCalendar!!
    }

}