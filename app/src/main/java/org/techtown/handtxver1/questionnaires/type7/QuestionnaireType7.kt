package org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type7

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AlignmentSpan
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import org.techtown.handtxver1.R
import org.techtown.handtxver1.org.techtown.handtxver1.CommonUserDefinedObjectSet
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.QuestionnaireMainPage

class QuestionnaireType7 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_type7)

        // CommonUserDefinedObjectSet 클래스 인스턴스 생성
        val commonUserDefinedObjectSet = CommonUserDefinedObjectSet()

        // ViewModel 에 접근 및 로딩
        val viewModel = ViewModelProvider(this).get(ViewModelForQType7::class.java)

        val presentPageBar =
            findViewById<androidx.appcompat.widget.AppCompatImageView>(R.id.presentPageBar)
        val pageBar = findViewById<ConstraintLayout>(R.id.pageBar)
        val toPreviousPage =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.previous_page)
        val toNextPage = findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.next_page)
        val pageNumberBox =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.pageNumberBox1)
        val submitButton =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.submitButton)

        val page1 = QType7ContentPage1()
        val page2 = QType7ContentPage2()
        val page3 = QType7ContentPage3()
        val page4 = QType7ContentPage4()
        val page5 = QType7ContentPage5()
        val page6 = QType7ContentPage6()
        val page7 = QType7ContentPage7()
        val page8 = QType7ContentPage8()
        val page9 = QType7ContentPage9()
        val page10 = QType7ContentPage10()
        val page11 = QType7ContentPage11()
        val page12 = QType7ContentPage12()
        val page13 = QType7ContentPage13()

        val pageSequence = arrayOf(
            page1, page2, page3, page4, page5, page6, page7, page8, page9, page10, page11, page12, page13
        )

        val frameLayoutID = R.id.pageFrame

        val responseSequence = viewModel.responseSequence

        val checkedStateArray = viewModel.checkedStateArray

        commonUserDefinedObjectSet.questionnaireActivityFunction(
            this,
            frameLayoutID,
            pageSequence,
            pageBar,
            presentPageBar,
            pageNumberBox,
            toPreviousPage,
            toNextPage,
            submitButton,
            7,
            responseSequence,
            checkedStateArray
        )

    }
}




