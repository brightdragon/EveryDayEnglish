package vp.app.everyeng.main

import android.content.Intent
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.CardView
import android.util.Log
import android.view.View
import android.widget.*
import com.example.android.notificationchannels.NotificationHelper
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import vp.app.everyeng.R
import vp.app.everyeng.base.BaseActivity
import vp.app.everyeng.base.BaseToolbar
import vp.app.everyeng.db.MySaveDBHelper
import vp.app.everyeng.dialog.HowToDialog
import vp.app.everyeng.dialog.ProgressDialog
import vp.app.everyeng.main.data.MainRepository
import vp.app.everyeng.main.item.EnglishItem
import vp.app.everyeng.main.presenter.MainContract
import vp.app.everyeng.main.presenter.MainPresenter
import vp.app.everyeng.my.EvEngMyActivity
import vp.app.everyeng.setting.EvEngSettingActivity
import vp.app.everyeng.setting.EvEngVoiceSettingActivity
import vp.app.everyeng.util.Pref
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Android on 2017. 12. 18..
 */
class EvEngMainActivity : BaseActivity(), View.OnClickListener, MainContract.View {

    private val toolbar: BaseToolbar by lazy { findViewById<BaseToolbar>(R.id.toolbar) }

    private val voiceLayout: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.voice_layout) }
    private val helpLayout: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.help_layout) }
    private val settingLayout: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.setting_layout) }
    private val reviewLayout: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.review_layout) }
    private val readWord: FloatingActionButton by lazy { findViewById<FloatingActionButton>(R.id.read_word_button) }
    private val saveButton: FloatingActionButton by lazy { findViewById<FloatingActionButton>(R.id.save_fab_button) }
    private val cvsEng01: TextView by lazy { findViewById<TextView>(R.id.cvs_eng_01) }
    private val cvsEng02: TextView by lazy { findViewById<TextView>(R.id.cvs_eng_02) }
    private val cvsEng03: TextView by lazy { findViewById<TextView>(R.id.cvs_eng_03) }
    private val cvsKor01: TextView by lazy { findViewById<TextView>(R.id.cvs_kor_01) }
    private val cvsKor02: TextView by lazy { findViewById<TextView>(R.id.cvs_kor_02) }
    private val cvsKor03: TextView by lazy { findViewById<TextView>(R.id.cvs_kor_03) }
    private val speechBtn01: CardView by lazy { findViewById<CardView>(R.id.speech__button_01) }
    private val speechBtn02: CardView by lazy { findViewById<CardView>(R.id.speech__button_02) }
    private val speechBtn03: CardView by lazy { findViewById<CardView>(R.id.speech__button_03) }

    private lateinit var mTextToSpeech: TextToSpeech        // TextToSpeech
    private lateinit var mAdView: AdView                    // admob
    private lateinit var mPresenter: MainContract.Presenter // presenter
    private var mEnglishItem: EnglishItem? = null

    override fun getResource(): Int {
        return R.layout.activity_eveng_main_activity
    }

    override fun setInitialize() {
        mPresenter = MainPresenter(this@EvEngMainActivity)
        mPresenter.setDataRepository(MainRepository)
        mPresenter.setProgress(true)


        settingLayout.setOnClickListener(this@EvEngMainActivity)
        helpLayout.setOnClickListener(this@EvEngMainActivity)
        reviewLayout.setOnClickListener(this@EvEngMainActivity)
        voiceLayout.setOnClickListener(this@EvEngMainActivity)
        readWord.setOnClickListener(this@EvEngMainActivity)
        saveButton.setOnClickListener(this@EvEngMainActivity)

        speechBtn01.setOnClickListener(this@EvEngMainActivity)
        speechBtn02.setOnClickListener(this@EvEngMainActivity)
        speechBtn03.setOnClickListener(this@EvEngMainActivity)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {

            }

            override fun onAdFailedToLoad(errorCode: Int) {

            }

            override fun onAdOpened() {

            }

            override fun onAdLeftApplication() {

            }

            override fun onAdClosed() {

            }
        }


        if (equalsDate()) {

        } else {
            mPresenter.setEnlgishItem()
        }


        val isFirstWelcom: Boolean = Pref.load(this@EvEngMainActivity, Pref.BOOL_FIRST_WELCOM) as Boolean
        if (isFirstWelcom) {

        } else {
            HowToDialog.show(this@EvEngMainActivity)
//            mPresenter.setEnlgishItem()
        }

    }

    override fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.layoutType(BaseToolbar.VIEW_TYPE_MAIN)
    }

    override fun onClick(p0: View?) {

        when (p0?.id) {
            R.id.setting_layout -> {
                mPresenter.setStartActivity(1)
            }

            R.id.help_layout -> {
                HowToDialog.show(this@EvEngMainActivity)
            }

            R.id.review_layout -> {
                mPresenter.setStartActivity(2);
            }

            R.id.voice_layout -> {
                mPresenter.setStartActivity(3);
            }

            R.id.save_fab_button -> {
                val dbSave: MySaveDBHelper = MySaveDBHelper(this, "ev_eng.db", null, 1)
                val saveList = dbSave.getDBResult()
                saveList.forEach {
                    if (mEnglishItem!!.wordEnglish == it.wordEnglish) {
                        Toast.makeText(this, getString(R.string.already_saved_word), Toast.LENGTH_SHORT).show()
                        return
                    }
                }

                dbSave.insert(mEnglishItem!!.wordEnglish, mEnglishItem!!.workKorea, mEnglishItem!!.date)
                Toast.makeText(this, getString(R.string.saved_word), Toast.LENGTH_SHORT).show()
            }

            R.id.read_word_button -> {
                setSpeech(mEnglishItem!!.wordEnglish.toString())
            }
            R.id.speech__button_01 -> {
                setSpeech(mEnglishItem!!.eng01.toString())
            }

            R.id.speech__button_02 -> {
                setSpeech(mEnglishItem!!.eng02.toString())
            }

            R.id.speech__button_03 -> {
                setSpeech(mEnglishItem!!.eng03.toString())
            }
        }
    }

    private fun setSpeech(text: String) {
        val utteranceId = this.hashCode().toString() + ""
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        mPresenter = presenter
    }

    override fun showToastMessage(message: String) {
        Toast.makeText(this@EvEngMainActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        ProgressDialog.show(this@EvEngMainActivity)
    }

    override fun hideProgress() {
        val mHandler: Handler = Handler()
        mHandler.postDelayed({
            ProgressDialog.hide(this@EvEngMainActivity)
        }, 500)
    }

    override fun showActivity(type: Int) {

        when (type) {
            1 -> {
                val intent: Intent = Intent(this@EvEngMainActivity, EvEngSettingActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                val intent: Intent = Intent(this@EvEngMainActivity, EvEngMyActivity::class.java)
                startActivity(intent)
            }

            3 -> {
                val intent: Intent = Intent(this@EvEngMainActivity, EvEngVoiceSettingActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun showEnglishItem(item: EnglishItem) {

        mEnglishItem = item

        toolbar.setTitleEnglishCount(item.index)
        toolbar.setWordEnglish(item.wordEnglish)
        toolbar.setWordKorea(item.workKorea)
        cvsEng01.text = item.eng01
        cvsEng02.text = item.eng02
        cvsEng03.text = item.eng03
        cvsKor01.text = item.kr01
        cvsKor02.text = item.kr02
        cvsKor03.text = item.kr03

        speechBtn01.visibility = View.VISIBLE
        speechBtn02.visibility = View.VISIBLE
        speechBtn03.visibility = View.VISIBLE
        mPresenter.setProgress(false)
    }

    override fun onResume() {
        super.onResume()
        mTextToSpeech = TextToSpeech(this, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
            } else {
            }
        })

        mTextToSpeech.isLanguageAvailable(Locale.US)
        mTextToSpeech.language = Locale.US
    }

    override fun onStop() {
        super.onStop()
        mTextToSpeech.stop()
        mTextToSpeech.shutdown()
    }

    fun getYesterDay(): String {
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        val date: String = sdf.format(calendar.time)
        return date
    }

    fun getToday(): String {
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        val calendar: Calendar = Calendar.getInstance()
        val date: String = sdf.format(calendar.time)
        return date
    }

    fun getNowTime(): String {
        return ""
    }

    fun equalsDate(): Boolean {
        try {
//            val now = System.currentTimeMillis()
//            val date: Date = Date(now)
//            val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm aa")
//            val getTime: String = dateFormat.format(date)
//            Log.d("TAG", "kypark >> getTime = " + getTime)

            val today: String = Pref.load(this@EvEngMainActivity, Pref.TODAY) as String
            return (today == getToday())
//            if(today == getToday()) {
//                return true
//            }else {
//                return false
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }
}