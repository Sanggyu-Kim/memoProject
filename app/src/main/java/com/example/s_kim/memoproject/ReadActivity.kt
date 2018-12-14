package com.example.s_kim.memoproject

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_read.*

class ReadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        val title: String = intent.getStringExtra("title")
        val message: String = intent.getStringExtra("message")
        val memoNumber: Int = intent.getIntExtra("memoNumber", 0)


        val renewTitle = readTitle.setText(title)
        val renewMessage =readMessage.setText(message)



        findViewById<Button>(R.id.delete).setOnClickListener {
            //삭제
            val deleteIntent = Intent(this, MainActivity::class.java)
            deleteIntent.putExtra("deleteTitle", title)
            deleteIntent.putExtra("deleteMessage", message)
            deleteIntent.putExtra("deleteNumber", memoNumber)
            setResult(MemoConst.RESULT_DELETE, deleteIntent)  //
            finish()
        }



        findViewById<Button>(R.id.renew).setOnClickListener {
            //수정
            val renewIntent = Intent(this, MainActivity::class.java)
            renewIntent.putExtra("pastTitle", title)
            renewIntent.putExtra("pastMessage", message)
            renewIntent.putExtra("renewTitle", readTitle.text.toString())
            renewIntent.putExtra("renewMessage", readMessage.text.toString())
            renewIntent.putExtra("renewNumber", memoNumber)
            setResult(MemoConst.RESULT_RENEW,renewIntent)
            finish()
        }


    }
}
