package com.example.s_kim.memoproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : AppCompatActivity() {
    private var memoListNumber = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        memoListNumber = intent.getIntExtra("memoListNumber", -1) //Memoの別個人番号(削除と変更のため)
        showAndHideKeyboard()//keyBoard 見える・隠す
        save()//新しいMEMO作成（saveボタン）
    }

    /**
     * 新しいMEMO作成（saveボタン）
     */
    private fun save() {
        findViewById<Button>(R.id.save).setOnClickListener {
            val title = createTitle.editableText.toString()
            val message = createMessage.editableText.toString()

            /**
             * 空欄時制限
             */
            if (title.isBlank() || message.isBlank()) {
                Toast
                    .makeText(
                        this,
                        "Write your Text",
                        Toast.LENGTH_SHORT
                    ).show()
                return@setOnClickListener
            }
            ++memoListNumber
            val creatIntent = Intent()  //インテントの前に移動してきたため,あえて中に書かなくてもいい。（인텐트 앞에 이동해 왔기 때문에 굳이 안에 안적어도 된다.）
            creatIntent
                .putExtra(
                    "title",
                    title
                )
            creatIntent
                .putExtra(
                    "message",
                    message
                )
            creatIntent
                .putExtra(
                    "memoListNumber",
                    memoListNumber
                )
            setResult(
                Activity.RESULT_OK,
                creatIntent
            )
            finish()
        }
    }


    /**
     *keyBoard 見える・隠す
     */
    private fun showAndHideKeyboard() {

        var titleSwitch = true
        findViewById<EditText>(R.id.createTitle).setOnClickListener {
            if (titleSwitch) {
                it.hideKeyboard()
                titleSwitch = false
            } else {
                it.showKeyboard(it)
                titleSwitch = true
            }
        }
        var messageSwitch = true
        findViewById<EditText>(R.id.createMessage).setOnClickListener {
            if (messageSwitch) {
                it.hideKeyboard()
                messageSwitch = false
            } else {
                it.showKeyboard(it)
                messageSwitch = true
            }
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm
            .hideSoftInputFromWindow(
                windowToken,
                0
            )
    }

    private fun View.showKeyboard(view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm
            .showSoftInput(
                view,
                0
            )
    }

}
