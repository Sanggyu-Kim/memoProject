package com.example.s_kim.memoproject

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_read.*

class ReadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        //intentで貰ったもの
        val title: String = intent.getStringExtra("title")
        val message: String = intent.getStringExtra("message")
        val memoNumber: Int = intent.getIntExtra("memoNumber", 0)

        readTitle.setText(title)
        readMessage.setText(message)

        /**
         *keyBoard 見える・隠す
         */
        var titleSwitch = true
        findViewById<EditText>(R.id.readTitle).setOnClickListener {
            if (titleSwitch) {
                it.hideKeyboard()
                titleSwitch = false
            } else {
                it.showKeyboard(it)
                titleSwitch = true
            }
        }

        var messageSwitch = true
        findViewById<EditText>(R.id.readMessage).setOnClickListener {
            if (messageSwitch) {
                it.hideKeyboard()
                messageSwitch = false
            } else {
                it.showKeyboard(it)
                messageSwitch = true
            }
        }


        /**
        Memo 修正・削除
         */
        //修正
        findViewById<Button>(R.id.renew).setOnClickListener {

            // 空欄時制限
            if (readTitle.editableText.isBlank() || readMessage.editableText.isBlank()) {
                Toast.makeText(this, "Write your Text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val renewIntent = Intent(this, MainActivity::class.java)
            renewIntent.putExtra("pastTitle", title)
            renewIntent.putExtra("pastMessage", message)
            renewIntent.putExtra("renewTitle", readTitle.text.toString())
            renewIntent.putExtra("renewMessage", readMessage.text.toString())
            renewIntent.putExtra("renewNumber", memoNumber)
            setResult(MemoConst.RESULT_RENEW, renewIntent)
            finish()
        }
        //削除
        findViewById<Button>(R.id.delete).setOnClickListener {
            val deleteIntent = Intent(this, MainActivity::class.java)
            deleteIntent.putExtra("deleteTitle", title)
            deleteIntent.putExtra("deleteMessage", message)
            deleteIntent.putExtra("deleteNumber", memoNumber)
            setResult(MemoConst.RESULT_DELETE, deleteIntent)  //
            finish()
        }
    }

    /**
     *keyBoard 見える・隠す
     */
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun View.showKeyboard(view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }


}
