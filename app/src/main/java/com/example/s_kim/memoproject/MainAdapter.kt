package com.example.s_kim.memoproject

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MainAdapter(private val readMove: ClickRead, private val memoInfoArrayList: List<MemoInfo>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     *下記viewholderでいただいた(v)をlayoutのfragmentと連結
     */
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title1)
        var message: TextView = view.findViewById(R.id.message1)
        var line: View = view.findViewById(R.id.line)

    }

    /**ここがviewholderを見せる
     *layout を作って viewholderに保存
     *viewholderを生成しviewを連結する部分
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_main,
                     parent,
                     false)

        // v.findViewById(R.id.Title1)ようにすぐ連結できる?                           (v.findViewById(R.id.Title1)처럼 바로 연결할수도 있나?)

        return MyViewHolder(v)  // 上記fragmentと連結のためにMyViewHolderメソッドと連結 (위에 프래그먼트와 연결하기위해 MyViewHolder 메소드와 연결)

    }

    /**viewholderの内容をlistの内容と結び付ける (뷰홀더의 내용을 리스트의 내용과 연결시킴)
     *渡してもらったdataを画面に出力する役割    (넘겨 받은 데이터를 화면에 출력하는 역할)
     *viewが呼び出して実行されるメソッド        (재활용되는 뷰가 호출하여 실행되는 메소드)
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myViewHolder = holder as MyViewHolder

        // 値を設定 (값설정)
        val memoNumber = memoInfoArrayList[position].memoNumber
        myViewHolder.title.text = memoInfoArrayList[position].title
        myViewHolder.message.text = memoInfoArrayList[position].message

        //RecyclerViewのlistを押すの場合、今押しているmemoの個人番号,title,messageを一緒に含めてmainActivityに移動!

        holder.itemView.setOnClickListener {
            readMove
                .onItemClickedAction(memoNumber,
                                    myViewHolder.title.text.toString(),
                                    myViewHolder.message.text.toString())
        }
    }

    /**
     * listがない時
     */
    override fun getItemCount(): Int {
        //Log.d("ddd", this.toString())
        return memoInfoArrayList.size
    }

    /**
     * RecyclerViewのlistを押すの場合、呼ぶ
     */
    interface ClickRead {
        fun onItemClickedAction(memoNumber: Int, text: String, Message: String)
    }

}
