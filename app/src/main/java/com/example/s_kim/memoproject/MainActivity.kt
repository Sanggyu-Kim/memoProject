package com.example.s_kim.memoproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import java.util.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private var viewAdapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //데이터 등록
        val memoInfoArrayList = listOf(
            MemoInfo("title1", "111"),
            MemoInfo("title2", "222"),
            MemoInfo("title3", "333")
        )
       Log.d("aaa", memoInfoArrayList.toString())


        //핵심으로 넣어야할 것들
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(memoInfoArrayList)
        mRecyclerView.adapter = viewAdapter  //이게 없으면 아무것도 나오지 않는다.



//데이터 작성
        // val titleList:MutableList<String> // titleList 작성
        // val titleListTest = arrayOf("kim","kkd","ddd")


//                viewAdapter = MyAdapter(memoInfoArrayList)


//        mRecyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
//
//            setHasFixedSize(true)
//
//            // use a linear layout manager
//            layoutManager = viewManager
//
//            // specify an viewAdapter (see also next example)
//            adapter = viewAdapter
//
//        }


    }
}
