package org.techtown.handtxver1.questionnaires.type10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import org.techtown.handtxver1.R
import org.techtown.handtxver1.org.techtown.handtxver1.CommonUserDefinedObjectSet
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type10.ViewModelForQType10
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type9.ViewModelForQType9
import org.techtown.handtxver1.questionnaires.type9.*

class QuestionnaireType10 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_type10)

        // CommonUserDefinedObjectSet 클래스 인스턴스 생성
        val commonUserDefinedObjectSet = CommonUserDefinedObjectSet()

        // ViewModel 에 접근 및 로딩
        val viewModel = ViewModelProvider(this).get(ViewModelForQType10::class.java)

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

        val page1 = QType10ContentPage1()
        val page2 = QType10ContentPage2()
        val page3 = QType10ContentPage3()
        val page4 = QType10ContentPage4()
        val page5 = QType10ContentPage5()
        val page6 = QType10ContentPage6()
        val page7 = QType10ContentPage7()
        val page8 = QType10ContentPage8()
        val page9 = QType10ContentPage9()
        val page10 = QType10ContentPage10()
        val page11 = QType10ContentPage11()

        val pageSequence = arrayOf(
            page1, page2, page3, page4, page5, page6, page7, page8, page9, page10, page11
        )

        val frameLayoutID = R.id.pageFrame

        val responseSequence = viewModel.responseSequence


        viewModel.snackType.observe(this) { newSnackType ->
            // snackType 의 값이 변경될 때 호출되는 옵저버 블록

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
                10,
                responseSequence,
                null,
                null,
                null,
                newSnackType,
                viewModel.snackConsumedNumber.value
            )

        }

        viewModel.snackConsumedNumber.observe(this) { snackConsumedNumber ->
            // snackConsumedNumber 의 값이 변경될 때 호출되는 옵저버 블록

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
                10,
                responseSequence,
                null,
                null,
                null,
                viewModel.snackType.value,
                snackConsumedNumber
            )

        }

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
            10,
            responseSequence,
            null,
            null,
            null,
            viewModel.snackType.value,
            viewModel.snackConsumedNumber.value
        )

    }
}