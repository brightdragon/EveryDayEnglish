package vp.app.everyeng.alram

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import vp.app.everyeng.R
import vp.app.everyeng.main.EvEngMainActivity

/**
 * Created by Android on 2017. 12. 21..
 */
class EvEngSchedulingService: IntentService("SchedunlingService") {

    override fun onHandleIntent(p0: Intent?) {
        sendNotification()
    }

    fun sendNotification(){
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val penddingIntent: PendingIntent = PendingIntent.getActivity(this, 0, Intent(this, EvEngMainActivity::class.java), 0)
        val mNotification: NotificationCompat.Builder = NotificationCompat.Builder(this)
        mNotification.setSmallIcon(R.mipmap.eveng_icon)
        mNotification.setContentTitle("[매일 영어]")
        mNotification.setStyle(NotificationCompat.BigTextStyle())
        mNotification.setContentText("새로운 단어와 새로운 회화를 봐야 할 시간이에요!")
        mNotification.setContentIntent(penddingIntent)
        mNotificationManager.notify(0, mNotification.build())
    }
}