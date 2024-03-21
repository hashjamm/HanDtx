package org.techtown.handtxver1.emotionDiary

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.*
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

    private var updateEmotionDiaryRecordsInterface: UpdateEmotionDiaryRecordsInterface =
        retrofit.create(UpdateEmotionDiaryRecordsInterface::class.java)

    suspend fun fetchEmotionDiaryData(userID: String, date: String): GetEmotionDiaryRecordsOutput? {

        return withContext(Dispatchers.IO) {
            try {
                val response =
                    getEmotionDiaryRecordsInterface.requestGetEmotionDiaryRecords(userID, date)
                        .awaitResponse()

                if (response.isSuccessful) {
                    Log.d("cheking", "${response.body()}")

                    response.body()

                } else {
                    null
                }
            } catch (e: Exception) {
                throw RuntimeException("Unexpected Error at fetchEmotionDiaryData methods: ${e.message}")
            }
        }
    }

    fun updateData(updateValue: UpdateEmotionDiaryRecordsInput) {

        updateEmotionDiaryRecordsInterface.requestUpdateEmotionDiaryRecords(
            updateValue.userID,
            updateValue.date,
            updateValue.score,
            updateValue.inputText,
            updateValue.type
        )
            .enqueue(object :
                Callback<UpdateEmotionDiaryRecordsOutput> {

                override fun onResponse(
                    call: Call<UpdateEmotionDiaryRecordsOutput>,
                    response: Response<UpdateEmotionDiaryRecordsOutput>
                ) {

                    if (!response.isSuccessful) {

                        throw RuntimeException("Unexpected Error at updateData methods: onResponse - ${response.code()}")

                    }

                }

                override fun onFailure(
                    call: Call<UpdateEmotionDiaryRecordsOutput>,
                    t: Throwable
                ) {

                    throw RuntimeException("Unexpected Error at updateData methods: onFailure")

                }

            })

    }

}





