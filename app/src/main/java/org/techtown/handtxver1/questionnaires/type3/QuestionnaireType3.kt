package org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import org.techtown.handtxver1.R
import org.techtown.handtxver1.ViewModelForQType2
import org.techtown.handtxver1.org.techtown.handtxver1.CommonUserDefinedObjectSet

class QuestionnaireType3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_type3)

        // CommonUserDefinedObjectSet 클래스 인스턴스 생성
        val commonUserDefinedObjectSet = CommonUserDefinedObjectSet()

        // ViewModel 에 접근 및 로딩
        val viewModel = ViewModelProvider(this).get(ViewModelForQType3::class.java)

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

        val page1 = QType3ContentPage1()
        val page2 = QType3ContentPage2()
        val page3 = QType3ContentPage3()
        val page4 = QType3ContentPage4()
        val page5 = QType3ContentPage5()
        val page6 = QType3ContentPage6()
        val page7 = QType3ContentPage7()

        val pageSequence = arrayOf(
            page1, page2, page3, page4, page5, page6, page7
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
            3,
            responseSequence
        )

    }
}

