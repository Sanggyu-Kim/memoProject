package com.example.s_kim.memoproject

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlin.coroutines.coroutineContext

class MyAdapter(private val readMove: ClickRead, private val memoInfoArrayList: List<MemoInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //아래 뷰홀더에서 받은 v를 레이아웃의 프래그먼트와 연결
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title1)
        var message: TextView = view.findViewById(R.id.message1)
        var line: View = view.findViewById(R.id.line)
    }

    //여기 뷰홀더가 보여주는 거다

    //레이아웃을 만들어 Holder에 저장
    //뷰홀더를 생성하고 뷰를 붙여주는 부분
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_article, parent, false)

        // v.findViewById(R.id.Title1) 처럼 바로 연결할수도 있나?

        return MyViewHolder(v)  // 위에 프래그먼트와 연결하기위해 MyViewHolder 메소드와 연결

    }

    //뷰홀더의 내용을 리스트의 내용과 연결시킴
    //넘겨 받은 데이터를 화면에 출력하는 역할
    //재활용되는 뷰가 호출하여 실행되는 메소드
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myViewHolder = holder as MyViewHolder
        //  Log.d("ccc123", myViewHolder.toString())
        val memoNumber = memoInfoArrayList[position].memoNumber
        myViewHolder.title.text = memoInfoArrayList[position].title
        myViewHolder.message.text = memoInfoArrayList[position].message


        // 값설정
        holder.itemView.setOnClickListener {
        //    Log.d("aaa", "hellow")

            Log.d("aaa","Title:"+myViewHolder.title.text)
            readMove.onItemClick(memoNumber,myViewHolder.title.text.toString(),myViewHolder.message.text.toString())

        }


    }

    //리스트가 없을 경우
    override fun getItemCount(): Int {
        //Log.d("ddd", this.toString())
        return memoInfoArrayList.size
    }


    interface ClickRead {

        fun onItemClick(memoNumber:Int, text:String, Message:String)

    }


}
