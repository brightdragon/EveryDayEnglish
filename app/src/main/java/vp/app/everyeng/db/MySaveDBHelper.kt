package vp.app.everyeng.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import vp.app.everyeng.main.item.EnglishItem
import java.util.ArrayList

/**
 * Created by Android on 2017. 12. 22..
 */
class MySaveDBHelper(conext: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(conext, name, factory, version) {

    private val TABLE_NAME = "EVENG_MY_ENGLISH"
    private val TODAY_ENG_ONE = "TODAY_ENG_ONE"
    private val TODAY_ENG_TWO = "TODAY_ENG_TWO"
    private val TODAY_ENG_THREE = "TODAY_ENG_THREE"

    override fun onCreate(p0: SQLiteDatabase?) {
        Log.d("TAG", "kypark >> MY DB Helper!!!!! OnCreatE()")

        p0!!.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "WORDENG TEXT, " +
                "WORDKOR TEXT, " +
                "DATE TEXT);")

        p0!!.execSQL("CREATE TABLE " + TODAY_ENG_ONE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "WORD_ENG TEXT, " +
                "WORD_KOR TEXT, " +
                "ENG_01 TEXT, " +
                "ENG_02 TEXT, " +
                "ENG_03 TEXT, " +
                "KOR_01 TEXT, " +
                "KOR_02 TEXT, " +
                "KOR_03 TEXT, " +
                "DATE TEXT);")

        p0!!.execSQL("CREATE TABLE " + TODAY_ENG_TWO + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "WORD_ENG TEXT, " +
                "WORD_KOR TEXT, " +
                "ENG_01 TEXT, " +
                "ENG_02 TEXT, " +
                "ENG_03 TEXT, " +
                "KOR_01 TEXT, " +
                "KOR_02 TEXT, " +
                "KOR_03 TEXT, " +
                "DATE TEXT);")

        p0!!.execSQL("CREATE TABLE " + TODAY_ENG_THREE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "WORD_ENG TEXT, " +
                "WORD_KOR TEXT, " +
                "ENG_01 TEXT, " +
                "ENG_02 TEXT, " +
                "ENG_03 TEXT, " +
                "KOR_01 TEXT, " +
                "KOR_02 TEXT, " +
                "KOR_03 TEXT, " +
                "DATE TEXT);")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS TODAY_ENG_ONE")
        p0!!.execSQL("DROP TABLE IF EXISTS TODAY_ENG_TWO")
        p0!!.execSQL("DROP TABLE IF EXISTS TODAY_ENG_THREE")
        onCreate(p0!!)
    }

    fun insert(wordEng: String, workKor: String, date: String) {
        val db = writableDatabase
        db.execSQL("INSERT INTO $TABLE_NAME VALUES(null, '$wordEng', '$workKor', '$date');")
        db.close()
    }

    fun getDBResult(): ArrayList<EnglishItem> {

        val list = ArrayList<EnglishItem>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + "", null)

        while (cursor.moveToNext()) {
            val item = EnglishItem()
            item.wordEnglish = cursor.getString(1)
            item.workKorea = cursor.getString(2)
            item.date = cursor.getString(3)
            list.add(item)
        }

        return list
    }
}