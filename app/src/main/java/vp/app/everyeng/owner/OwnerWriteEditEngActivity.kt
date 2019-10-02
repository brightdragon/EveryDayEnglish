package vp.app.everyeng.owner

import android.content.Intent
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import vp.app.everyeng.R
import vp.app.everyeng.base.BaseActivity
import vp.app.everyeng.base.BaseToolbar
import vp.app.everyeng.main.item.EnglishItem

/**
 * Created by Android on 2017. 12. 18..
 */
class OwnerWriteEditEngActivity: BaseActivity() {

    private var mIntent: Intent? = null
    private var mType: String? = null

    private val toolbar: BaseToolbar by lazy { findViewById<BaseToolbar>(R.id.toolbar) }
    private val uploadBtn: Button by lazy { findViewById<Button>( R.id.upload_button) }
    private val wordEng: EditText by lazy { findViewById<EditText>(R.id.word_eng_edit) }
    private val wordKor: EditText by lazy { findViewById<EditText>(R.id.word_kr_edit) }
    private val cvsEng01: EditText by lazy { findViewById<EditText>(R.id.conversation_eng_edit_01) }
    private val cvsEng02: EditText by lazy { findViewById<EditText>(R.id.conversation_eng_edit_02) }
    private val cvsEng03: EditText by lazy { findViewById<EditText>(R.id.conversation_eng_edit_03) }
    private val cvsKor01: EditText by lazy { findViewById<EditText>(R.id.conversation_kr_edit_01) }
    private val cvsKor02: EditText by lazy { findViewById<EditText>(R.id.conversation_kr_edit_02) }
    private val cvsKor03: EditText by lazy { findViewById<EditText>(R.id.conversation_kr_edit_03) }

    override fun getResource(): Int {
        return R.layout.activity_owner_edit_layout
    }

    override fun setInitialize() {

        mIntent = intent

        if(mIntent != null){
            mType = mIntent?.extras?.getString("TYPE")
        }

        uploadBtn.setOnClickListener {
            if(isEmptyNull()){
                setWriteData()
            }
        }
    }

    override fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.layoutType(BaseToolbar.VIEW_TYPE_COMMON)

        if(mType.equals("1")){
            toolbar.setTitleCommonName("첫번째 영어 설정")
        }else if(mType.equals("2")){
            toolbar.setTitleCommonName("두번쨰 영어 설정")
        }else if(mType.equals("3")){
            toolbar.setTitleCommonName("세번째 영어 설정")
        }
    }

    fun isEmptyNull(): Boolean {

        if(TextUtils.isEmpty(wordEng.text.toString())){ return false }
        if(TextUtils.isEmpty(wordKor.text.toString())){ return false }
        if(TextUtils.isEmpty(cvsEng01.text.toString())){ return false }
        if(TextUtils.isEmpty(cvsEng02.text.toString())){ return false }
        if(TextUtils.isEmpty(cvsEng03.text.toString())){ return false }
        if(TextUtils.isEmpty(cvsKor01.text.toString())){ return false }
        if(TextUtils.isEmpty(cvsKor02.text.toString())){ return false }
        if(TextUtils.isEmpty(cvsKor03.text.toString())){ return false }

        return true
    }

    fun setWriteData(){

        val engItem: EnglishItem = EnglishItem()
        engItem.index = mType
        engItem.wordEnglish = wordEng.text.toString()
        engItem.workKorea = wordKor.text.toString()
        engItem.eng01 = cvsEng01.text.toString()
        engItem.eng02 = cvsEng02.text.toString()
        engItem.eng03 = cvsEng03.text.toString()
        engItem.kr01 = cvsKor01.text.toString()
        engItem.kr02 = cvsKor02.text.toString()
        engItem.kr03 = cvsKor03.text.toString()
        engItem.isVisible = false
        engItem.date = getNowDate()

        try{
            var ch = if (mType.equals("1")) {
                ENGLISH_FIRST
            }else if(mType.equals("2")) {
                ENGLISH_SECOND
            }else if(mType .equals("3")){
                ENGLISH_THIRD
            }else {
                return
            }

            getDatabaseReference().child(ENGLISH_INFO).child(ch).setValue(engItem)
            getDatabaseReference().addListenerForSingleValueEvent( object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {

                }
            })
        }catch(e: Exception) {
            e.printStackTrace()
        }
    }
}