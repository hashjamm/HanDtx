package org.techtown.handtxver1.emotionDiary

import androidx.lifecycle.ViewModel

class ViewModelForEachDateViewer : ViewModel() {

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

}