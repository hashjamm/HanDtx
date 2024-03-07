package org.techtown.handtxver1.emotionDiary

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    // retrofit 객체 생성
    private var retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/") // 연결하고자 하는 서버 주소 입력
        .addConverterFactory(GsonConverterFactory.create()) // gson 을 통한 javaScript 로의 코드 자동 전환 - Gson 장착
        .build() // 코드 마무리

    // 감정다이어리 서비스 interface 를 장착한 Retrofit 객체 생성
    private var getEmotionDiaryRecordsInterface: GetEmotionDiaryRecordsInterface =
        retrofit.create(GetEmotionDiaryRecordsInterface::class.java)

    suspend fun fetchEmotionDiaryData(userID: String, date: String): GetEmotionDiaryRecordsOutput? {

        return withContext(Dispatchers.IO) {
            try {
                val response =
                    getEmotionDiaryRecordsInterface.requestGetEmotionDiaryRecords(userID, date)
                        .awaitResponse()

                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                throw RuntimeException("Unexpected Error at fetchEmotionDiaryData methods.")
            }
        }
    }
}





