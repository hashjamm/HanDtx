package org.techtown.handtxver1

import androidx.lifecycle.ViewModel

class ViewModelForQuestionnairePages: ViewModel() {

    var scoreOfType1: Int = 0
    var scoreOfType2: Int = 0
    var scoreOfType3: Int = 0
    var scoreOfType4: Int = 0
    var scoreOfType5: Int = 0
    var scoreOfType6: Int = 0

    fun updateData(
        inputValue: Int,
        typeValue: Int
    ) {
        when (typeValue) {
            1 -> this.scoreOfType1 += inputValue
            2 -> this.scoreOfType2 += inputValue
            3 -> this.scoreOfType3 += inputValue
            4 -> this.scoreOfType4 += inputValue
            5 -> this.scoreOfType5 += inputValue
            6 -> this.scoreOfType6 += inputValue
        }
    }

}