package com.example.moodle.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodle.R
import com.example.moodle.db.models.Course
import com.example.moodle.db.models.Notification
import com.example.moodle.db.models.Week
import com.example.moodle.ui.adapters.WeekAdapter
import com.example.moodle.ui.util.util
import com.google.android.material.tabs.TabLayout

class NotificationActivity:AppCompatActivity() {

    lateinit var closebtn:ImageButton
    lateinit var courseNameBox : TextView
    lateinit var Daybefore : TextView
    lateinit var link : TextView
    lateinit var Viewbtn : LinearLayout
    lateinit var contentBox : TextView
    lateinit var CurrNotification : Notification

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_details)
        initialize()

        closebtn.setOnClickListener {
            finish()
        }
        CurrNotification=util.currentNotification!!
        courseNameBox.text=CurrNotification.courseId+":"+CurrNotification.topic
        Daybefore.text=CurrNotification.sentOn+" . "+CurrNotification.sentby
        link.text=CurrNotification.courseId
        contentBox.text=CurrNotification.content

    }


    private fun initialize(){
        closebtn=findViewById(R.id.backNotificationbtn)
        courseNameBox=findViewById(R.id.txt_content)
        Daybefore=findViewById(R.id.txt_dayBefore)
        contentBox=findViewById(R.id.contentBox)
        link=findViewById(R.id.link)
        Viewbtn=findViewById(R.id.Look_btn)
    }
}