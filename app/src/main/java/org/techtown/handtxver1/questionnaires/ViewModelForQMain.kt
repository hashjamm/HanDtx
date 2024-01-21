package org.techtown.handtxver1.questionnaires

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelForQMain : ViewModel() {

    // MutableLiveData를 사용하여 UI에 변경을 알릴 수 있는 변수
    val resultLiveData = MutableLiveData<GetAllSurveyCheckedOutput>()

    fun fetchData(userID: String, date: String) {
        viewModelScope.launch {
            try {
                // getData 함수를 호출하여 데이터를 가져옴
                val result = getData(userID, date)

                // LiveData에 결과를 전달하여 UI에 변경을 알림
                resultLiveData.postValue(result)
            } catch (e: Exception) {
                // 네트워크 문제 또는 기타 오류 처리
                e.printStackTrace()
            }
        }
    }

    // CommonUserDefinedObjectSet 클래스 인스턴스 생성
    val objectSet = QuestionnaireUserDefinedObjectSet()

    private val getAllSurveyCheckedInterface: GetAllSurveyCheckedInterface =
        objectSet.retrofit.create(GetAllSurveyCheckedInterface::class.java)

    private suspend fun getData(userID: String, date: String): GetAllSurveyCheckedOutput {

        return withContext(Dispatchers.IO) {

            var resultValue =
                GetAllSurveyCheckedOutput(
                    issue_checking = false,
                    self_diagnosis = false,
                    well_being_scale = false,
                    phq9 = false,
                    gad7 = false,
                    pss10 = false,
                    exercise = false,
                    smoking_drinking = false,
                    stress = false,
                    nutrition = false
                )

            try {
                val response = getAllSurveyCheckedInterface.requestGetAllSurveyChecked(userID, date)
                    .execute()

                if (response.isSuccessful) {

                    resultValue = response.body()!!

                }
            } catch (e: Exception) {

                // 네트워크 문제 또는 기타 오류 처리
                e.printStackTrace()

            }

//            getAllSurveyCheckedInterface.requestGetAllSurveyChecked(userID, date)
//                .enqueue(
//                    object :
//                        Callback<GetAllSurveyCheckedOutput> {
//
//                        override fun onResponse(
//                            call: Call<GetAllSurveyCheckedOutput>,
//                            response: Response<GetAllSurveyCheckedOutput>
//                        ) {
//                            if (response.isSuccessful) {
//
//                                resultValue = response.body()!!
//
//                            }
//
//                        }
//
//                        override fun onFailure(
//                            call: Call<GetAllSurveyCheckedOutput>,
//                            t: Throwable
//                        ) {
//
//                            val errorDialog = AlertDialog.Builder(this@ViewModelForQMain)
//                            errorDialog.setTitle("통신 오류")
//                            errorDialog.setMessage("통신에 실패했습니다 : ${t.message}")
//
//                            errorDialog.show()
//
//                        }
//
//
//                    }
//
//                )

            resultValue
        }

    }

}