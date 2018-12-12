package com.example.s_kim.memoproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var mRecyclerView: RecyclerView
    private var viewAdapter: MyAdapter? = null


    //데이터 등록
    private val memoInfoArrayList = mutableListOf<MemoInfo>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //button createActivity로 이동
        findViewById<FloatingActionButton>(R.id.create).setOnClickListener {
            Toast.makeText(this, "here", Toast.LENGTH_LONG).show()
            val changeCreate = Intent(this, CreateActivity::class.java)
            startActivityForResult(changeCreate, 1)
        }


        //핵심으로 넣어야할 것들
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(memoInfoArrayList)
        mRecyclerView.adapter = viewAdapter  //이게 없으면 아무것도 나오지 않는다.


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {
                val title = data?.getStringExtra("title") ?: "" //?반드시 null이 아닐 경우만
                val message = data?.getStringExtra("message") ?: ""

                memoInfoArrayList.add(MemoInfo(title, message))  //리스트에도 ?를 추가해야 한다.
            }


        }

    }

}
