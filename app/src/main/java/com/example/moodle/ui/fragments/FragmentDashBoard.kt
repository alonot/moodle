package com.example.moodle.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.PopupMenu.OnMenuItemClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodle.R
import com.example.moodle.db.models.Course
import com.example.moodle.db.models.LoginRequest
import com.example.moodle.ui.CourseActivity
import com.example.moodle.ui.adapters.CourseAdapter
import com.example.moodle.ui.util.util


class FragmentDashBoard: Fragment(R.layout.activity_dashboard) {

    lateinit var btn_next:ImageButton
    lateinit var btn_prev:ImageButton
    lateinit var rvRecent:RecyclerView
    lateinit var rvAll:RecyclerView
    lateinit var spinner_type:TextView
    lateinit var btn_Order:ImageButton
    lateinit var btn_layout:ImageButton
    private var currentlyShowingGrid=false
    private var isCurrentOrderCourse=true
    private lateinit var recentAdapter: CourseAdapter
    private lateinit var courseAdapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.activity_dashboard,container,false)
        initialize(view)
        val viewsModel= util.viewModel
        // Temporary till login page is made
        val loginrequest = LoginRequest("csk200@gmail.com","1111")
        viewsModel.login(loginrequest)
        setRecyclerViews()

        spinner_type.setOnClickListener {
            showpopup(it,R.menu.type_menu)
        }
        btn_Order.setOnClickListener {
            showpopup(it,R.menu.order_menu)
        }
        btn_layout.setOnClickListener {
            if(currentlyShowingGrid) {
                btn_layout.setImageResource(R.drawable.ic_grid)
                courseAdapter= CourseAdapter(R.layout.course_item)
                rvAll.adapter=courseAdapter
            }
            else {
                courseAdapter= CourseAdapter(R.layout.recent_item)
                rvAll.adapter=courseAdapter
                btn_layout.setImageResource(R.drawable.ic_linear)
            }
            courseAdapter.setOnClickListenerr {
                callCourseActivity(it)
            }
            courseAdapter.differ.submitList(listOf())
            courseAdapter.differ.submitList(util.allCourses)
            currentlyShowingGrid=!currentlyShowingGrid
        }

        return view
    }


    fun updateRV(){
        courseAdapter.differ.submitList(listOf())
        courseAdapter.differ.submitList(util.allCourses)
        recentAdapter.differ.submitList(util.allCourses!!.toList())
    }

    // On menu click listener to show popup ... spinner.
    val myMenuItemClickListener=object : OnMenuItemClickListener{
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when(item?.itemId){
                R.id.type_all->{
                    spinner_type.text=item.title
                    //Toast.makeText(requireContext(),"All",Toast.LENGTH_SHORT).show()
                    courseAdapter.differ.submitList(util.allCourses)
                }
                R.id.type_future->{
                    spinner_type.text=item.title
                    Toast.makeText(requireContext(),"future",Toast.LENGTH_SHORT).show()
                    courseAdapter.differ.submitList(listOf())
                    courseAdapter.differ.submitList(util.futureCourse)
                }
                R.id.type_hidden->{
                    spinner_type.text=item.title
                    Toast.makeText(requireContext(),"hidden",Toast.LENGTH_SHORT).show()
                    courseAdapter.differ.submitList(listOf())
                    courseAdapter.differ.submitList(util.hiddenCourse)
                }
                R.id.type_past->{
                    spinner_type.text=item.title
                    Toast.makeText(requireContext(),"past",Toast.LENGTH_SHORT).show()
                    courseAdapter.differ.submitList(listOf())
                    courseAdapter.differ.submitList(util.pastCourse)
                }
                R.id.type_starred->{
                    spinner_type.text=item.title
                    Toast.makeText(requireContext(),"starred",Toast.LENGTH_SHORT).show()
                    courseAdapter.differ.submitList(listOf())
                    courseAdapter.differ.submitList(util.starredCourse)
                }
                R.id.type_present->{
                    spinner_type.text=item.title
                    Toast.makeText(requireContext(),"prevent",Toast.LENGTH_SHORT).show()
                    courseAdapter.differ.submitList(listOf())
                    courseAdapter.differ.submitList(util.presentCourse)
                }
                R.id.order_LastCourse->{
                    isCurrentOrderCourse=false
                    Toast.makeText(requireContext(),"lastAccessed",Toast.LENGTH_SHORT).show()
                }
                R.id.order_courseName-> {
                    isCurrentOrderCourse=true
                    Toast.makeText(requireContext(), "courseName", Toast.LENGTH_SHORT).show()
                    util.OrderByName=util.allCourses!!.sortedWith(object :Comparator<Course?>{
                        override fun compare(p0: Course?, p1: Course?): Int {
                            return p0?.name!!.compareTo(p1?.name!!)
                        }
                    })
                    courseAdapter.differ.submitList(util.OrderByName)
                }
            }
            return true
        }
    }

    private fun callCourseActivity(course: Course){
        Intent(context,CourseActivity::class.java).also {
            util.currentCourse=course
            startActivity(it)
        }
    }

    private fun setRecyclerViews(){
        recentAdapter= CourseAdapter(R.layout.recent_item)
        recentAdapter.setOnClickListenerr {
            callCourseActivity(it)
        }
        rvRecent.adapter=recentAdapter
        rvRecent.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        courseAdapter= CourseAdapter(R.layout.course_item)
        courseAdapter.setOnClickListenerr {
            callCourseActivity(it)
        }
        rvAll.adapter=courseAdapter
        rvAll.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
    }

    private fun showpopup(view: View,menu:Int){
        val popup=PopupMenu(requireContext(),view)
        popup.setOnMenuItemClickListener(myMenuItemClickListener)
        popup.inflate(menu)
        popup.show()

    }


    private fun initialize(view:View){
        btn_next=view.findViewById(R.id.btn_nextCourse)
        btn_prev=view.findViewById(R.id.btn_prevCourse)
        rvRecent=view.findViewById(R.id.rvRecentCourses)
        spinner_type=view.findViewById(R.id.spinner)
        btn_Order=view.findViewById(R.id.btn_orderIn)
        btn_layout=view.findViewById(R.id.btn_showAs)
        rvAll=view.findViewById(R.id.rvCourses)
    }
}