package com.example.s_kim.memoproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Adapter
import android.widget.Button
import android.widget.Toast


class MainActivity : AppCompatActivity(), MyAdapter.ClickRead {
    private lateinit var mRecyclerView: RecyclerView
    private var viewAdapter: MyAdapter? = null

    var memoListNumber: Int = 0

    //데이터 등록
    private val memoInfoArrayList = mutableListOf<MemoInfo>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //button createActivity로 이동
        findViewById<FloatingActionButton>(R.id.create).setOnClickListener {
            //       Toast.makeText(this, "here", Toast.LENGTH_LONG).show()
            val changeCreate = Intent(this, CreateActivity::class.java)
            startActivityForResult(changeCreate, 1)
        }


        //핵심으로 넣어야할 것들
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(this, memoInfoArrayList)  //this는 class 뿐만아니라 adapter도 this
        mRecyclerView.adapter = viewAdapter  //이게 없으면 아무것도 나오지 않는다.

    }


    override fun onItemClick(memoNumber: Int, title: String, message: String) {
        val moveToReadIntent = Intent(this, ReadActivity::class.java)
        // Toast.makeText(this,"title: "+title +"message: "+message,Toast.LENGTH_LONG).show()
        moveToReadIntent.putExtra("memoNumber", memoNumber)
        moveToReadIntent.putExtra("title", title)
        moveToReadIntent.putExtra("message", message)
        startActivityForResult(moveToReadIntent, 2)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                ++memoListNumber

                val title = data?.getStringExtra("title") ?: "" //?반드시 null이 아닐 경우만
                val message = data?.getStringExtra("message") ?: ""
                memoInfoArrayList.add(MemoInfo(memoListNumber, title, message))  //리스트에도 ?를 추가해야 한다.
            }
        }

        if (requestCode == 2) {
            when (resultCode) {
                MemoConst.RESULT_DELETE -> {
                    val deleteTitle = data?.getStringExtra("deleteTitle") ?: ""  //null 許可しません。
                    val deleteMessage = data?.getStringExtra("deleteMessage") ?: "" // null　許可しません。
                    val deleteNumber = data?.getIntExtra("deleteNumber", 0) ?: 0 //null 許可しません。
                    val indexOfmemo = memoInfoArrayList.indexOf(MemoInfo(deleteNumber, deleteTitle, deleteMessage))
                    memoInfoArrayList.remove(MemoInfo(deleteNumber,deleteTitle,deleteMessage))

                }

                MemoConst.RESULT_RENEW -> {
                    val renewNumber = data?.getIntExtra("renewNumber", 0) ?: 0 //null 許可しません。

                    val pastTitle = data?.getStringExtra("pastTitle") ?: ""
                    val pastMessage = data?.getStringExtra("pastMessage") ?: ""

                    val renewTitle = data?.getStringExtra("renewTitle") ?: ""
                    val renewMessage = data?.getStringExtra("renewMessage") ?: ""

                    val indexOfmemo2 = 0//memoInfoArrayList.indexOf(MemoInfo(renewNumber, pastTitle, pastMessage))
                    Log.d(
                        "vvv",
                        "renewNumber:$renewNumber renew Message: $renewMessage passMessage: $pastMessage indextofMemo2:$indexOfmemo2"
                    )

                    memoInfoArrayList.set(indexOfmemo2,MemoInfo(renewNumber, renewTitle, renewMessage))
                    viewAdapter?.notifyDataSetChanged()
                }
            }
        }
    }
}
