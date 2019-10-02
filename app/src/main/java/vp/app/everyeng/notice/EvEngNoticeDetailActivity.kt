package vp.app.everyeng.notice

import android.content.Intent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import vp.app.everyeng.R
import vp.app.everyeng.base.BaseActivity
import vp.app.everyeng.base.BaseToolbar
import vp.app.everyeng.dialog.LoginDialog
import vp.app.everyeng.interfaces.ILoginListener
import vp.app.everyeng.items.NoticeItem

/**
 * Created by Android on 2017. 12. 20..
 */
class EvEngNoticeDetailActivity: BaseActivity(){

    private val toolbar: BaseToolbar by lazy { findViewById<BaseToolbar>(R.id.toolbar) }
    private val leftTitle: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.title_left) }

    private var mIntent: Intent? = null
    private val title: TextView by lazy { findViewById<TextView>(R.id.title) }
    private val content: TextView by lazy { findViewById<TextView>(R.id.content) }
    private val date: TextView by lazy { findViewById<TextView>(R.id.date) }

    lateinit var mAdView: AdView

    override fun getResource(): Int {
        return R.layout.activity_eveng_notice_detail_layout
    }

    override fun setInitialize() {

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        }

        mIntent = intent
        if(mIntent!!.hasExtra("NOTICE_ITEM")){
            val mItem: NoticeItem = mIntent!!.extras!!.getSerializable("NOTICE_ITEM") as NoticeItem
            title.text = mItem.title
            content.text = mItem.message
            date.text = mItem.date
        }

        leftTitle.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                finish()
            }
        })
    }

    override fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.layoutType(BaseToolbar.VIEW_TYPE_COMMON)
        toolbar.setTitleCommonName("공지 사항 보기")
    }
}