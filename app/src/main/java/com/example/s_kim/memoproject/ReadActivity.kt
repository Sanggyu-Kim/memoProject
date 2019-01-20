package com.example.s_kim.memoproject


import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

import java.text.SimpleDateFormat
import com.google.firebase.database.DatabaseError
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.firebase.database.DataSnapshot
import android.content.Context
import android.widget.*
import android.content.DialogInterface


class ReadActivity : AppCompatActivity() {
    private var title: String? = null
    private var message: String? = null
    private var memoNumber: Int? = 0
    private var mToast: Toast? = null
    private var etText: EditText? = null
    private var imm: InputMethodManager? = null
    private var color: String? = "black"
    private var chatMessage: TextView? = null
    private var switch: Boolean = true
    private var blank: RelativeLayout? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var mChat = mutableListOf<ChatInfo>() //listでData登録
    private var database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        intent() //setting intentで貰ったもの

        imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        showAndHideKeyboard()//keyBoard 見える・隠す
        rodioButton()
        write() // write Message (style:chat)
        recyclerView.scrollToPosition(mChat.size - 1) //View a recentMessage

    }


    private fun rodioButton() {
        if (switch) {
            recyclerViewMain()//RecyclerView適用：RecyclerViewで核心に必要なもの  // get Message
        }
        findViewById<RadioButton>(R.id.black).setOnClickListener {
            color = "black"
            recyclerViewMain()
            switch = false
        }
        findViewById<RadioButton>(R.id.blue).setOnClickListener {
            color = "blue"
            recyclerViewMain()
            switch = false
        }

        findViewById<RadioButton>(R.id.red).setOnClickListener {
            color = "red"
            recyclerViewMain()
            switch = false
        }


    }


    /**
     * get Message (style:chat)
     */
    private fun getData() {
        val myRef: DatabaseReference = database.getReference(memoNumber.toString() + title)

        myRef.addChildEventListener(
            object : ChildEventListener {
                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    val chat: ChatInfo? = p0.getValue(ChatInfo::class.java)

                    Log.d("special", "p0.getValue:${p0.value}")
                    chat?.let {
                        mChat.add(it)
                        recyclerView.scrollToPosition(mChat.size - 1)
                    }
                    viewAdapter.notifyItemInserted(mChat.size - 1)
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })


    }

    /**
     * write Message (style:chat)
     */
    private fun write() {
        findViewById<Button>(R.id.btnWrite).setOnClickListener {
            etText = findViewById(R.id.etText)
            var text = etText?.text.toString()

            if (text.isBlank()) {
                showToast("Write your message")
            } else {
                // Write a message to the database

                val c = Calendar.getInstance().time
                println("Current time => $c")

                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val formattedDate = df.format(c)


                val myRef = database.getReference(memoNumber.toString() + title).child(formattedDate)

//                var chat2: java.util.HashMap<String,String>?=null
//                chat2?.put("memoNumber",memoNumber.toString())
//                chat2?.put("title",title.toString())
//                chat2?.put("text",text)
                var chat = ChatInfo(color, memoNumber?.toInt() ?: 0, title.toString() ?: "", text)

                myRef.setValue(chat)
                //showToast(chat?.toString())
                etText?.text?.clear()
            }

        }

    }

    /**
     * setting intentで貰ったもの
     */
    private fun intent() {
        title = intent.getStringExtra("title")
        message = intent.getStringExtra("message")
        memoNumber = intent
            .getIntExtra(
                "memoNumber",
                0
            )
    }


    /**
     *RecyclerView適用：RecyclerViewで核心に必要なもの
     */
    private fun recyclerViewMain() {
        mChat.clear()
        getData()
        viewManager = LinearLayoutManager(this)
        viewAdapter = ReadAdapter(mChat, color ?: "yellow")

        recyclerView = findViewById<RecyclerView>(R.id.readRecycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }


    }


    /**
    //     *keyBoard 見える・隠す
    //     */
    private fun showAndHideKeyboard() {
        imm!!
            .hideSoftInputFromWindow(
                etText?.windowToken,
                0
            )
        var etTextSwitch = true
        findViewById<EditText>(R.id.etText).setOnClickListener {
            etTextSwitch = if (etTextSwitch) {
                it.hideKeyboard()
                recyclerView.scrollToPosition(mChat.size - 1)
                false
            } else {
                it.showKeyboard(it)

                // keyboard 어떻게 하지?? 키보드가 켜지면 리사이클러뷰가 자동으로 수정

                recyclerView.scrollToPosition(mChat.size - 1)
                true
            }
        }


    }

    private fun View.hideKeyboard() {
        imm!!
            .hideSoftInputFromWindow(
                windowToken,
                0
            )
    }

    private fun View.showKeyboard(view: View) {
        imm!!
            .showSoftInput(
                view,
                0
            )
    }


    /**
     * Toast
     */
    private fun showToast(msg: String) {
        if (mToast != null) mToast?.cancel()

        mToast = Toast
            .makeText(
                this@ReadActivity,
                msg,
                Toast.LENGTH_SHORT
            )
        mToast?.show()

    }


    /**
     * 단말기 기기 백키
     *
     *  activity가 3개 있으니깐
     *  moveTaskToBack(true) finish() android.os.Process.killProcess(android.os.Process.myPid())
     *  해야 종료된다.
     */
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("APPを終了しますか")
        builder.setMessage("Are you sure you want to exit?").setPositiveButton("YES") { dialog, id ->
            moveTaskToBack(true)
            finish()
            android.os.Process.killProcess(android.os.Process.myPid())
        }.setNegativeButton("NO") { dialog, id -> }
        builder.show()
    }
}

