package com.example.s_kim.memoproject


import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import java.util.ArrayList

//class Test1(val memoInfoArrayList: ArrayList<MemoInfo>) :
//    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
//
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder.
//    // Each data item is just a string in this case that is shown in a TextView.
//    class MyViewHolder(val title: TextView) : RecyclerView.ViewHolder(title)
//
//
//    val memoInfoArrayList: ArrayList<MemoInfo>
//
//    internal fun MyAdapter(memoInfoArrayList: ArrayList<MemoInfo>){
//        this.memoInfoArrayList= memoInfoArrayList
//        Log.d("aaa",memoInfoArrayList.toString())
//    }
//
//
//    // Create new views (invoked by the layout manager)
//    override fun onCreateViewHolder(parent: ViewGroup,
//                                    viewType: Int): MyAdapter.MyViewHolder {
//        // create a new view
//        val memoTextView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.fragment_article, parent, false) as TextView
//        // set the view's size, margins, paddings and layout parameters
//        // 레이아웃에서 프래그먼트와 연결
//
//        return MyViewHolder(memoTextView)
//    }
//
//
//
//
//    // Replace the contents of a view (invoked by the layout manager)
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        holder.title.setText(memoInfoArrayList.get(position).title)
//        Log.d("aaa", holder.toString())
//
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    override fun getItemCount() = memoInfoArrayList.size
//}