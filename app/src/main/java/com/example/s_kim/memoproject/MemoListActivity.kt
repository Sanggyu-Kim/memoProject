package com.example.s_kim.memoproject

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.widget.Toast

class MemoListActivity : AppCompatActivity(), MainAdapter.ClickRead {
    private lateinit var mRecyclerView: RecyclerView
    private var viewAdapter: MainAdapter? = null
    private var mToast: Toast? = null
    private var memoInfoArrayList = mutableListOf<MemoInfo>() //listでData登録
    private val mDbOpenHelper: DbOpenHelper? = DbOpenHelper(this) //data


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memolist)

        sqlLiteData()//Memo 内部データと連結
        create() //Memo作成ボタンを押すと、CreateActivityに移動
        deleteAll()//Memo全部削除
        recyclerViewMain()//RecyclerView適用：RecyclerViewで核心に必要なもの
        swipeDelete()//memoListからswipeで削除するアクション適用
    }


    /**
     *RecyclerView適用：RecyclerViewで核心に必要なもの
     */
    private fun recyclerViewMain() {
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        viewAdapter = MainAdapter(
            this,
            memoInfoArrayList
        )  //thisは class それだけでなく adapterも this
        mRecyclerView.adapter = viewAdapter  //これがないと何も出ない。（이게 없으면 아무것도 나오지 않는다.）

    }

    /**
     * memoListからswipeで削除するアクション適用
     */
    private fun swipeDelete() {
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    /**
     *Memo 内部データと連結
     */
    private fun sqlLiteData() {
        mDbOpenHelper?.open()
        mDbOpenHelper?.create()
        showDatabase()
    }

    /**
     *Memo全部削除
     */
    private fun deleteAll() {
        findViewById<FloatingActionButton>(R.id.deleteall).setOnClickListener {
            deleteDialog()
        }
    }

    /**
     *Memo作成ボタンを押すと、CreateActivityに移動
     */
    private fun create() {
        findViewById<FloatingActionButton>(R.id.create).setOnClickListener {
            val changeCreate = Intent(
                this,
                CreateActivity::class.java
            )

            changeCreate
                .putExtra(
                    "memoListNumber",
                    countData()
                )

            startActivityForResult(
                changeCreate,
                1
            )
        }
    }

    /**
     * RecyclerViewのlistを押すの場合、呼ぶ
     */
    override fun onItemClickedAction(memoNumber: Int, title: String, message: String) {

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
                // memoListNumber = //Memoの別個人番号(削除と変更のため)
                val title = data?.getStringExtra("title") ?: ""
                val message = data?.getStringExtra("message") ?: ""
                val memoListNumber = data?.getIntExtra(
                    "memoListNumber",
                    0
                ) ?: 0
                val memo = MemoInfo(
                    memoListNumber,
                    title,
                    message
                )

                memoInfoArrayList.add(memo) //LISTにも ?を追加しなければならない

                mDbOpenHelper?.open()
                mDbOpenHelper
                    ?.insertColumn(
                        memoListNumber,
                        title,
                        message
                    )

                viewAdapter?.notifyDataSetChanged()
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
                    memoInfoArrayList
                        .remove(
                            MemoInfo(
                                deleteNumber,
                                deleteTitle,
                                deleteMessage
                            )
                        )

                    mDbOpenHelper?.deleteColumn(deleteNumber)


                    Toast
                        .makeText(
                            this@MemoListActivity,
                            "Dataを削除しました。",
                            Toast.LENGTH_SHORT
                        ).show()

                    viewAdapter?.notifyDataSetChanged()
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

                    mDbOpenHelper
                        ?.updateColumn(
                            renewNumber,
                            renewTitle,
                            renewMessage
                        )
                    Toast
                        .makeText(
                            this@MemoListActivity,
                            "Dataを修正しました。.",
                            Toast.LENGTH_SHORT
                        ).show()

                    viewAdapter?.notifyDataSetChanged()

                }
            }
        }
    }

    /**
    Memo 内部データと連結して,以前に内部データを受け取りリストに適用
     */
    private fun showDatabase() {
        var iCursor = mDbOpenHelper?.sortColumn()
        if (iCursor != null) {
            while (iCursor.moveToNext()) {
                var memoNumber = iCursor.getString(iCursor.getColumnIndex("memoNumber"))
                var title = iCursor.getString(iCursor.getColumnIndex("title"))
                var message = iCursor.getString(iCursor.getColumnIndex("message"))

                val memo = MemoInfo(
                    memoNumber.toInt(),
                    title,
                    message
                )
                memoInfoArrayList.add(memo)
            }
        }
    }

    /**
    Memo 内部データと連結し,以前に内部データの個数をreturnする。
     */
    private fun countData(): Int {
        var iCursor = mDbOpenHelper?.sortColumn()
        var count = 0
        if (iCursor != null) {
            while (iCursor.moveToNext()) {
                count = iCursor.getString(iCursor.getColumnIndex("memoNumber")).toInt()
            }
        }
        return count
    }

    /**
    Memo 全体削除ボタンをクリックするときにダイアログを表示させる。
     */
    private fun deleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("全部削除")
        builder.setMessage("全部削除しても大丈夫ですか").setPositiveButton("YES") { dialog, id ->
            mDbOpenHelper?.deleteAllColumns()
            Toast
                .makeText(
                    this,
                    "全部削除",
                    Toast.LENGTH_SHORT
                ).show()
            memoInfoArrayList.clear()   //
            viewAdapter?.notifyDataSetChanged()
        }.setNegativeButton("NO") { dialog, id ->
        }
        builder.show()
    }

    /**
     * memoListからswipeで削除するアクション適用
     */
    private var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            showToast("on Move")
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            // 削除されるitemのpositionを持ってくる
            val position = viewHolder.adapterPosition

            showToast("position: $position, number: ${memoInfoArrayList[position].memoNumber}")
            Log.d(
                "onSwipe",
                "position: $position, number: ${memoInfoArrayList[position].memoNumber}"
            )

            mDbOpenHelper?.deleteColumn(memoInfoArrayList[position].memoNumber)   //dataを削除してからリストを削除する＿error_indexOutOfBoundException
            memoInfoArrayList.removeAt(position)
            viewAdapter?.notifyItemRemoved(position)
        }
    }

    /**
     * Toast
     */
    private fun showToast(msg: String) {
        if (mToast != null) mToast?.cancel()

        mToast = Toast
            .makeText(
                this@MemoListActivity,
                msg,
                Toast.LENGTH_SHORT
            )
        mToast?.show()

    }

}
