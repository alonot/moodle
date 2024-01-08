package com.example.moodle.ui

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.moodle.R
import com.example.moodle.db.models.LoginRequest
import com.example.moodle.db.repositories.MoodleRepository
import com.example.moodle.ui.fragments.FragmentApp
import com.example.moodle.ui.fragments.FragmentDashBoard
import com.example.moodle.ui.fragments.FragmentMainPage
import com.example.moodle.ui.fragments.FragmentNotification
import com.example.moodle.ui.util.MoodleViewModelProviderFactory
import com.example.moodle.ui.util.Resource
import com.example.moodle.ui.util.util
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


class MoodleActivity : AppCompatActivity() {

    private lateinit var account_navigation:NavigationView
    private lateinit var menu_navigation:NavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var bottomNavigationView: BottomNavigationView
    var MainFragment: FragmentMainPage?=null
    var NotificationFragment: FragmentNotification?=null
    var AppFragment: FragmentApp?=null
    lateinit var btn_menu:FloatingActionButton
    lateinit var btn_account:ImageView
    lateinit var viewsModel: MoodleViewsModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moodle)
        initialize()

        // Right Drawer
        btn_account.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        // Left Drawer
        btn_menu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            setmenuHeader()
        }

        // Try to draw button
        btn_menu.setOnLongClickListener{
            val item=ClipData.Item("Hello")
            val mimeType= arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data=ClipData("Hello",mimeType,item)
            val shadowBuilder= View.DragShadowBuilder(it)
            it.startDragAndDrop(data,shadowBuilder,it,0)
            it.visibility=View.INVISIBLE
            true
        }

        MainFragment= FragmentMainPage()
        AppFragment= FragmentApp()
        NotificationFragment= FragmentNotification()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame,MainFragment!!)
            commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item->
            setBottomNavTab(item.itemId)
            true
        }

        // Initialising Repo..
        val repository = MoodleRepository()
        val viewModelProviderFactory = MoodleViewModelProviderFactory(application,repository)
        viewsModel = ViewModelProvider(this,viewModelProviderFactory)[MoodleViewsModel::class.java]
        util.viewModel = viewsModel


        viewsModel.loginResponse.observe(this, Observer { response ->
            run {
                when (response) {
                    is Resource.Success -> {
                        Log.d("MoodleAPI", response.data?.courses!!.toString())
                        response.data.let{apiResponse ->
                            util.allCourses=(apiResponse.courses!!.toList())
                            util.weeks=apiResponse.weeks!!.toList()
                            util.hiddenCourse= listOf()
                            util.notificationsList=util.notificationsList.plus(apiResponse.notifications!!.toList())
                            for (course in util.allCourses!!){
                                if(apiResponse.hidden!!.contains(course!!.Id)){
                                    util.hiddenCourse=util.hiddenCourse.plus(course)
                                    util.allCourses= util.allCourses!!.minus(course)
                                }
                                if(apiResponse.present!!.contains(course.Id)){
                                    util.presentCourse=util.presentCourse.plus(course)
                                }
                                if(apiResponse.past!!.contains(course.Id)){
                                    util.pastCourse=util.pastCourse.plus(course)
                                }
                                if(apiResponse.future!!.contains(course.Id)){
                                    util.futureCourse=util.futureCourse.plus(course)
                                }
                            }
                            setaccountHeader(apiResponse.name!!)
                        }
                        MainFragment!!.updateRV()
                        if (NotificationFragment != null)
                            NotificationFragment!!.updateRV()
                    }

                    is Resource.Error -> {
                        Log.d("MoodleAPI", response.message!!)
                        response.message.let { message ->
                            Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Resource.Loading -> {

                    }
                }
            }
        })

    }

    private fun setaccountHeader(name:String){
        val headerView=account_navigation.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.drawer_user_name).text=name
    }

    private fun setmenuHeader(){

    }


    private fun setBottomNavTab(id:Int){
        when(id) {
            R.id.home -> setFragment(MainFragment!!)
            R.id.notification -> {
                setFragment(NotificationFragment!!)}
            R.id.app -> {
                setFragment(AppFragment!!)
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame,fragment)
            commit()
        }
    }

    private fun initialize(){
        account_navigation=findViewById(R.id.account_navigator)
        drawerLayout=findViewById(R.id.drawer_layout)
        menu_navigation=findViewById(R.id.menu_navigator)
        bottomNavigationView=findViewById(R.id.bootom_nav_bar)
        btn_menu=findViewById(R.id.menu_btn)
        btn_account=findViewById(R.id.btn_account)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(event?.action == KeyEvent.ACTION_DOWN){
            if(keyCode == KeyEvent.KEYCODE_BACK){
                MainFragment?.goBack()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        when(bottomNavigationView.selectedItemId){
            R.id.home ->{
                onBackPressedDispatcher.onBackPressed()}
            else -> {
                bottomNavigationView.menu.findItem(R.id.home).isChecked = true
                setBottomNavTab(bottomNavigationView.selectedItemId)
            }
        }
    }




}