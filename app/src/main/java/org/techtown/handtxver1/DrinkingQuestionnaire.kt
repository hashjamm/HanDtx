package org.techtown.handtxver1

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

class DrinkingQuestionnaire : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinking_questionnaire)

        // ViewModel 에 접근 및 로딩
        val viewModel = ViewModelProvider(this).get(ViewModelForDrinkingQuestionnaire::class.java)

        val presentPageBar =
            findViewById<androidx.appcompat.widget.AppCompatImageView>(R.id.presentPageBar)
        val pageBar = findViewById<ConstraintLayout>(R.id.pageBar)
        val toPreviousPage =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.previous_page)
        val toNextPage = findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.next_page)
        val pageNumberBox1 =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.pageNumberBox1)
        val pageNumberBox2 =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.pageNumberBox2)
        val submitButton =
            findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.submitButton)

        val previousPageDrawable = ContextCompat.getDrawable(this, R.drawable.previous_page)
        val nextPageDrawable = ContextCompat.getDrawable(this, R.drawable.next_page)
        val submitButtonDrawable =
            ContextCompat.getDrawable(this, R.drawable.submit_button)

        fun previousButtonDrawableOn() {
            toPreviousPage.background = previousPageDrawable
        }

        fun previousButtonDrawableOff() {
            toPreviousPage.setBackgroundColor(Color.parseColor("#00FF0000"))
        }

        fun nextButtonDrawableOn() {
            toNextPage.background = nextPageDrawable
        }

        fun nextButtonDrawableOff() {
            toNextPage.setBackgroundColor(Color.parseColor("#00FF0000"))
        }

        fun submitButtonOff() {
            submitButton.setBackgroundColor(Color.parseColor("#00FF0000"))
            submitButton.setText("")
            submitButton.setOnClickListener(null)
        }

        fun submitButtonOn() {
            submitButton.background = submitButtonDrawable
            submitButton.setText("제출 완료!")
            submitButton.setOnClickListener {
                // 설문 내용 서버에 전송하기 위한 코드 작성 예정
                // 일단은 답변내용을 알려주는 팝업창 코드 작성

                if (viewModel.responseSequence.any { it == null }) {

                    val nullIndices =
                        viewModel.responseSequence.indices.filter { viewModel.responseSequence[it] == null }
                    val missingQuestions =
                        nullIndices.map { (it + 1).toString() + "번" }.joinToString(", ", "", " 질문")

                    // 기획팀 요청 : 팝업 메세지 중앙 정렬을 위한 spannableStringBuilder 객체 사용 코드
                    val title = "작성되지 않은 문항이 있습니다."
                    val message = "모든 문항에 대하여 응답해주십시오. \n $missingQuestions"

                    val spannableStringBuilderTitle = SpannableStringBuilder(title)
                    spannableStringBuilderTitle.setSpan(
                        AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0,
                        title.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    val spannableStringBuilderMessage = SpannableStringBuilder(message)
                    spannableStringBuilderMessage.setSpan(
                        AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0,
                        message.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    val dialogOfResponses = AlertDialog.Builder(this)
                        .setTitle(spannableStringBuilderTitle)
                        .setMessage(spannableStringBuilderMessage)

                    dialogOfResponses.show()
                } else {
                    val dialogOfResponses = AlertDialog.Builder(this)
                        .setTitle("응답한 내용")
                        .setMessage(
                            "1번 : ${viewModel.responseSequence[0]}\n" +
                                    "2번 : ${viewModel.responseSequence[1]}\n" +
                                    "3번 : ${viewModel.responseSequence[2]}\n" +
                                    "4번 : ${viewModel.responseSequence[3]}\n" +
                                    "5번 : ${viewModel.responseSequence[4]}\n" +
                                    "6번 : ${viewModel.responseSequence[5]}\n" +
                                    "7번 : ${viewModel.responseSequence[6]}\n" +
                                    "8번 : ${viewModel.responseSequence[7]}\n" +
                                    "9번 : ${viewModel.responseSequence[8]}\n" +
                                    "10번 : ${viewModel.responseSequence[9]}"
                        )
                        .setPositiveButton("완료") { dialog, which ->
                            Toast.makeText(this, "설문을 완료하였습니다.", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, QuestionnaireMainPage::class.java)
                            intent.putExtra(
                                "scoreOfDrinkingQuestionnaire",
                                viewModel.responseSequence.filterNotNull().sum().toString()
                            )
                            startActivity(intent)
                        }
                        .setNeutralButton("수정", null)

                    dialogOfResponses.show()
                }

            }
        }

        fun pageBarLengthSetting(pageNum: Int, wholePageNum: Int) {
            pageBar.post {
                val pageBarWidth = pageBar.width

                val presentPageBarLayoutParams = LinearLayout.LayoutParams(
                    pageBarWidth * pageNum / wholePageNum,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )

                presentPageBar.layoutParams = presentPageBarLayoutParams

            }
        }

        fun pageNumberBoxSetting(pageNum: Int, wholePageNum: Int) {
            pageNumberBox1.setText("$pageNum of $wholePageNum")
            pageNumberBox2.setText("$pageNum of $wholePageNum")
        }

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

        supportFragmentManager.beginTransaction().add(R.id.pageFrame, page1).commitNow()
        pageBarLengthSetting(1, 10)
        pageNumberBoxSetting(1, 10)
        previousButtonDrawableOff()

        toNextPage.setOnClickListener {
            when (supportFragmentManager.findFragmentById(R.id.pageFrame)) {
                page1 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page2)
                        .commitNow()
                    pageBarLengthSetting(2, 10)
                    pageNumberBoxSetting(2, 10)
                    previousButtonDrawableOn()
                }
                page2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page3)
                        .commitNow()
                    pageBarLengthSetting(3, 10)
                    pageNumberBoxSetting(3, 10)
                }
                page3 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page4)
                        .commitNow()
                    pageBarLengthSetting(4, 10)
                    pageNumberBoxSetting(4, 10)
                }
                page4 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page5)
                        .commitNow()
                    pageBarLengthSetting(5, 10)
                    pageNumberBoxSetting(5, 10)
                }
                page5 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page6)
                        .commitNow()
                    pageBarLengthSetting(6, 10)
                    pageNumberBoxSetting(6, 10)
                }
                page6 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page7)
                        .commitNow()
                    pageBarLengthSetting(7, 10)
                    pageNumberBoxSetting(7, 10)
                }
                page7 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page8)
                        .commitNow()
                    pageBarLengthSetting(8, 10)
                    pageNumberBoxSetting(8, 10)
                }
                page8 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page9)
                        .commitNow()
                    pageBarLengthSetting(9, 10)
                    pageNumberBoxSetting(9, 10)
                }
                page9 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page10)
                        .commitNow()
                    pageBarLengthSetting(10, 10)
                    pageNumberBoxSetting(10, 10)
                    submitButtonOn()
                    nextButtonDrawableOff()
                }
            }

            toPreviousPage.setOnClickListener {
                when (supportFragmentManager.findFragmentById(R.id.pageFrame)) {
                    page2 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page1)
                            .commitNow()
                        pageBarLengthSetting(1, 10)
                        pageNumberBoxSetting(1, 10)
                        previousButtonDrawableOff()
                    }
                    page3 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page2)
                            .commitNow()
                        pageBarLengthSetting(2, 10)
                        pageNumberBoxSetting(2, 10)
                    }
                    page4 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page3)
                            .commitNow()
                        pageBarLengthSetting(3, 10)
                        pageNumberBoxSetting(3, 10)
                    }
                    page5 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page4)
                            .commitNow()
                        pageBarLengthSetting(4, 10)
                        pageNumberBoxSetting(4, 10)
                    }
                    page6 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page5)
                            .commitNow()
                        pageBarLengthSetting(5, 10)
                        pageNumberBoxSetting(5, 10)
                    }
                    page7 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page6)
                            .commitNow()
                        pageBarLengthSetting(6, 10)
                        pageNumberBoxSetting(6, 10)
                    }
                    page8 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page7)
                            .commitNow()
                        pageBarLengthSetting(7, 10)
                        pageNumberBoxSetting(7, 10)
                    }
                    page9 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page8)
                            .commitNow()
                        pageBarLengthSetting(8, 10)
                        pageNumberBoxSetting(8, 10)
                    }
                    page10 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.pageFrame, page9)
                            .commitNow()
                        pageBarLengthSetting(9, 10)
                        pageNumberBoxSetting(9, 10)
                        submitButtonOff()
                        nextButtonDrawableOn()
                    }
                }
            }
        }
    }
}

