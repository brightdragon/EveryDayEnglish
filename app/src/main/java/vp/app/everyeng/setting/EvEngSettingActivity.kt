package vp.app.everyeng.setting

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import vp.app.everyeng.R
import vp.app.everyeng.base.BaseActivity
import vp.app.everyeng.base.BaseToolbar
import vp.app.everyeng.notice.EvEngNoticeActivity
import vp.app.everyeng.sponsor.SponsorActivity
import vp.app.everyeng.util.DeviceInfo
import vp.app.everyeng.util.Pref

/**
 * Created by Android on 2017. 12. 18..
 */
    class EvEngSettingActivity : BaseActivity(), View.OnClickListener {

        private val toolbar: BaseToolbar by lazy { findViewById<BaseToolbar>(R.id.toolbar) }
        private val leftTitle: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.title_left) }

        private val noticeLayout: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.notice_layout) }
        private val inquiryLayout: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.inquiry_layout) }
        private val assessmentlayout: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.assessment_layout) }
        private val sponsorLayout: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.sponsor_layout) }
        private val ttxLayout: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.tts_layout) }
        private val appversion: TextView by lazy { findViewById<TextView>(R.id.appversion) }
        private val pushSwitch: Switch by lazy { findViewById<Switch>(R.id.push_switch) }

        private lateinit var mAdView: AdView

        override fun getResource(): Int {
            return R.layout.activity_eveng_setting_layout
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

            val isPushCheck: Boolean = Pref.load(this@EvEngSettingActivity, Pref.BOOL_PUSH_CHECK) as Boolean
            pushSwitch.isChecked = isPushCheck
            noticeLayout.setOnClickListener {
                val intent: Intent = Intent(this@EvEngSettingActivity, EvEngNoticeActivity::class.java)
                startActivity(intent)
            }

            pushSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                Pref.save(this@EvEngSettingActivity, Pref.BOOL_PUSH_CHECK, isChecked)
                pushSwitch.isChecked = isChecked
            }

            appversion.text = DeviceInfo.appVersionName(this@EvEngSettingActivity)
            inquiryLayout.setOnClickListener(this@EvEngSettingActivity)
            assessmentlayout.setOnClickListener(this@EvEngSettingActivity)
            sponsorLayout.setOnClickListener(this@EvEngSettingActivity)
            ttxLayout.setOnClickListener(this@EvEngSettingActivity)
            leftTitle.setOnClickListener(this@EvEngSettingActivity)
        }

        override fun setToolbar() {
            setSupportActionBar(toolbar)
            toolbar.layoutType(BaseToolbar.VIEW_TYPE_COMMON)
            toolbar.setTitleCommonName("설정")
        }

        override fun onClick(p0: View?) {
            when (p0!!.id) {
                R.id.sponsor_layout -> {
                    val intent = Intent(this@EvEngSettingActivity, SponsorActivity::class.java)
                    startActivity(intent)
                }
                R.id.inquiry_layout -> {
                    val email = Intent(Intent.ACTION_SEND)
                    email.type = "plain/text"
                    val address = arrayOf("help.variouspeople@gmail.com")
                    email.putExtra(Intent.EXTRA_EMAIL, address)
                    email.putExtra(Intent.EXTRA_SUBJECT, "[매일 영어]")
                    email.putExtra(Intent.EXTRA_TEXT, " - 문의 내용 : \n")
                    startActivity(email)
                }

                R.id.title_left -> {
                    finish()
                }

                R.id.assessment_layout -> {

                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("market://details?id=vp.app.everyeng")
                    startActivity(intent)
                }

                R.id.tts_layout -> {
                    val intent = Intent(this@EvEngSettingActivity, EvEngVoiceSettingActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }