package vp.app.everyeng.notice

import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import vp.app.everyeng.R
import vp.app.everyeng.base.BaseActivity
import vp.app.everyeng.base.BaseToolbar
import vp.app.everyeng.items.NoticeItem

/**
 * Created by Android on 2017. 12. 20..
 */
class EvEngNoticeWriteActivity: BaseActivity() {

    private val toolbar: BaseToolbar by lazy { findViewById<BaseToolbar>(R.id.toolbar) }

    private val subject: EditText by lazy { findViewById<EditText>(R.id.notice_subject) }
    private val message: EditText by lazy { findViewById<EditText>(R.id.notice_message) }
    private val uploadButton: Button by lazy { findViewById<Button>(R.id.upload_button) }

    override fun getResource(): Int {
        return R.layout.activity_eveng_notice_write_layout
    }

    override fun setInitialize() {

        uploadButton.setOnClickListener {
            uploadNotice()
        }
    }

    override fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.layoutType(BaseToolbar.VIEW_TYPE_COMMON)
        toolbar.setTitleCommonName("공지 사항 작성하기")
    }

    fun uploadNotice(){

        val noticeItem: NoticeItem = NoticeItem()
        noticeItem.title = subject.text.toString()
        noticeItem.message = message.text.toString()
        noticeItem.date = getNowDate()

        try{

            val key = getDatabaseReference().child(NOTICE_INFO).push().key
            val values: HashMap<String, Any> = hashMapOf()
            values.put("/NOTICE/"+key, noticeItem)
            getDatabaseReference().updateChildren(values)
            getDatabaseReference().addListenerForSingleValueEvent( object : ValueEventListener {
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