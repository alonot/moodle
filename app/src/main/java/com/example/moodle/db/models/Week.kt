package com.example.moodle.db.models

data class Week(
    val Comments: List<String?>?,
    val CourseId: String?,
    val MarkedDone: List<Boolean?>?,
    val Titles: List<String?>?,
    val Type: List<String?>?,
    val _id: String?,
    val createdOn: List<String?>?,
    val sentFiles: List<String?>?,
    val submissionOn: List<Boolean?>?,
    val submitedFiles: List<String?>?,
    val weekId: String?
)