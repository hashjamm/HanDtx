package org.techtown.handtxver1.org.techtown.handtxver1

import android.annotation.SuppressLint
import android.content.SharedPreferences
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.techtown.handtxver1.R
import org.techtown.handtxver1.SharedDateViewModel
import java.text.SimpleDateFormat
import java.util.*

class CommonUserDefinedObjectSet {

    // 유저의 id와 pw를 Login 클래스에서 로그인 버튼을 눌렀을 때 가져오기 위하여 우선 null 로 초기화
    var userID: String? = null
    var userPW: String? = null

    // 각 DB 파일로 사용된 sharedPreferences 에서 userID 밑으로 주요 분류 값으로 사용될 오늘 날짜에 대한 값을 미리 작성
    val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
    val dateToday = dateFormat.format(Calendar.getInstance().time)

    // munuNum 에 따라 아래와 같이 정의
    // 1 : "기분" 텝
    // 2 : "불안" 텝
    // 3 : "식이" 텝
    // 이외 : null

    // json String 으로 encoding 하기 위해 데이터를 묶기 위한 데이터 클래스 생성
    // 감정다이어리 부분
    @Serializable
    data class MixedData(
        // 기분 관련 점수 및 입력 내용
        @SerialName("score1") val score1: Int? = null,
        @SerialName("inputText1") val inputText1: String? = null,
        // 불안 관련 점수 및 입력 내용
        @SerialName("score2") val score2: Int? = null,
        @SerialName("inputText2") val inputText2: String? = null,
        // 식이 관련 점수 및 입력 내용
        @SerialName("score3") val score3: Int? = null,
        @SerialName("inputText3") val inputText3: String? = null
    )

    // json String 으로 encoding 하기 위해 데이터를 묶기 위한 데이터 클래스 생성
    // 설문지 부분
    @Serializable
    data class OneSurveyResult(
        @SerialName("results") val results: MutableList<Int>? = null,
        @SerialName("comment") val comment: String? = null // 설문 타입 1을 제외하고는 모두 null
    )

    @Serializable
    data class OneDateSurveyData(
        @SerialName("surveyData") val surveyData: Map<Int, OneSurveyResult>? = null
    )

    @Serializable
    data class OneUserSurveyData(
        @SerialName("dates") val dates: Map<String, OneDateSurveyData>? = null
    )


    // 기분, 불안, 비만 탭에서 그래프 최적화를 위해 점수마다 부여해줄 그래프 백그라운드 배열 생성
    val graphBackgroundArray = arrayOf(
        R.drawable.emotion_graph_image_0,
        R.drawable.emotion_graph_image_1,
        R.drawable.emotion_graph_image_2,
        R.drawable.emotion_graph_image_3,
        R.drawable.emotion_graph_image_4,
        R.drawable.emotion_graph_image_5,
        R.drawable.emotion_graph_image_6,
        R.drawable.emotion_graph_image_7,
        R.drawable.emotion_graph_image_8,
        R.drawable.emotion_graph_image_9
    )

    // 기분, 불안, 비만 탭에서 그래프 최적화를 위해 점수마다 부여해줄 그래프 말풍선 내용 배열 생성
    val graphTextArray1 = arrayOf(
        "0점 내용",
        "1점 내용",
        "2점 내용",
        "3점 내용",
        "4점 내용",
        "5점 내용",
        "6점 내용",
        "7점 내용",
        "8점 내용",
        "9점 내용"
    )

    val graphTextArray2 = arrayOf(
        "0점 내용",
        "1점 내용",
        "2점 내용",
        "3점 내용",
        "4점 내용",
        "5점 내용",
        "6점 내용",
        "7점 내용",
        "8점 내용",
        "9점 내용"
    )

    val graphTextArray3 = arrayOf(
        "0점 내용",
        "1점 내용",
        "2점 내용",
        "3점 내용",
        "4점 내용",
        "5점 내용",
        "6점 내용",
        "7점 내용",
        "8점 내용",
        "9점 내용"
    )

    // MixedData 의 default 값에 대한 encoded json String
    val defaultJsonMixedData = Json.encodeToString(MixedData())

    // SharedPreferences 를 생성해주면 해당 파일에서 score 를 알맞게 가져오는 함수
    fun getScore(
        sharedPreferences: SharedPreferences,
        menuNum: Int,
        viewModel: SharedDateViewModel? = null, // viewModel 접근을 통해 key 를 만들어야 할지 말아야 할지
        givenKey: String? = null // key 값이 이미 주어진 경우와 지정을 해주어야하는 경우 다르게 함수를 진행
    ): Int? {

        val key =
            viewModel?.dateString?.value?.toString() // key 값을 지정해줘야하는 경우 -> viewModel 입력 필요, givenKey 입력 x
                ?: givenKey // key 값이 이미 주어진 경우 -> viewModel 입력 x, givenKey 입력 필요

        val obtainedMixedData = sharedPreferences.getString(key, defaultJsonMixedData)

        return if (obtainedMixedData != null && obtainedMixedData != "{}") {

            val deserializedMixedData =
                Json.decodeFromString<MixedData>(obtainedMixedData)

            when (menuNum) {
                1 -> {
                    deserializedMixedData.score1
                }
                2 -> {
                    deserializedMixedData.score2
                }
                3 -> {
                    deserializedMixedData.score3
                }
                else -> null
            }

        } else {

            null // 어차피 obtainedMixedData 는 null 일 수가 없다.
        }

    }

    // SharedPreferences 를 생성해주면 해당 파일에서 inputText 를 알맞게 가져오는 함수
    fun getInputText(
        sharedPreferences: SharedPreferences,
        menuNum: Int,
        viewModel: SharedDateViewModel? = null, // viewModel 접근을 통해 key 를 만들어야 할지 말아야 할지
        givenKey: String? = null // key 값이 이미 주어진 경우와 지정을 해주어야하는 경우 다르게 함수를 진행
    ): String? {

        val key =
            viewModel?.dateString?.value?.toString() // key 값을 지정해줘야하는 경우
                ?: givenKey // key 값이 이미 주어진 경우

        val obtainedMixedData = sharedPreferences.getString(key, defaultJsonMixedData)

        return if (obtainedMixedData != null && obtainedMixedData != "{}") {

            val deserializedMixedData =
                Json.decodeFromString<MixedData>(obtainedMixedData)

            when (menuNum) {
                1 -> {
                    deserializedMixedData.inputText1
                }
                2 -> {
                    deserializedMixedData.inputText2
                }
                3 -> {
                    deserializedMixedData.inputText3
                }
                else -> null
            }

        } else {

            null // 어차피 obtainedMixedData 는 null 일 수가 없다.
        }

    }

    // SharedPreferences 를 생성해주면 해당 파일에 score 를 알맞게 json String 으로 업로드하는 함수. 기존 값에 업데이트
    fun updateScore(
        score: Int,
        sharedPreferences: SharedPreferences,
        menuNum: Int,
        viewModel: SharedDateViewModel? = null, // viewModel 접근을 통해 key 를 만들어야 할지 말아야 할지
        givenKey: String? = null // key 값이 이미 주어진 경우와 지정을 해주어야하는 경우 다르게 함수를 진행
    ) {

        val key =
            viewModel?.dateString?.value?.toString() // key 값을 지정해줘야하는 경우
                ?: givenKey // key 값이 이미 주어진 경우

        val editor = sharedPreferences.edit()

        val obtainedMixedData = sharedPreferences.getString(key, defaultJsonMixedData)

        val updateData =
            if (obtainedMixedData != null && obtainedMixedData != "{}") {
                val deserializedMixedData =
                    Json.decodeFromString<MixedData>(obtainedMixedData)
                when (menuNum) {
                    1 -> deserializedMixedData.copy(score1 = score)
                    2 -> deserializedMixedData.copy(score2 = score)
                    3 -> deserializedMixedData.copy(score3 = score)
                    else -> deserializedMixedData
                }
            } else {
                when (menuNum) {
                    1 -> MixedData(score1 = score)
                    2 -> MixedData(score2 = score)
                    3 -> MixedData(score3 = score)
                    else -> null
                }
            }

        val jsonUpdateData = Json.encodeToString(updateData)
        editor.putString(key, jsonUpdateData)
        editor.apply()
    }

    // SharedPreferences 를 생성해주면 해당 파일에 inputText 를 알맞게 json String 으로 업로드하는 함수. 기존 값에 업데이트
    fun updateInputText(
        inputText: String,
        sharedPreferences: SharedPreferences,
        menuNum: Int,
        viewModel: SharedDateViewModel? = null, // viewModel 접근을 통해 key 를 만들어야 할지 말아야 할지
        givenKey: String? = null // key 값이 이미 주어진 경우와 지정을 해주어야하는 경우 다르게 함수를 진행
    ) {

        val key =
            viewModel?.dateString?.value?.toString() // key 값을 지정해줘야하는 경우
                ?: givenKey // key 값이 이미 주어진 경우

        val editor = sharedPreferences.edit()

        val obtainedMixedData = sharedPreferences.getString(key, defaultJsonMixedData)

        val updateData =
            if (obtainedMixedData != null && obtainedMixedData != "{}") {
                val deserializedMixedData =
                    Json.decodeFromString<MixedData>(obtainedMixedData)
                when (menuNum) {
                    1 -> deserializedMixedData.copy(inputText1 = inputText)
                    2 -> deserializedMixedData.copy(inputText2 = inputText)
                    3 -> deserializedMixedData.copy(inputText3 = inputText)
                    else -> deserializedMixedData
                }
            } else {
                when (menuNum) {
                    1 -> MixedData(inputText1 = inputText)
                    2 -> MixedData(inputText2 = inputText)
                    3 -> MixedData(inputText3 = inputText)
                    else -> null
                }
            }

        val jsonUpdateData = Json.encodeToString(updateData)
        editor.putString(key, jsonUpdateData)
        editor.apply()
    }

    // QuestionnaireSharedPreferences 에 설문 데이터 결과를 Json String 으로 업데이트하는 함수

    fun updateSurveyData(
        results: MutableList<Int>,
        comment: String? = null,
        surveyNumber: Int,
        date: String,
        sharedPreferences: SharedPreferences // sharedPreferences 를 context 없이 함수 내부에서 쓰면 오류 발생 여지가 있어서 파라미터로 적는 것으로..
    ) {

        // editor 생성
        val editor = sharedPreferences.edit()

        // QuestionnaireSharedPreferences 파일 내부에서 유저의 기존 데이터를 가져옴
        val getOneUserSurveyData = sharedPreferences.getString(userID, "{}")

        if (getOneUserSurveyData != null && getOneUserSurveyData != "{}") {

            val obtainedOneUserSurveyData =
                Json.decodeFromString<OneUserSurveyData>(getOneUserSurveyData)

            val obtainedOneDateSurveyData = obtainedOneUserSurveyData.dates?.get(date)

            if (obtainedOneDateSurveyData != null) {

                val obtainedOneSurveyResult =
                    obtainedOneDateSurveyData.surveyData?.get(surveyNumber)

                val updateOneSurveyResult =
                    obtainedOneSurveyResult?.copy(results = results, comment = comment)
                        ?: OneSurveyResult(results, comment)

                // 해당 번호의 설문 데이터가 있을 때를 가정하고 있기 때문에 null safe 가 아닌 non null 처리함.
                // 명시해주길 코틀린이 바라고 있었음.

                val updateOneDateSurveyData =
                    obtainedOneDateSurveyData.surveyData!!.toMutableMap()

                updateOneDateSurveyData[surveyNumber] = updateOneSurveyResult

                val classModifiedUpdateOneDateSurveyData =
                    OneDateSurveyData(updateOneDateSurveyData)

                val updateOneUserSurveyData = obtainedOneUserSurveyData.dates.toMutableMap()
                updateOneUserSurveyData[date] = classModifiedUpdateOneDateSurveyData
                val classModifiedUpdateOneUserSurveyData =
                    OneUserSurveyData(updateOneUserSurveyData)

                val jsonUpdateData = Json.encodeToString(classModifiedUpdateOneUserSurveyData)

                editor.putString(userID, jsonUpdateData)
                editor.apply()

            } else {

                val updateOneSurveyData = OneSurveyResult(results, comment)
                val updateOneDateSurveyData =
                    OneDateSurveyData(mapOf(surveyNumber to updateOneSurveyData))

                // 해당 유저의 설문 데이터가 있을 때를 가정하고 있기 때문에 null safe 가 아닌 non null 처리함.
                // 명시해주길 코틀린이 바라고 있었음.

                val updateOneUserSurveyData = obtainedOneUserSurveyData.dates!!.toMutableMap()
                updateOneUserSurveyData[date] = updateOneDateSurveyData
                val classModifiedUpdateOneUserSurveyData =
                    OneUserSurveyData(updateOneUserSurveyData)

                val jsonUpdateData = Json.encodeToString(classModifiedUpdateOneUserSurveyData)

                editor.putString(userID, jsonUpdateData)
                editor.apply()

            }


        } else {

            val updateOneSurveyData = OneSurveyResult(results, comment)
            val updateOneDateSurveyData =
                OneDateSurveyData(mapOf(surveyNumber to updateOneSurveyData))
            val updateOneUserSurveyData = OneUserSurveyData(mapOf(date to updateOneDateSurveyData))

            val jsonUpdateData = Json.encodeToString(updateOneUserSurveyData)

            editor.putString(userID, jsonUpdateData)
            editor.apply()

        }


    }

    // QuestionnaireSharedPreferences 에서 특정 설문지의 설문 데이터 결과를 가져오는 함수

    fun getOneSurveyResults(
        surveyNumber: Int,
        date: String,
        sharedPreferences: SharedPreferences // sharedPreferences 를 context 없이 함수 내부에서 쓰면 오류 발생 여지가 있어서 파라미터로 적는 것으로..
    ): OneSurveyResult? {

        // QuestionnaireSharedPreferences 파일 내부에서 유저의 기존 데이터를 가져옴
        val getOneUserSurveyData = sharedPreferences.getString(userID, "{}")

        if (getOneUserSurveyData != null && getOneUserSurveyData != "{}") {

            val obtainedOneUserSurveyData =
                Json.decodeFromString<OneUserSurveyData>(getOneUserSurveyData)

            val obtainedOneDateSurveyData = obtainedOneUserSurveyData.dates?.get(date)

            return if (obtainedOneDateSurveyData != null) {

                obtainedOneDateSurveyData.surveyData?.get(surveyNumber)

            } else {

                null

            }


        } else {

            return null

        }

    }

    // QuestionnaireSharedPreferences 에서 특정 설문지의 설문 데이터 결과를 가져오는 함수

    fun getOneDateSurveyData(
        date: String,
        sharedPreferences: SharedPreferences // sharedPreferences 를 context 없이 함수 내부에서 쓰면 오류 발생 여지가 있어서 파라미터로 적는 것으로..
    ): OneDateSurveyData? {

        // QuestionnaireSharedPreferences 파일 내부에서 유저의 기존 데이터를 가져옴
        val getOneUserSurveyData = sharedPreferences.getString(userID, "{}")

        return if (getOneUserSurveyData != null && getOneUserSurveyData != "{}") {

            val obtainedOneUserSurveyData =
                Json.decodeFromString<OneUserSurveyData>(getOneUserSurveyData)

            val obtainedOneDateSurveyData = obtainedOneUserSurveyData.dates?.get(date)

            obtainedOneDateSurveyData

        } else {

            null

        }

    }


}