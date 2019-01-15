package com.example.s_kim.memoproject

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ReadAdapter(private val mChat: List<ChatInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var message: TextView = view.findViewById(R.id.messageD)        //view를 통째로 받아서 뷰에서 텍스트뷰를 찾아야하지만 텍스트뷰안에서 자신(텍스트뷰)을 찾으려고 하면 못 찾는다.
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReadAdapter.MyViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.fragment_read,
                parent,
                false
            )
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(v)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myViewHolder = holder as MyViewHolder
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        myViewHolder.message.text = mChat[position].chatMessage
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = mChat.size



}