package org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type4

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import org.techtown.handtxver1.R
import org.techtown.handtxver1.org.techtown.handtxver1.CommonUserDefinedObjectSet
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.QuestionnaireMainPage
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type3.ViewModelForQType3

class QuestionnaireType4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_type4)

        // CommonUserDefinedObjectSet 클래스 인스턴스 생성
        val commonUserDefinedObjectSet = CommonUserDefinedObjectSet()

        // ViewModel 에 접근 및 로딩
        val viewModel = ViewModelProvider(this).get(ViewModelForQType4::class.java)

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

        val page1 = QType4ContentPage1()
        val page2 = QType4ContentPage2()
        val page3 = QType4ContentPage3()
        val page4 = QType4ContentPage4()
        val page5 = QType4ContentPage5()
        val page6 = QType4ContentPage6()
        val page7 = QType4ContentPage7()
        val page8 = QType4ContentPage8()
        val page9 = QType4ContentPage9()

        val pageSequence = arrayOf(
            page1, page2, page3, page4, page5, page6, page7, page8, page9
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
            4,
            responseSequence
        )

    }
}

