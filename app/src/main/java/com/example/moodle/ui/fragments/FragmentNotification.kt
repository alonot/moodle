package com.example.moodle.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.moodle.R
import com.example.moodle.db.models.Notification
import com.example.moodle.ui.CourseActivity
import com.example.moodle.ui.NotificationActivity
import com.example.moodle.ui.adapters.NotificationAdapter
import com.example.moodle.ui.util.util

class FragmentNotification: Fragment(R.layout.activity_notification) {

    lateinit var rvNotification: RecyclerView
    lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.activity_notification,container,false)
        rvNotification=view.findViewById(R.id.rvnotification)
        adapter= NotificationAdapter()
        adapter.setOnClickListenerr {notification ->
            Intent(context, NotificationActivity::class.java).also {
                util.currentNotification=notification
                startActivity(it)
            }
        }
        rvNotification.adapter=adapter
        rvNotification.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        Log.d("MoodleAPI",util.notificationsList.toString())
        updateRV()
        return view
    }

    fun updateRV() {
        if(this::adapter.isInitialized)
            adapter.differ.submitList(util.notificationsList)
    }
}