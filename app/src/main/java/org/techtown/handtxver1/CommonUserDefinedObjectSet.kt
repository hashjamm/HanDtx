package org.techtown.handtxver1.org.techtown.handtxver1

import android.content.SharedPreferences
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.techtown.handtxver1.R
import org.techtown.handtxver1.SharedDateViewModel

class CommonUserDefinedObjectSet {

    // 유저의 id와 pw를 Login 클래스에서 로그인 버튼을 눌렀을 때 가져오기 위하여 우선 null 로 초기화
    var userID: String? = null
    var userPW: String? = null

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
    data class SurveyData(
        @SerialName("typeNum") val typeNum: Int? = null,
        @SerialName("results") val results: List<Int?>? = null,
        @SerialName("totalScore") val totalScore: Int? = null
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

}