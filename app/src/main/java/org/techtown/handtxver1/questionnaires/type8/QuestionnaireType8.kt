package org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type8

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import org.techtown.handtxver1.R
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.QuestionnaireUserDefinedObjectSet

class QuestionnaireType8 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_type8)

        // QuestionnaireUserDefinedObjectSet 클래스 인스턴스 생성
        val objectSet = QuestionnaireUserDefinedObjectSet()

        // ViewModel 에 접근 및 로딩
        val viewModel = ViewModelProvider(this).get(ViewModelForQType8::class.java)

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

        val page1 = QType8ContentPage1()
        val page2 = QType8ContentPage2()
        val page3 = QType8ContentPage3()
        val page4 = QType8ContentPage4()
        val page5 = QType8ContentPage5()
        val page6 = QType8ContentPage6()
        val page7 = QType8ContentPage7()
        val page8 = QType8ContentPage8()
        val page9 = QType8ContentPage9()

        val pageSequence = arrayOf(
            page1, page2, page3, page4, page5, page6, page7, page8, page9
        )

        val frameLayoutID = R.id.pageFrame

        val responseSequence = viewModel.responseSequence

        objectSet.questionnaireActivityFunction(
            this,
            frameLayoutID,
            pageSequence,
            pageBar,
            presentPageBar,
            pageNumberBox,
            toPreviousPage,
            toNextPage,
            submitButton,
            8,
            responseSequence,
            null,
            0,
            80
        )
    }
}




