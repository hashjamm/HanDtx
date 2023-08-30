package org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.drinking

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
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.QuestionnaireMainPage
import org.techtown.handtxver1.R
import org.techtown.handtxver1.org.techtown.handtxver1.CommonUserDefinedObjectSet

class DrinkingQuestionnaire : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinking_questionnaire)

        // CommonUserDefinedObjectSet 클래스 인스턴스 생성
        val commonUserDefinedObjectSet = CommonUserDefinedObjectSet()

        // ViewModel 에 접근 및 로딩
        val viewModel = ViewModelProvider(this).get(ViewModelForDrinkingQuestionnaire::class.java)

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

        val page1 = DrinkingQuestionnaireContentPage1()
        val page2 = DrinkingQuestionnaireContentPage2()
        val page3 = DrinkingQuestionnaireContentPage3()
        val page4 = DrinkingQuestionnaireContentPage4()
        val page5 = DrinkingQuestionnaireContentPage5()
        val page6 = DrinkingQuestionnaireContentPage6()
        val page7 = DrinkingQuestionnaireContentPage7()
        val page8 = DrinkingQuestionnaireContentPage8()
        val page9 = DrinkingQuestionnaireContentPage9()
        val page10 = DrinkingQuestionnaireContentPage10()

        val pageSequence = arrayOf(
            page1, page2, page3, page4, page5, page6, page7, page8, page9, page10
        )

        val frameLayoutID = R.id.pageFrame

        val responseSequence = viewModel.responseSequence

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
            80,
            responseSequence
        )
    }
}

