package com.example.moodle.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moodle.R
import com.google.android.material.tabs.TabLayout

class FragmentMainPage:Fragment() {
    lateinit var tabLayout:TabLayout
    private var isWebViewOn = false
    var WebFragment: FragmentWebView?=null
    var DashboardFragment: FragmentDashBoard?=null
    lateinit var dashboardTab: TabLayout.Tab
    lateinit var siteTab: TabLayout.Tab

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.activity_main_page,container,false)
        initialize(view)

        activity?.supportFragmentManager!!.beginTransaction().apply {
            replace(R.id.inner_frame,DashboardFragment!!)
            commit()
        }

        tabLayout=view.findViewById(R.id.tab_layout)
        dashboardTab=tabLayout.newTab().setText("DashBoard")
        siteTab=tabLayout.newTab().setText("Site Home")
        tabLayout.addTab(dashboardTab)
        tabLayout.addTab(siteTab)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.text) {
                    "DashBoard" -> {setFragment(DashboardFragment!!)
                        isWebViewOn= false}
                    "Site Home"-> {isWebViewOn=true
                        setFragment(WebFragment!!)}
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        return view
    }

    fun updateRV(){
        DashboardFragment!!.updateRV()
    }

    private fun  initialize(view: View){
        DashboardFragment= FragmentDashBoard()
        tabLayout=view.findViewById(R.id.tab_layout)
        WebFragment= FragmentWebView()
    }


    private fun setFragment(fragment: Fragment) {
        activity?.supportFragmentManager!!.beginTransaction().apply {
            replace(R.id.inner_frame,fragment)
            addToBackStack(null)
            commit()
        }
    }


    fun goBack() {
        if(isWebViewOn)
            if(! WebFragment!!.goBack()){
                tabLayout.selectTab(dashboardTab)
            }
    }

}