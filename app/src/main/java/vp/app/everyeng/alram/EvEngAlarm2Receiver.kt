package vp.app.everyeng.alram

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.android.notificationchannels.NotificationHelper
import vp.app.everyeng.R
import vp.app.everyeng.main.EvEngMainActivity
import java.util.*

/**
 * Created by Android on 2017. 12. 21..
 */
class EvEngAlarm2Receiver : BroadcastReceiver() {

    private var mAlaramManager: AlarmManager? = null
    private var alaramIntent: PendingIntent? = null

    private lateinit var helper: NotificationHelper

    override fun onReceive(p0: Context?, p1: Intent?) {
        val mThread: Thread = Thread()
        mThread.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                helper = NotificationHelper(p0!!)
                helper.notify(1, helper.getNotification1("[매일 영어]", "새로운 단어와 새로운 회화를 봐야 할 시간이에요!"))
            } else {
                sendNotification(p0!!)
            }
        }

        mThread.start()
    }

    fun sendNotification(context: Context) {

        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val penddingIntent: PendingIntent = PendingIntent.getActivity(context, 0, Intent(context, EvEngMainActivity::class.java), 0)
        val mNotification: NotificationCompat.Builder = NotificationCompat.Builder(context)
        mNotification.setSmallIcon(R.mipmap.eveng_icon)
        mNotification.setContentTitle("[매일 영어]")
        mNotification.setStyle(NotificationCompat.BigTextStyle())
        mNotification.setContentText("새로운 단어와 새로운 회화를 봐야 할 시간이에요!")
        mNotification.setContentIntent(penddingIntent)
        mNotification.setAutoCancel(true)
        mNotificationManager.notify(1, mNotification.build())
    }


    fun setAlarm(context: Context) {

        val mIntent = Intent(context, EvEngAlarm2Receiver::class.java)
        mAlaramManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alaramIntent = PendingIntent.getBroadcast(context, 0, mIntent, 0)

        val mCalendar = Calendar.getInstance()
        mCalendar.timeInMillis = System.currentTimeMillis()
        mCalendar.set(Calendar.HOUR_OF_DAY, 12)
        mCalendar.set(Calendar.MINUTE, 0)

        val diff = Calendar.getInstance().timeInMillis - mCalendar.timeInMillis

        if (diff < 0L) {
            mAlaramManager!!.setInexactRepeating(AlarmManager.RTC_WAKEUP, mCalendar.timeInMillis, AlarmManager.INTERVAL_DAY, alaramIntent!!)
        }

        val receiver = ComponentName(context, EvEngBootReceiver::class.java)
        val pm = context.packageManager
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
    }

    fun cancelAlarm(context: Context) {
        // If the alarm has been set, cancel it.
        if (mAlaramManager != null) {
            mAlaramManager!!.cancel(alaramIntent)
        }

        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
        val receiver = ComponentName(context, EvEngBootReceiver::class.java)
        val pm = context.packageManager
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
    }
}