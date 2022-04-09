package com.example.surveyapp.model


data class QuestionsItem(
    var index: String?,
    var isRequired: String?,
    var questionType: String?,
    var suggestedAnswers: List<String>?,
    var text: String?
)