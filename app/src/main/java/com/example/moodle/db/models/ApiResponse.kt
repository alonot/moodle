package com.example.moodle.db.models

data class ApiResponse(
    val courses:MutableList<Course?>?,
    val email: String?,
    val hidden: List<String?>?,
    val present: List<String?>?,
    val future: List<String?>?,
    val past: List<String?>?,
    val name: String?,
    val notifications: MutableList<Notification?>?,
    val password: String?,
    val sessionId: String?,
    val success: Boolean?,
    val msg: String?,
    val weeks: MutableList<Week>?
)