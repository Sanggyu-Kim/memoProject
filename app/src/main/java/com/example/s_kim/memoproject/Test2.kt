package com.example.s_kim.memoproject

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import java.util.ArrayList

//class Test2 internal constructor(private val memoInfoArrayList: ArrayList<MemoInfo>) :
//    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    class MyViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
//         var title: TextView
//         var message: TextView
//
//        init {
//            title = view.findViewById(R.id.title)
//            message = view.findViewById(R.id.message)
//
//        }
//    }
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//
//        val v = LayoutInflater.from(parent.context)
//            .inflate(R.layout.fragment_article, parent, false)
//
//        return MyViewHolder(v)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//
//        val myViewHolder = holder as MyViewHolder
//        Log.d("aaa", myViewHolder.toString())
//        myViewHolder.title.setText(memoInfoArrayList[position].title)
//        myViewHolder.message.setText(memoInfoArrayList[position].message)
//    }
//
//    override fun getItemCount(): Int {
//        return memoInfoArrayList.size
//    }
//}
