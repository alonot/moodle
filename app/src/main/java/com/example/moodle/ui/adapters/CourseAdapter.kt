package com.example.moodle.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moodle.R
import com.example.moodle.db.models.Course

class CourseAdapter(
    val layout : Int
): RecyclerView.Adapter<CourseAdapter.ViewHolder>(){

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var txt_course: TextView
        var txt_duration: TextView
        init {
            txt_course=view.findViewById(R.id.CourseName)
            txt_duration=view.findViewById(R.id.courseDuration)
        }
    }

    private val differCallback = object :DiffUtil.ItemCallback<Course>(){
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }

    val differ=AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onClickListener:((Course)-> Unit) ? = null

    fun setOnClickListenerr(listener: (Course) -> Unit){
        onClickListener=listener
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCourse = differ.currentList[position]

        holder.txt_course.text= "${currentCourse.Id} ${currentCourse.name}"
        holder.txt_duration.text= currentCourse.duration

        holder.itemView.setOnClickListener{
            onClickListener?.let {
                it(currentCourse) }
        }
    }
}