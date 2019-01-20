package com.example.s_kim.memoproject

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ReadAdapter(private val mChat: List<ChatInfo>, private val color: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val right = 1
    val left = 2

    override fun getItemViewType(position: Int): Int {
        if (mChat.get(position).color.equals(color)) return right
        else return left
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var message: TextView =
            view.findViewById(R.id.message)        //view를 통째로 받아서 뷰에서 텍스트뷰를 찾아야하지만 텍스트뷰안에서 자신(텍스트뷰)을 찾으려고 하면 못 찾는다.


    }

    private fun setColor(message: TextView?, color: String?) {
        when (color) {
            "black" -> {
                message?.setTextColor(Color.BLACK)
            }
            "blue" -> {
                message?.setTextColor(Color.BLUE)
            }
            "red" -> {
                message?.setTextColor(Color.RED)
            }
            else -> {
                message?.setTextColor(Color.YELLOW)
            }
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReadAdapter.MyViewHolder {
        // create a new view
        var v: View? = null

        if (viewType == 1) {
            v = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.fragment_read_right,
                    parent,
                    false
                )
            //setColor(v)
        } else {
            v = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.fragment_read_left,
                    parent,
                    false
                )
            //setColor(v)

        }


        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(v)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myViewHolder = holder as MyViewHolder
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        myViewHolder.message.text = mChat[position].chatMessage

        setColor(myViewHolder.message, mChat[position].color)

    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = mChat.size


}