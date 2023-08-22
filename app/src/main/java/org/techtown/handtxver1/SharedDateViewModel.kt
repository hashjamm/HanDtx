package org.techtown.handtxver1

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import java.text.SimpleDateFormat
import java.util.*

open class SharedDateViewModel : ViewModel() {
    val date = MutableLiveData<Calendar>()
    val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
    val weekdayFormat: SimpleDateFormat = SimpleDateFormat("E", Locale.KOREA)
    val dateWeekDayFormat: SimpleDateFormat = SimpleDateFormat("MM월 dd일(E)", Locale.KOREA)
    val dateString = MutableLiveData<String>()
    val weekdayString = MutableLiveData<String>()
    val dateWeekDayString = MutableLiveData<String>()
    val daysInMonth = MutableLiveData<Int>()

    init {
        date.value = Calendar.getInstance()
        dateString.value = dateFormat.format(Calendar.getInstance().time)
        weekdayString.value = weekdayFormat.format(Calendar.getInstance().time)
        dateWeekDayString.value = dateWeekDayFormat.format(Calendar.getInstance().time)
        daysInMonth.value = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun observeDate(lifecycleOwner: LifecycleOwner) {
        date.observe(lifecycleOwner, Observer {
            dateString.value = dateFormat.format(it.time)
            weekdayString.value = weekdayFormat.format(it.time)
            dateWeekDayString.value = dateWeekDayFormat.format(it.time)
            val calendar = Calendar.getInstance()
            calendar.time = it.time
            daysInMonth.value = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        })
    }

    public fun addDate() {
        val currentCalendar = date.value
        currentCalendar?.add(Calendar.DAY_OF_YEAR, 1)
        date.value = currentCalendar!!
    }

    public fun substractDate() {
        val currentCalendar = date.value
        currentCalendar?.add(Calendar.DAY_OF_YEAR, -1)
        date.value = currentCalendar!!
    }

}