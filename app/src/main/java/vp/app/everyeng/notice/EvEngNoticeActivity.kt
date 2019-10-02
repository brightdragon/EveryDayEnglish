package vp.app.everyeng.notice

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import vp.app.everyeng.R
import vp.app.everyeng.base.BaseActivity
import vp.app.everyeng.base.BaseToolbar
import vp.app.everyeng.dialog.LoginDialog
import vp.app.everyeng.dialog.ProgressDialog
import vp.app.everyeng.interfaces.ILoginListener
import vp.app.everyeng.items.NoticeItem
import vp.app.everyeng.notice.adapter.NoticeAdapter
import vp.app.everyeng.notice.presenter.NoticeContract
import vp.app.everyeng.notice.presenter.NoticePresenter
import vp.app.everyeng.owner.OwnerActivity
import java.util.*

/**
 * Created by Android on 2017. 12. 18..
 */
class EvEngNoticeActivity : BaseActivity(), NoticeContract.View {


    private val toolbar: BaseToolbar by lazy { findViewById<BaseToolbar>(R.id.toolbar) }
    private val leftTitle: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.title_left) }
    private val rightTitle: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.title_right) }

    private val noticeRecycler: RecyclerView by lazy { findViewById<RecyclerView>(R.id.notice_recyclerview) }
    private var noticeList: ArrayList<NoticeItem> = arrayListOf()
    private var noticeAdapter: NoticeAdapter? = null

    lateinit var mAdView: AdView

    // Presenter
    lateinit var mPresenter: NoticeContract.Presenter

    override fun getResource(): Int {
        return R.layout.activity_eveng_notice_layout
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

        leftTitle.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                finish()
            }
        })

        rightTitle.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {

                LoginDialog.show(this@EvEngNoticeActivity, object : ILoginListener {
                    override fun login(email: String, password: String) {
                        accountLogin(email, password)
                        LoginDialog.hide(this@EvEngNoticeActivity)
                    }
                })
                return false
            }
        })

        noticeAdapter = NoticeAdapter(this@EvEngNoticeActivity)

        noticeRecycler.layoutManager = LinearLayoutManager(this@EvEngNoticeActivity)
        noticeRecycler.isNestedScrollingEnabled = false
        noticeRecycler.setHasFixedSize(false)


        mPresenter = NoticePresenter(this@EvEngNoticeActivity)

        getNoticeList()
    }

    override fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.layoutType(BaseToolbar.VIEW_TYPE_SECRET)
        toolbar.setTitleCommonName("공지 사항")
    }


    fun accountLogin(email: String, password: String) {

        try {
            getFirebaseAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(this@EvEngNoticeActivity, object : OnCompleteListener<AuthResult> {
                override fun onComplete(p0: Task<AuthResult>) {
                    if (p0.isSuccessful) {
                        val intent: Intent = Intent(this@EvEngNoticeActivity, OwnerActivity::class.java)
                        startActivity(intent)
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }

    fun getNoticeList() {

        //ProgressDialog.show(this@EvEngNoticeActivity)

        mPresenter.setProgress(true)

        try {
            getDatabaseReference().child(NOTICE_INFO).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                }

                override fun onDataChange(p0: DataSnapshot?) {

                    if (p0!!.hasChildren()) {
                        for (child: DataSnapshot in p0!!.children) {
                            val serialized: NoticeItem? = child.getValue(NoticeItem::class.java)
                            noticeList.add(serialized!!)
                        }

                        Collections.sort<NoticeItem>(noticeList) { notiItem, t1 ->
                            t1.date.compareTo(notiItem.date)
                        }

                        noticeRecycler.adapter = noticeAdapter
                        noticeAdapter!!.setList(noticeList)
                    }
                    
                    mPresenter.setProgress(false)

                }
            })
        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    override fun setPresenter(presenter: NoticeContract.Presenter) {
        mPresenter = presenter
    }

    override fun showProgress() {
        ProgressDialog.show(this@EvEngNoticeActivity)
    }

    override fun hideProgress() {
        ProgressDialog.hide(this@EvEngNoticeActivity)
    }

}