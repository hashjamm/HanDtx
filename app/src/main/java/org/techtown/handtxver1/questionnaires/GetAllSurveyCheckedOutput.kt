package org.techtown.handtxver1.questionnaires

data class GetAllSurveyCheckedOutput(

    var issue_checking: Boolean?,
    var self_diagnosis: Boolean?,
    var well_being_scale: Boolean?,
    var phq9: Boolean?,
    var gad7: Boolean?,
    var pss10: Boolean?,
    var exercise: Boolean?,
    var smoking_drinking: Boolean?,
    var stress: Boolean?,
    var nutrition: Boolean?

)
