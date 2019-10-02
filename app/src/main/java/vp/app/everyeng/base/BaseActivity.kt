package vp.app.everyeng.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import vp.app.everyeng.alram.EvEngAlarm1Receiver
import vp.app.everyeng.alram.EvEngAlarm2Receiver
import vp.app.everyeng.alram.EvEngAlarm3Receiver
import vp.app.everyeng.base.interfaces.ISetView
import vp.app.everyeng.util.Pref
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Android on 2017. 12. 18..
 */
abstract class BaseActivity : AppCompatActivity(), ISetView {

    protected var mFirebaseAuth: FirebaseAuth? = null
    protected var mFirebaseDataBase: FirebaseDatabase? = null
    protected var mDatabaseReference: DatabaseReference? = null

    protected val ENGLISH_INFO: String = "ENGLISH-INFO"
    protected val ENGLISH_FIRST: String = "ENGLISH-FIRST"
    protected val ENGLISH_SECOND: String = "ENGLISH-SECOND"
    protected val ENGLISH_THIRD: String = "ENGLISH-THIRD"
    protected val NOTICE_INFO: String = "NOTICE"
    protected val UPDATE_INFO: String = "UPDATE-INFO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this, "ca-app-pub-8665962795507517~9314379360")

        val isPush: Boolean = Pref.load(this@BaseActivity, Pref.BOOL_PUSH_CHECK) as Boolean
        val evengAlaram1: EvEngAlarm1Receiver = EvEngAlarm1Receiver()
        val evengAlaram2: EvEngAlarm2Receiver = EvEngAlarm2Receiver()
        val evengAlaram3: EvEngAlarm3Receiver = EvEngAlarm3Receiver()

        if (isPush) {
            evengAlaram1.setAlarm(this@BaseActivity)
            evengAlaram2.setAlarm(this@BaseActivity)
            evengAlaram3.setAlarm(this@BaseActivity)
        } else{
            evengAlaram1.cancelAlarm(this@BaseActivity)
            evengAlaram2.cancelAlarm(this@BaseActivity)
            evengAlaram3.cancelAlarm(this@BaseActivity)
        }

        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseDataBase = FirebaseDatabase.getInstance()
        mDatabaseReference = mFirebaseDataBase?.reference

        setContentView(getResource())
        setToolbar()
        setInitialize()
    }

    protected fun getFirebaseAuth(): FirebaseAuth {
        return mFirebaseAuth!!
    }

    protected fun getFireBaseDatabase(): FirebaseDatabase {
        return mFirebaseDataBase!!
    }

    protected fun getDatabaseReference(): DatabaseReference {
        return mDatabaseReference!!
    }

    protected fun getNowDate(): String {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
    }
}