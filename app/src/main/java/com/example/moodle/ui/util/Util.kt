package com.example.moodle.ui.util

import com.example.moodle.db.models.Course
import com.example.moodle.db.models.Notification
import com.example.moodle.db.models.Week
import com.example.moodle.ui.MoodleViewsModel

object util {
    val BASE_URL:String = "http://10.0.2.2:5000/"
    lateinit var viewModel : MoodleViewsModel
    var allCourses:List<Course?>? = listOf()
    var pastCourse:List<Course?> = listOf()
    var presentCourse:List<Course?> = listOf()
    var futureCourse:List<Course?> = listOf()
    var starredCourse:List<Course?> = listOf()
    var hiddenCourse:List<Course?> = listOf()
    var OrderByName:List<Course?> = listOf()
    var notificationsList:List<Notification?> = listOf()
    var weeks:List<Week?> = listOf()
    var currentCourse : Course? = null
    var currentNotification : Notification? = null
}