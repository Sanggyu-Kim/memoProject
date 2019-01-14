package com.example.s_kim.memoproject


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase




class ReadActivity : AppCompatActivity() {
    private var title: String? = null
    private var message: String? = null
    private var memoNumber: Int? = 0
    private var mToast: Toast? = null
    private var etText:EditText? = null


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var testDataMemo = mutableListOf<String>() //listでData登録


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        intent() //setting intentで貰ったもの

        testDataMemo.add(0,"a")
        testDataMemo.add(1,"b")
        testDataMemo.add(2,"c")

        recyclerViewMain()//RecyclerView適用：RecyclerViewで核心に必要なもの


        findViewById<Button>(R.id.btnWrite).setOnClickListener {
            etText = findViewById(R.id.etText)
            var text =etText?.text.toString()

            if (text.isBlank()){
                showToast("Write your message")
            }else{
                // Write a message to the database
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference(title+memoNumber)
                myRef.setValue(text)
                showToast(text)
            }

        }



    }

    /**
     * setting intentで貰ったもの
     */
    private fun intent(){
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
        viewManager = LinearLayoutManager(this)
        viewAdapter = ReadAdapter(testDataMemo)

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
