package org.techtown.handtxver1.questionnaires

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.techtown.handtxver1.questionnaires.type1.GetIssueCheckingSurveyInterface
import org.techtown.handtxver1.questionnaires.type1.GetIssueCheckingSurveyOutput
import org.techtown.handtxver1.questionnaires.type10.GetNutritionSurveyInterface
import org.techtown.handtxver1.questionnaires.type10.GetNutritionSurveyOutput
import org.techtown.handtxver1.questionnaires.type2.GetSelfDiagnosisSurveyInterface
import org.techtown.handtxver1.questionnaires.type2.GetSelfDiagnosisSurveyOutput
import org.techtown.handtxver1.questionnaires.type3.GetWellBeingScaleSurveyInterface
import org.techtown.handtxver1.questionnaires.type3.GetWellBeingScaleSurveyOutput
import org.techtown.handtxver1.questionnaires.type4.GetPHQ9SurveyInterface
import org.techtown.handtxver1.questionnaires.type4.GetPHQ9SurveyOutput
import org.techtown.handtxver1.questionnaires.type5.GetGAD7SurveyInterface
import org.techtown.handtxver1.questionnaires.type5.GetGAD7SurveyOutput
import org.techtown.handtxver1.questionnaires.type6.GetPSS10SurveyInterface
import org.techtown.handtxver1.questionnaires.type6.GetPSS10SurveyOutput
import org.techtown.handtxver1.questionnaires.type7.GetExerciseSurveyInterface
import org.techtown.handtxver1.questionnaires.type7.GetExerciseSurveyOutput
import org.techtown.handtxver1.questionnaires.type9.GetStressSurveyInterface
import org.techtown.handtxver1.questionnaires.type9.GetStressSurveyOutput
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelForQMain : ViewModel() {

    var issueCheckingSurveyData: GetIssueCheckingSurveyOutput? = null
    var selfDiagnosisSurveyData: GetSelfDiagnosisSurveyOutput? = null
    var wellBeingScaleSurveyData: GetWellBeingScaleSurveyOutput? = null
    var phq9SurveyData: GetPHQ9SurveyOutput? = null
    var gad7SurveyData: GetGAD7SurveyOutput? = null
    var pss10SurveyData: GetPSS10SurveyOutput? = null
    var exerciseSurveyData: GetExerciseSurveyOutput? = null
    var smokingDrinkingSurveyData: GetSmokingDrinkingSurveyOutput? = null
    var stressSurveyData: GetStressSurveyOutput? = null
    var nutritionSurveyData: GetNutritionSurveyOutput? = null

    fun fetchData() {

        viewModelScope.launch(Dispatchers.IO) {

            getIssueCheckingSurveyData()
            getSelfDiagnosisSurveyData()
            getWellBeingScaleSurveyData()
            getPHQ9SurveyData()
            getGAD7SurveyData()
            getPSS10SurveyData()
            getExerciseSurveyData()
            getSmokingDrinkingSurveyData()
            getStressSurveyData()
            getNutritionSurveyData()

        }

    }

    // CommonUserDefinedObjectSet 클래스 인스턴스 생성
    val objectSet = QuestionnaireUserDefinedObjectSet()

    private var getIssueCheckingSurveyInterface: GetIssueCheckingSurveyInterface =
        objectSet.retrofit.create(GetIssueCheckingSurveyInterface::class.java)

    private var getSelfDiagnosisSurveyInterface: GetSelfDiagnosisSurveyInterface =
        objectSet.retrofit.create(GetSelfDiagnosisSurveyInterface::class.java)

    private var getWellBeingScaleSurveyInterface: GetWellBeingScaleSurveyInterface =
        objectSet.retrofit.create(GetWellBeingScaleSurveyInterface::class.java)

    private var getPHQ9SurveyInterface: GetPHQ9SurveyInterface =
        objectSet.retrofit.create(GetPHQ9SurveyInterface::class.java)

    private var getGAD7SurveyInterface: GetGAD7SurveyInterface =
        objectSet.retrofit.create(GetGAD7SurveyInterface::class.java)

    private var getPSS10SurveyInterface: GetPSS10SurveyInterface =
        objectSet.retrofit.create(GetPSS10SurveyInterface::class.java)

    private var getExerciseSurveyInterface: GetExerciseSurveyInterface =
        objectSet.retrofit.create(GetExerciseSurveyInterface::class.java)

    private var getSmokingDrinkingSurveyInterface: GetSmokingDrinkingSurveyInterface =
        objectSet.retrofit.create(GetSmokingDrinkingSurveyInterface::class.java)

    private var getStressSurveyInterface: GetStressSurveyInterface =
        objectSet.retrofit.create(GetStressSurveyInterface::class.java)

    private var getNutritionSurveyInterface: GetNutritionSurveyInterface =
        objectSet.retrofit.create(GetNutritionSurveyInterface::class.java)

    private suspend fun getIssueCheckingSurveyData() {

        return try {
            withContext(Dispatchers.IO) {
                getIssueCheckingSurveyInterface.requestGetIssueCheckingSurvey(
                    objectSet.userID!!, objectSet.date
                ).enqueue(object : Callback<GetIssueCheckingSurveyOutput> {
                    override fun onResponse(
                        call: Call<GetIssueCheckingSurveyOutput>,
                        response: Response<GetIssueCheckingSurveyOutput>
                    ) {
                        issueCheckingSurveyData = response.body()
                    }

                    override fun onFailure(
                        call: Call<GetIssueCheckingSurveyOutput>,
                        t: Throwable
                    ) {

                        throw RuntimeException("서버 응답 실패")

                    }

                })

            }
        } catch (e: Exception) {
            throw RuntimeException("통신 중 오류 발생", e)
        }

    }

    private suspend fun getSelfDiagnosisSurveyData() {

        return try {
            withContext(Dispatchers.IO) {
                getSelfDiagnosisSurveyInterface.requestGetSelfDiagnosisSurvey(
                    objectSet.userID!!, objectSet.date
                ).enqueue(object : Callback<GetSelfDiagnosisSurveyOutput> {
                    override fun onResponse(
                        call: Call<GetSelfDiagnosisSurveyOutput>,
                        response: Response<GetSelfDiagnosisSurveyOutput>
                    ) {
                        selfDiagnosisSurveyData = response.body()
                    }

                    override fun onFailure(
                        call: Call<GetSelfDiagnosisSurveyOutput>,
                        t: Throwable
                    ) {

                        throw RuntimeException("서버 응답 실패")

                    }

                })

            }
        } catch (e: Exception) {
            throw RuntimeException("통신 중 오류 발생", e)
        }

    }

    private suspend fun getWellBeingScaleSurveyData() {

        return try {
            withContext(Dispatchers.IO) {
                getWellBeingScaleSurveyInterface.requestGetWellBeingScaleSurvey(
                    objectSet.userID!!, objectSet.date
                ).enqueue(object : Callback<GetWellBeingScaleSurveyOutput> {
                    override fun onResponse(
                        call: Call<GetWellBeingScaleSurveyOutput>,
                        response: Response<GetWellBeingScaleSurveyOutput>
                    ) {
                        wellBeingScaleSurveyData = response.body()
                    }

                    override fun onFailure(
                        call: Call<GetWellBeingScaleSurveyOutput>,
                        t: Throwable
                    ) {

                        throw RuntimeException("서버 응답 실패")

                    }

                })

            }
        } catch (e: Exception) {
            throw RuntimeException("통신 중 오류 발생", e)
        }

    }

    private suspend fun getPHQ9SurveyData() {

        return try {
            withContext(Dispatchers.IO) {
                getPHQ9SurveyInterface.requestGetPHQ9Survey(
                    objectSet.userID!!, objectSet.date
                ).enqueue(object : Callback<GetPHQ9SurveyOutput> {
                    override fun onResponse(
                        call: Call<GetPHQ9SurveyOutput>,
                        response: Response<GetPHQ9SurveyOutput>
                    ) {
                        phq9SurveyData = response.body()
                    }

                    override fun onFailure(
                        call: Call<GetPHQ9SurveyOutput>,
                        t: Throwable
                    ) {

                        throw RuntimeException("서버 응답 실패")

                    }

                })

            }
        } catch (e: Exception) {
            throw RuntimeException("통신 중 오류 발생", e)
        }

    }

    private suspend fun getGAD7SurveyData() {

        return try {
            withContext(Dispatchers.IO) {
                getGAD7SurveyInterface.requestGetGAD7Survey(
                    objectSet.userID!!, objectSet.date
                ).enqueue(object : Callback<GetGAD7SurveyOutput> {
                    override fun onResponse(
                        call: Call<GetGAD7SurveyOutput>,
                        response: Response<GetGAD7SurveyOutput>
                    ) {
                        gad7SurveyData = response.body()
                    }

                    override fun onFailure(
                        call: Call<GetGAD7SurveyOutput>,
                        t: Throwable
                    ) {

                        throw RuntimeException("서버 응답 실패")

                    }

                })

            }
        } catch (e: Exception) {
            throw RuntimeException("통신 중 오류 발생", e)
        }

    }

    private suspend fun getPSS10SurveyData() {

        return try {
            withContext(Dispatchers.IO) {
                getPSS10SurveyInterface.requestGetPSS10Survey(
                    objectSet.userID!!, objectSet.date
                ).enqueue(object : Callback<GetPSS10SurveyOutput> {
                    override fun onResponse(
                        call: Call<GetPSS10SurveyOutput>,
                        response: Response<GetPSS10SurveyOutput>
                    ) {
                        pss10SurveyData = response.body()
                    }

                    override fun onFailure(
                        call: Call<GetPSS10SurveyOutput>,
                        t: Throwable
                    ) {

                        throw RuntimeException("서버 응답 실패")

                    }

                })

            }
        } catch (e: Exception) {
            throw RuntimeException("통신 중 오류 발생", e)
        }

    }

    private suspend fun getExerciseSurveyData() {

        return try {
            withContext(Dispatchers.IO) {
                getExerciseSurveyInterface.requestGetExerciseSurvey(
                    objectSet.userID!!, objectSet.date
                ).enqueue(object : Callback<GetExerciseSurveyOutput> {
                    override fun onResponse(
                        call: Call<GetExerciseSurveyOutput>,
                        response: Response<GetExerciseSurveyOutput>
                    ) {
                        exerciseSurveyData = response.body()
                    }

                    override fun onFailure(
                        call: Call<GetExerciseSurveyOutput>,
                        t: Throwable
                    ) {

                        throw RuntimeException("서버 응답 실패")

                    }

                })

            }
        } catch (e: Exception) {
            throw RuntimeException("통신 중 오류 발생", e)
        }

    }

    private suspend fun getSmokingDrinkingSurveyData() {

        return try {
            withContext(Dispatchers.IO) {
                getSmokingDrinkingSurveyInterface.requestGetSmokingDrinkingSurvey(
                    objectSet.userID!!, objectSet.date
                ).enqueue(object : Callback<GetSmokingDrinkingSurveyOutput> {
                    override fun onResponse(
                        call: Call<GetSmokingDrinkingSurveyOutput>,
                        response: Response<GetSmokingDrinkingSurveyOutput>
                    ) {
                        smokingDrinkingSurveyData = response.body()
                    }

                    override fun onFailure(
                        call: Call<GetSmokingDrinkingSurveyOutput>,
                        t: Throwable
                    ) {

                        throw RuntimeException("서버 응답 실패")

                    }

                })

            }
        } catch (e: Exception) {
            throw RuntimeException("통신 중 오류 발생", e)
        }

    }

    private suspend fun getStressSurveyData() {

        return try {
            withContext(Dispatchers.IO) {
                getStressSurveyInterface.requestGetStressSurvey(
                    objectSet.userID!!, objectSet.date
                ).enqueue(object : Callback<GetStressSurveyOutput> {
                    override fun onResponse(
                        call: Call<GetStressSurveyOutput>,
                        response: Response<GetStressSurveyOutput>
                    ) {
                        stressSurveyData = response.body()
                    }

                    override fun onFailure(
                        call: Call<GetStressSurveyOutput>,
                        t: Throwable
                    ) {

                        throw RuntimeException("서버 응답 실패")

                    }

                })

            }
        } catch (e: Exception) {
            throw RuntimeException("통신 중 오류 발생", e)
        }

    }

    private suspend fun getNutritionSurveyData() {

        return try {
            withContext(Dispatchers.IO) {
                getNutritionSurveyInterface.requestGetNutritionSurvey(
                    objectSet.userID!!, objectSet.date
                ).enqueue(object : Callback<GetNutritionSurveyOutput> {
                    override fun onResponse(
                        call: Call<GetNutritionSurveyOutput>,
                        response: Response<GetNutritionSurveyOutput>
                    ) {
                        nutritionSurveyData = response.body()
                    }

                    override fun onFailure(
                        call: Call<GetNutritionSurveyOutput>,
                        t: Throwable
                    ) {

                        throw RuntimeException("서버 응답 실패")

                    }

                })

            }
        } catch (e: Exception) {
            throw RuntimeException("통신 중 오류 발생", e)
        }

    }

}