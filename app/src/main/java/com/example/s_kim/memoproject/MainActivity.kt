package com.example.s_kim.memoproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.*
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.FirebaseDatabase
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity(), MyAdapter.ClickRead {

    private lateinit var mRecyclerView: RecyclerView
    private var viewAdapter: MyAdapter? = null

    var memoListNumber: Int = 0 //Memoの別個人番号(削除と変更のため)
    //private var mFirebaseAnalytics: FirebaseAnalytics? = null //firebase
    private var memoInfoArrayList = mutableListOf<MemoInfo>() //Data登録

    //val dbHelper = DBHelper(applicationContext, "MEMOLIST.db", null!!, 1)
    private val mDbOpenHelper: DbOpenHelper? = DbOpenHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         *Obtain the FirebaseAnalytics.
         */
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        mDbOpenHelper?.open()
        mDbOpenHelper?.create()

        showDatabase()

        /**
         *Memo作成ボタンを押すと、CreateActivityに移動
         */
        findViewById<FloatingActionButton>(R.id.create).setOnClickListener {
            val changeCreate = Intent(
                this,
                CreateActivity::class.java
            )
            startActivityForResult(
                changeCreate,
                1
            )
        }

        /**
         *RecyclerView適用：RecyclerViewで核心に必要なもの
         */
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(
            this,
            memoInfoArrayList
        )  //thisは class それだけでなく adapterも this
        mRecyclerView.adapter = viewAdapter  //これがないと何も出ない。（이게 없으면 아무것도 나오지 않는다.）

    }



    /**
     * RecyclerViewのlistを押すの場合、呼ぶ
     */
    override fun onItemClick(memoNumber: Int, title: String, message: String) {
        val moveToReadIntent = Intent(
            this,
            ReadActivity::class.java
        )
        // Toast.makeText(this,"title: "+title +"message: "+message,Toast.LENGTH_LONG).show()
        moveToReadIntent
            .putExtra(
                "memoNumber",
                memoNumber
            )
        moveToReadIntent
            .putExtra(
                "title",
                title
            )
        moveToReadIntent
            .putExtra(
                "message",
                message
            )
        startActivityForResult(
            moveToReadIntent,
            2
        )
    }

    /**
     * Intentの結果を処理
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super
            .onActivityResult(
                requestCode,
                resultCode,
                data
            )

        /**
        Memo作成の結果を処理
         */
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                ++memoListNumber //Memoの別個人番号(削除と変更のため)
                val title = data?.getStringExtra("title") ?: ""
                val message = data?.getStringExtra("message") ?: ""

                val memo = MemoInfo(
                    memoListNumber,
                    title,
                    message
                )

                memoInfoArrayList.add(memo) //LISTにも ?を追加しなければならない

                mDbOpenHelper?.open()
                mDbOpenHelper?.insertColumn(memoListNumber,title,message)

            }
        }

        /**
        Memo削除・修正の結果を処理
         */
        if (requestCode == 2) {
            when (resultCode) {
                MemoConst.RESULT_DELETE -> {
                    val deleteTitle = data?.getStringExtra("deleteTitle") ?: ""  //null 許可しません。
                    val deleteMessage = data?.getStringExtra("deleteMessage") ?: "" // null　許可しません。
                    val deleteNumber = data?.getIntExtra(
                        "deleteNumber",
                        0
                    ) ?: 0 //null 許可しません。
                    val deleteIndex = memoInfoArrayList
                        .indexOf(
                            MemoInfo(
                                deleteNumber,
                                deleteTitle,
                                deleteMessage
                            )
                        )
                    Log
                        .d(
                            "delete",
                            "deleteNumber:$deleteNumber deleteTitle: $deleteTitle deleteMessage: $deleteMessage deleteIndex:$deleteIndex"
                        )
                    memoInfoArrayList.removeAt(deleteIndex)

                    mDbOpenHelper?.deleteColumn(deleteNumber)
                    Toast.makeText(this@MainActivity, "Dataを削除しました。", Toast.LENGTH_SHORT).show()
                }
                MemoConst.RESULT_RENEW -> {
                    val renewNumber = data?.getIntExtra(
                        "renewNumber",
                        0
                    ) ?: 0 //null 許可しません。

                    val pastTitle = data?.getStringExtra("pastTitle") ?: ""
                    val pastMessage = data?.getStringExtra("pastMessage") ?: ""

                    val renewTitle = data?.getStringExtra("renewTitle") ?: ""
                    val renewMessage = data?.getStringExtra("renewMessage") ?: ""

                    val renewIndex = memoInfoArrayList
                        .indexOf(
                            MemoInfo(
                                renewNumber,
                                pastTitle,
                                pastMessage
                            )
                        )
                    Log
                        .d(
                            "renew",
                            "renewNumber:$renewNumber renewMessage: $renewMessage passMessage: $pastMessage renewIndex:$renewIndex"
                        )
                    memoInfoArrayList[renewIndex] = MemoInfo(
                        renewNumber,
                        renewTitle,
                        renewMessage
                    )

                    mDbOpenHelper?.updateColumn(renewNumber,renewTitle,renewMessage)
                    Toast.makeText(this@MainActivity, "Dataを修正しました。.", Toast.LENGTH_SHORT).show()


                    viewAdapter?.notifyDataSetChanged()

                }
            }
        }
    }


    private fun showDatabase() {
        var iCursor = mDbOpenHelper?.sortColumn()

        if (iCursor != null) {
            while (iCursor.moveToNext()) {
                var memoNumber= iCursor.getString(iCursor.getColumnIndex("memoNumber"))
                var title = iCursor.getString(iCursor.getColumnIndex("title"))
                var message = iCursor.getString(iCursor.getColumnIndex("message"))

                val memo = MemoInfo(memoNumber.toInt(),title,message)
                memoInfoArrayList.add(memo)
            }
        }
    }







}
