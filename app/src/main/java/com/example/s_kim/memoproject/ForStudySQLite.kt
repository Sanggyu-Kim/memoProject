package com.example.s_kim.memoproject

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper// DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    (context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    // 읽기가 가능하게 DB 열기
    // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
    var memoInfoArrayList = mutableListOf<MemoInfo>()
        get() {
            val db = readableDatabase
            val cursor = db.rawQuery("SELECT * FROM MEMOLIST", null)
            while (cursor.moveToNext()) {
                var memo= MemoInfo(cursor.getInt(0),cursor.getString(1),cursor.getString(2))
                memoInfoArrayList.add(memo)
            }

            return memoInfoArrayList
        }

    // DB를 새로 생성할 때 호출되는 함수
    override fun onCreate(db: SQLiteDatabase) {
        // 새로운 테이블 생성
        /* 이름은 MEMOLIST이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL(
            "CREATE TABLE MEMOLIST(" +
                    "memoNumber INTEGER PRIMARY KEY AUTOINCREMENT" +
                    ",title TEXT" +
                    ",message TEXT);"
        )
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun insert(memoNumber: Int, title: String, message: String) {
        // 읽고 쓰기가 가능하게 DB 열기
        val db = writableDatabase
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO MEMOLIST VALUES(memoNumber, '$title', $message');")
        db.close()
    }

    fun update(memoNumber: Int, title: String) {
        val db = writableDatabase
        // 입력한 항목과 일치하는 행의 정보 수정
        db.execSQL("UPDATE MEMOLIST SET title=$title WHERE memoNumber='$memoNumber';")
        db.close()
    }

    fun delete(memoNumber: Int) {
        val db = writableDatabase
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM MEMOLIST WHERE memoNumber='$memoNumber';")
        db.close()
    }
}


