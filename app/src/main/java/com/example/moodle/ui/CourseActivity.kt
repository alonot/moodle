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
import com.example.moodle.db.models.Week
import com.example.moodle.ui.adapters.WeekAdapter
import com.example.moodle.ui.util.util
import com.google.android.material.tabs.TabLayout

class CourseActivity:AppCompatActivity() {

    lateinit var closebtn:ImageButton
    lateinit var courseNameBox : TextView
    lateinit var progressBar : ProgressBar
    lateinit var tabCourse: TabLayout
    lateinit var weekNotxt : TextView
    lateinit var rvWeek:RecyclerView
    lateinit var prevBtn : LinearLayout
    lateinit var nextBtn : LinearLayout
    lateinit var adapter : WeekAdapter
    lateinit var currentWeeks: List<Week>
    lateinit var CurrCourse : Course
    private var currentWeekNo:Int ?= 0
    private var pospresent:List<Int> ?= listOf()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course_details)
        initialize()

        closebtn.setOnClickListener {
            finish()
        }

        CurrCourse=util.currentCourse!!
        for(week in util.weeks){
            if(week!!.CourseId.equals(CurrCourse.Id)){
                currentWeeks=currentWeeks.plus(week)
                pospresent=pospresent!!.plus(week.weekId!!.substring(week.weekId.indexOf("w")+1).toInt())
            }
        }
        Log.d("MoodleApi",pospresent.toString())
        courseNameBox.text=CurrCourse.Id+" "+CurrCourse.name
        val course=tabCourse.newTab().setText("Course")
        val members=tabCourse.newTab().setText("Members")
        tabCourse.addTab(course)
        tabCourse.addTab(members)
        tabCourse.selectTab(course)

        adapter= WeekAdapter(this)
        rvWeek.adapter=adapter
        rvWeek.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        setRv()
        nextBtn.setOnClickListener {
            currentWeekNo=currentWeekNo!!+1
            if(currentWeekNo == util.currentCourse!!.totalWeeks){
                currentWeekNo=0
            }
            setRv()
        }
        prevBtn.setOnClickListener {
            currentWeekNo=currentWeekNo!!-1
            if(currentWeekNo == -1){
                currentWeekNo=util.currentCourse?.totalWeeks!!-1
            }
            setRv()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setRv(){
        weekNotxt.text="Week ${currentWeekNo}"
        if(pospresent!!.contains(currentWeekNo!!)){
            adapter.differTitle.submitList(currentWeeks[pospresent!!.indexOf(currentWeekNo!!)].Titles)
            adapter.differMark.submitList(currentWeeks[pospresent!!.indexOf(currentWeekNo!!)].MarkedDone)
        }else{
            adapter.differMark.submitList(listOf())
            adapter.differTitle.submitList(listOf())
        }
    }

    private fun initialize(){
        closebtn=findViewById(R.id.backCoursebtn)
        courseNameBox=findViewById(R.id.CourseName)
        progressBar=findViewById(R.id.progressBar)
        tabCourse=findViewById(R.id.Coursetab_layout)
        weekNotxt=findViewById(R.id.WeekNotxt)
        rvWeek=findViewById(R.id.rvWeekContent)
        prevBtn=findViewById(R.id.prevWeek_btn)
        nextBtn=findViewById(R.id.nextWeek_btn)
        currentWeeks= listOf()
        currentWeekNo=0
    }
}