package com.example.moodle.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moodle.R
import com.example.moodle.db.models.Course
import com.example.moodle.db.models.Notification

class NotificationAdapter: RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notification_item,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNotification= differ.currentList[position]

        holder.contentBox.text=currentNotification.courseId+":"+currentNotification.topic
        holder.dayAndByBox.text=currentNotification.sentOn+"\t. "+currentNotification.sentby
        if(currentNotification.read!!){
            holder.readbtn.visibility=View.INVISIBLE
        }
        holder.itemView.setOnClickListener{
            onClickListener?.let {
                it(currentNotification) }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Notification>(){
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.notificationId == newItem.notificationId
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }
    }

    private var onClickListener:((Notification)-> Unit) ? = null

    fun setOnClickListenerr(listener: (Notification) -> Unit){
        onClickListener=listener
    }

    val differ = AsyncListDiffer(this, diffCallback)

    inner class  ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var contentBox : TextView
        var dayAndByBox : TextView
        var readbtn :ImageView
        init {
            contentBox=view.findViewById(R.id.txt_content)
            dayAndByBox =view.findViewById(R.id.txt_dayBefore)
            readbtn = view.findViewById(R.id.img_unread)
        }
    }
}