package com.example.moodle.db.models

data class Course(
    val Id: String?,
    val _id: String?,
    val color: String?,
    val duration: String?,
    val name: String?,
    val notifications: MutableList<String?>?,
    val weeks: MutableList<String?>?,
    val totalWeeks: Int?
)