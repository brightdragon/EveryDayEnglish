package vp.app.everyeng.owner

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import vp.app.everyeng.R
import vp.app.everyeng.base.BaseActivity
import vp.app.everyeng.base.BaseToolbar
import vp.app.everyeng.dialog.ProgressDialog
import vp.app.everyeng.main.item.EnglishItem
import vp.app.everyeng.notice.EvEngNoticeWriteActivity

/**
 * Created by Android on 2017. 12. 18..
 */
class OwnerActivity : BaseActivity(), View.OnClickListener {

    private val toolbar: BaseToolbar by lazy { findViewById<BaseToolbar>(R.id.toolbar) }
    private val firstSetting: ImageView by lazy { findViewById<ImageView>(R.id.first_setting) }
    private val secondSetting: ImageView by lazy { findViewById<ImageView>(R.id.second_setting) }
    private val thirdSetting: ImageView by lazy { findViewById<ImageView>(R.id.third_setting) }
    private val firstWord: TextView by lazy { findViewById<TextView>(R.id.first_word) }
    private val secondWord: TextView by lazy { findViewById<TextView>(R.id.second_word) }
    private val thirdWord: TextView by lazy { findViewById<TextView>(R.id.third_word) }
    private val visible01: TextView by lazy { findViewById<TextView>(R.id.visiblegone_text_01) }
    private val visible02: TextView by lazy { findViewById<TextView>(R.id.visiblegone_text_02) }
    private val visible03: TextView by lazy { findViewById<TextView>(R.id.visiblegone_text_03) }
    private val noticeWrite: Button by lazy { findViewById<Button>(R.id.notice_write_button) }

    private var noticeList: ArrayList<EnglishItem> = arrayListOf()

    override fun getResource(): Int {
        return R.layout.activity_owner_layout
    }

    override fun setInitialize() {
        firstSetting.setOnClickListener(this@OwnerActivity)
        secondSetting.setOnClickListener(this@OwnerActivity)
        thirdSetting.setOnClickListener(this@OwnerActivity)
        noticeWrite.setOnClickListener(this@OwnerActivity)
        visible01.setOnClickListener(this@OwnerActivity)
        visible02.setOnClickListener(this@OwnerActivity)
        visible03.setOnClickListener(this@OwnerActivity)

        getEnglishInfo()
    }

    override fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.layoutType(BaseToolbar.VIEW_TYPE_COMMON)
        toolbar.setTitleCommonName("관리자 페이지")
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.first_setting -> startActivityType("1")
            R.id.second_setting -> startActivityType("2")
            R.id.third_setting -> startActivityType("3")
            R.id.visiblegone_text_01 -> setVisibleGone(0, noticeList!!.get(0).isVisible)
            R.id.visiblegone_text_02 -> setVisibleGone(1, noticeList!!.get(1).isVisible)
            R.id.visiblegone_text_03 -> setVisibleGone(2, noticeList!!.get(2).isVisible)
            R.id.notice_write_button -> {
                val intent: Intent = Intent(this@OwnerActivity, EvEngNoticeWriteActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun startActivityType(type: String) {
        val intent: Intent = Intent(this@OwnerActivity, OwnerWriteEditEngActivity::class.java)
        intent.putExtra("TYPE", type)
        startActivity(intent)
    }

    fun getEnglishInfo() {

        try {
            getDatabaseReference().child(ENGLISH_INFO).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    ProgressDialog.hide(this@OwnerActivity)
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    if (p0!!.hasChildren()) {
                        noticeList = arrayListOf()
                        for (child: DataSnapshot in p0!!.children) {
                            val item: EnglishItem? = child!!.getValue(EnglishItem::class.java)
                            noticeList.add(item!!)
                        }

                        firstWord.text = noticeList!!.get(0).wordEnglish
                        secondWord.text = noticeList!!.get(1).wordEnglish
                        thirdWord.text = noticeList!!.get(2).wordEnglish
                        visibleGone(visible01, noticeList.get(0).isVisible)
                        visibleGone(visible02, noticeList.get(1).isVisible)
                        visibleGone(visible03, noticeList.get(2).isVisible)

                    } else {
                        return
                    }
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun setVisibleGone(type: Int, isVisibleGone: Boolean) {

        try {

            val engItem: EnglishItem = noticeList.get(type)
            engItem.isVisible = !isVisibleGone

            val values: HashMap<String, Any> = hashMapOf()
            if(type == 0){
                values.put("/ENGLISH-INFO/ENGLISH-FIRST/", engItem)
            }else if(type == 1){
                values.put("/ENGLISH-INFO/ENGLISH-SECOND/", engItem)
            }else if(type == 2){
                values.put("/ENGLISH-INFO/ENGLISH-THIRD/", engItem)
            }
            getDatabaseReference().updateChildren(values)
            getDatabaseReference().addValueEventListener( object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {

                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun visibleGone(textView: TextView, isVisible: Boolean) {

        if (isVisible) {
            textView.text = "visible"
            textView.setTextColor(ContextCompat.getColor(this@OwnerActivity, R.color.tomato))
        } else {
            textView.text = "gone"
            textView.setTextColor(ContextCompat.getColor(this@OwnerActivity, R.color.gray))
        }
    }
}