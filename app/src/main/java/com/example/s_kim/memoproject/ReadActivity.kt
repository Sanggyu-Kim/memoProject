package com.example.s_kim.memoproject


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.HashMap

import java.text.SimpleDateFormat
import com.google.firebase.database.DatabaseError
import android.R.attr.author
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.example.s_kim.memoproject.R.id.etText
import com.google.firebase.database.DataSnapshot
import org.w3c.dom.Comment


class ReadActivity : AppCompatActivity() {
    private var title: String? = null
    private var message: String? = null
    private var memoNumber: Int? = 0
    private var mToast: Toast? = null
    private var etText: EditText? = null


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var mChat = mutableListOf<ChatInfo>() //listでData登録
    private var database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        intent() //setting intentで貰ったもの



        write() // write message (style:chat)

        recyclerViewMain()//RecyclerView適用：RecyclerViewで核心に必要なもの





    }


    private fun getData (){
        val myRef:DatabaseReference = database!!.getReference(memoNumber.toString() + title)

        myRef.addChildEventListener(
            object : ChildEventListener {
                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    //val chat:ChatInfo= p0.getValue(ChatInfo::class.java)?:ChatInfo(0,"","")
                    Log.d("special","p0.getValue:${p0.value}")
                    //mChat.add(chat)
                    viewAdapter.notifyItemInserted(mChat.size-1)
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
     * write message (style:chat)
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


                val myRef = database!!.getReference(memoNumber.toString() + title).child(formattedDate)

//                var chat2: java.util.HashMap<String,String>?=null
//                chat2?.put("memoNumber",memoNumber.toString())
//                chat2?.put("title",title.toString())
//                chat2?.put("text",text)
                var chat = ChatInfo(memoNumber?:0,title?:"",text)
                myRef.setValue(chat)
                //showToast(chat?.toString())
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

        getData()
        viewManager = LinearLayoutManager(this)
        viewAdapter = ReadAdapter(mChat)

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


}

