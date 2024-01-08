package com.example.moodle.db.models

data class Notification(
    val _id: String?,
    val courseId: String?,
    val notificationId: String?,
    val read: Boolean?,
    val sentOn: String?,
    val sentby: String?,
    val topic: String?,
    val content :String?
)