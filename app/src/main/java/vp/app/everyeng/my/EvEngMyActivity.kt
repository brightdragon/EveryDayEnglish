package vp.app.everyeng.my

import android.speech.tts.TextToSpeech
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.RelativeLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import vp.app.everyeng.R
import vp.app.everyeng.base.BaseActivity
import vp.app.everyeng.base.BaseToolbar
import vp.app.everyeng.db.MySaveDBHelper
import vp.app.everyeng.interfaces.ISelectListener
import vp.app.everyeng.main.item.EnglishItem
import vp.app.everyeng.my.adapter.MyPageAdapter
import java.util.*

/**
 * Created by Android on 2017. 12. 21..
 */
class EvEngMyActivity: BaseActivity() {

    private val toolbar: BaseToolbar by lazy { findViewById<BaseToolbar>(R.id.toolbar) }
    private val leftTitle: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.title_left) }

    private val mypageRecycler: RecyclerView by lazy { findViewById<RecyclerView>(R.id.mypage_recyclerview) }
    private lateinit var myPageAdapter: MyPageAdapter

    lateinit var mAdView: AdView

    // TextToSpeech
    private lateinit var mTextToSpeech: TextToSpeech

    override fun getResource(): Int {
        return R.layout.activity_eveng_mypage_layout
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

        myPageAdapter = MyPageAdapter(this@EvEngMyActivity)
        myPageAdapter.setListener(object : ISelectListener{
            override fun onSelect(item: EnglishItem) {
                val utteranceId = this.hashCode().toString() + ""
                mTextToSpeech.speak(item.wordEnglish.toString(), TextToSpeech.QUEUE_FLUSH, null, utteranceId)
            }
        })

        mypageRecycler.layoutManager = LinearLayoutManager(this@EvEngMyActivity)
        mypageRecycler.isNestedScrollingEnabled = false
        mypageRecycler.setHasFixedSize(false)
        mypageRecycler.adapter = myPageAdapter

        val dbSave: MySaveDBHelper = MySaveDBHelper(this, "ev_eng.db", null, 1)
        val saveList = dbSave.getDBResult()

        Collections.sort<EnglishItem>(saveList) {
            engItem, t1 -> t1.date.compareTo(engItem.date)
        }

        myPageAdapter.setList(saveList)

        leftTitle.setOnClickListener {
            finish()
        }
    }

    override fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.layoutType(BaseToolbar.VIEW_TYPE_COMMON)
        toolbar.setTitleCommonName(getString(R.string.review_saved_word))
    }

    override fun onStop() {
        super.onStop()
        mTextToSpeech.stop()
        mTextToSpeech.shutdown()
    }

    override fun onResume() {
        super.onResume()
        mTextToSpeech = TextToSpeech(this, object : TextToSpeech.OnInitListener {

            override fun onInit(status: Int) {

                if(status === TextToSpeech.SUCCESS) {

                }else {

                }
            }
        })

        mTextToSpeech.isLanguageAvailable(Locale.US)
        mTextToSpeech.language = Locale.US
    }
}