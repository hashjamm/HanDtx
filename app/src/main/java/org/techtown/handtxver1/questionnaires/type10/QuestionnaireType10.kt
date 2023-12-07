package org.techtown.handtxver1.questionnaires.type10

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AlignmentSpan
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import org.techtown.handtxver1.R
import org.techtown.handtxver1.org.techtown.handtxver1.questionnaires.type10.ViewModelForQType10
import org.techtown.handtxver1.questionnaires.QuestionnaireMainPage
import org.techtown.handtxver1.questionnaires.QuestionnaireUserDefinedObjectSet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class QuestionnaireType10 : AppCompatActivity() {

    // QuestionnaireUserDefinedObjectSet 클래스 인스턴스 생성
    val objectSet = QuestionnaireUserDefinedObjectSet()

    // ViewModel 에 접근 및 로딩
    val viewModel = ViewModelProvider(this)[ViewModelForQType10::class.java]

    val presentPageBar: AppCompatImageView =
        findViewById(R.id.presentPageBar)
    val pageBar: ConstraintLayout = findViewById(R.id.pageBar)
    val toPreviousPage: AppCompatTextView =
        findViewById(R.id.previous_page)
    val toNextPage: AppCompatTextView = findViewById(R.id.next_page)
    val pageNumberBox: AppCompatTextView =
        findViewById(R.id.pageNumberBox1)
    val submitButton: AppCompatTextView =
        findViewById(R.id.submitButton)

    private val page1 = QType10ContentPage1()
    private val page2 = QType10ContentPage2()
    private val page3 = QType10ContentPage3()
    private val page4 = QType10ContentPage4()
    private val page5 = QType10ContentPage5()
    private val page6 = QType10ContentPage6()
    private val page7 = QType10ContentPage7()
    private val page8 = QType10ContentPage8()
    private val page9 = QType10ContentPage9()
    private val page10 = QType10ContentPage10()
    private val page11 = QType10ContentPage11()

    val pageSequence = arrayOf(
        page1, page2, page3, page4, page5, page6, page7, page8, page9, page10, page11
    )

    val frameLayoutID = R.id.pageFrame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire_type10)

        supportFragmentManager.beginTransaction().add(frameLayoutID, pageSequence[0]).commitNow()

        objectSet.pageBarLengthSetting(pageBar, presentPageBar, 1, pageSequence.size)
        objectSet.pageNumberBoxSetting(pageNumberBox, 1, pageSequence.size)
        objectSet.buttonDrawableOff(toPreviousPage)

        toNextPage.setOnClickListener {

            val currentPage = supportFragmentManager.findFragmentById(frameLayoutID)
            val currentPageIndex = pageSequence.indexOf(currentPage)
            val currentPageNumber = currentPageIndex + 1

            if (currentPageNumber in 1 until pageSequence.size) {

                val nextPage = pageSequence[currentPageIndex + 1]

                supportFragmentManager.beginTransaction()
                    .replace(frameLayoutID, nextPage)
                    .commitNow()

                objectSet.pageBarLengthSetting(
                    pageBar,
                    presentPageBar,
                    currentPageNumber + 1,
                    pageSequence.size
                )

                objectSet.pageNumberBoxSetting(
                    pageNumberBox,
                    currentPageNumber + 1,
                    pageSequence.size
                )

                if (currentPageIndex == 0) {
                    objectSet.buttonDrawableOn(this, toPreviousPage, -1)
                }

                if (currentPageNumber + 1 == pageSequence.size) {

                    submitButton.background =
                        ContextCompat.getDrawable(this, R.drawable.submit_button)
                    submitButton.text = "제출 완료"

                    submitButton.setOnClickListener {

                        if (viewModel.responseSequence.any { it == null }) {

                            val nullIndices =
                                viewModel.responseSequence.indices.filter { viewModel.responseSequence[it] == null }

                            val missingQuestions =
                                nullIndices.joinToString(
                                    ", ",
                                    "",
                                    " 질문"
                                ) { (it + 1).toString() + "번" }

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

                            val messageBuilder = StringBuilder()

                            for (i in viewModel.responseSequence.indices) {
                                val response = viewModel.responseSequence[i]
                                messageBuilder.append("${i + 1}번 : $response\n")
                            }

                            val dialogOfResponses = AlertDialog.Builder(this)
                                .setTitle("응답한 내용")
                                .setMessage(messageBuilder.toString())
                                .setPositiveButton("완료") { _, _ ->
                                    Toast.makeText(this, "설문을 완료하였습니다.", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this, QuestionnaireMainPage::class.java)

                                    updateData(objectSet.userID!!, objectSet.date)

                                    startActivity(intent)

                                }
                                .setNeutralButton("수정", null)

                            dialogOfResponses.show()

                        }
                    }

                    objectSet.buttonDrawableOff(toNextPage)

                }

            }
        }

        toPreviousPage.setOnClickListener {

            val currentPage = supportFragmentManager.findFragmentById(frameLayoutID)
            val currentPageIndex = pageSequence.indexOf(currentPage)
            val currentPageNumber = currentPageIndex + 1

            if (currentPageNumber in 2 until pageSequence.size + 1) {

                val previousPage = pageSequence[currentPageIndex - 1]

                supportFragmentManager.beginTransaction()
                    .replace(frameLayoutID, previousPage).commitNow()

                objectSet.pageBarLengthSetting(
                    pageBar,
                    presentPageBar,
                    currentPageNumber - 1,
                    pageSequence.size
                )

                objectSet.pageNumberBoxSetting(
                    pageNumberBox,
                    currentPageNumber - 1,
                    pageSequence.size
                )

                if (currentPageIndex == 1) {
                    objectSet.buttonDrawableOff(toPreviousPage)
                }

                if (currentPageNumber == pageSequence.size) {
                    objectSet.submitButtonOff(submitButton)
                    objectSet.buttonDrawableOn(this, toNextPage, 1)
                }

            }

        }
    }

    private var updateNutritionSurveyInterface: UpdateNutritionSurveyInterface =
        objectSet.retrofit.create(UpdateNutritionSurveyInterface::class.java)

    private fun updateData(
        userID: String,
        date: Date
    ) {

        val responseSequence = viewModel.responseSequence
        val snackTypeArray = viewModel.snackTypeArray
        val consumeNumArray = viewModel.consumeNumArray

        updateNutritionSurveyInterface.requestUpdateNutritionSurvey(
            userID,
            date,
            responseSequence[0]!!,
            responseSequence[1]!!,
            responseSequence[2]!!,
            responseSequence[3]!!,
            responseSequence[4]!!,
            responseSequence[5]!!,
            responseSequence[6]!!,
            responseSequence[7]!!,
            responseSequence[8]!!,
            responseSequence[9]!!,
            snackTypeArray[0],
            consumeNumArray[0]
        ).enqueue(object :
            Callback<UpdateNutritionSurveyOutput> {

            override fun onResponse(
                call: Call<UpdateNutritionSurveyOutput>,
                response: Response<UpdateNutritionSurveyOutput>
            ) {

            }

            override fun onFailure(call: Call<UpdateNutritionSurveyOutput>, t: Throwable) {
                val errorDialog = android.app.AlertDialog.Builder(this@QuestionnaireType10)
                errorDialog.setTitle("통신 오류")
                errorDialog.setMessage("통신에 실패했습니다 : type2")
                errorDialog.show()
            }

        })

    }

}