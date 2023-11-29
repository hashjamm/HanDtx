package org.techtown.handtxver1.org.techtown.handtxver1.questioXnnaires.type2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import org.techtown.handtxver1.R
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.QuestionnaireUserDefinedObjectSet
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type2.*
import org.techtown.handtxver1.questionnaires.type2.ViewModelForQType2
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type2.QType2ContentPage7 as QType2ContentPage7

class QuestionnaireType2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_type2)

        // QuestionnaireUserDefinedObjectSet 클래스 인스턴스 생성
        val objectSet = QuestionnaireUserDefinedObjectSet()

        // ViewModel 에 접근 및 로딩
        val viewModel = ViewModelProvider(this).get(ViewModelForQType2::class.java)

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

        val page1 = QType2ContentPage1()
        val page2 = QType2ContentPage2()
        val page3 = QType2ContentPage3()
        val page4 = QType2ContentPage4()
        val page5 = QType2ContentPage5()
        val page6 = QType2ContentPage6()
        val page7 = QType2ContentPage7()
        val page8 = QType2ContentPage8()
        val page9 = QType2ContentPage9()
        val page10 = QType2ContentPage10()

        val pageSequence = arrayOf(
            page1, page2, page3, page4, page5, page6, page7, page8, page9, page10
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
            2,
            responseSequence
        )
    }
}

