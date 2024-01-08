package com.example.moodle.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moodle.R
import com.example.moodle.db.models.Week
import com.google.android.material.card.MaterialCardView

class WeekAdapter(
    val context: Context
): RecyclerView.Adapter<WeekAdapter.ViewHolder>(){

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var txt_course: TextView
        var btnMark: TextView
        var btnMarkHolder:MaterialCardView
        init {
            txt_course=view.findViewById(R.id.CourseName)
            btnMark=view.findViewById(R.id.markDone)
            btnMarkHolder=view.findViewById(R.id.weekmarkbtnHolder)
        }
    }

    private val differCallback = object :DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differCallbackMark = object :DiffUtil.ItemCallback<Boolean>(){
        override fun areItemsTheSame(oldItem: Boolean, newItem: Boolean): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Boolean, newItem: Boolean): Boolean {
            return oldItem == newItem
        }
    }

    val differTitle=AsyncListDiffer(this,differCallback)
    val differMark=AsyncListDiffer(this,differCallbackMark)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.week_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differTitle.currentList.size
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForColorStateLists")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTitle = differTitle.currentList[position]
        var mark=differMark.currentList[position]

        holder.txt_course.text= "${currentTitle}"
        if(mark){
            holder.btnMark.text="Mark as Done"
            holder.btnMarkHolder.backgroundTintList=ContextCompat.getColorStateList(context,R.color.white)
        }else{
            holder.btnMark.text="Done"
            holder.btnMarkHolder.backgroundTintList=ContextCompat.getColorStateList(context,R.color.green)
        }

        holder.btnMark.setOnClickListener {
            mark=!mark
            if(mark){
                holder.btnMark.text="Mark as Done"
                holder.btnMarkHolder.backgroundTintList=ContextCompat.getColorStateList(context,R.color.white)
            }else{
                holder.btnMark.text="Done"
                holder.btnMarkHolder.backgroundTintList=ContextCompat.getColorStateList(context,R.color.green)
            }
        }
    }
}