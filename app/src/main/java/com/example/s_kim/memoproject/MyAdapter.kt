package com.example.s_kim.memoproject

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MyAdapter(private val memoInfoArrayList: List<MemoInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //아래 뷰홀더에서 받은 v를 레이아웃의 프래그먼트와 연결
    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title1)
        var message: TextView = view.findViewById(R.id.message1)


    }

    //여기 뷰홀더가 보여주는 거다
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_article, parent, false)

        // v.findViewById(R.id.Title1) 처럼 바로 연결할수도 있나?

        return MyViewHolder(v)  // 위에 프래그먼트와 연결하기위해 MyViewHolder 메소드와 연결

    }

    //뷰홀더의 내용을 리스트의 내용과 연결시킴
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myViewHolder = holder as MyViewHolder
      //  Log.d("ccc123", myViewHolder.toString())
        myViewHolder.title.text = memoInfoArrayList[position].title
        myViewHolder.message.text = memoInfoArrayList[position].message
    }

    //리스트가 없을 경우
    override fun getItemCount(): Int {
        //Log.d("ddd", this.toString())
        return memoInfoArrayList.size
    }
}
