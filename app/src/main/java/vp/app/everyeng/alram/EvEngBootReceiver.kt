package vp.app.everyeng.alram

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import vp.app.everyeng.util.Pref

/**
 * Created by Android on 2017. 12. 21..
 */
class EvEngBootReceiver : BroadcastReceiver() {

    var alarm1 = EvEngAlarm1Receiver()
    var alarm2 = EvEngAlarm2Receiver()
    var alarm3 = EvEngAlarm3Receiver()

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1!!.action == ("android.intent.action.BOOT_COMPLETED")) {
            Log.d(p0!!.packageName.toString(), "!!! Boot Recevier >> BOOT_COMPLETED >> Start !!");
            val isPush: Boolean = Pref.load(p0!!, Pref.BOOL_PUSH_CHECK) as Boolean
            if (isPush) {
                alarm1.setAlarm(p0!!)
                alarm2.setAlarm(p0!!)
                alarm3.setAlarm(p0!!)
            } else {
                alarm1.cancelAlarm(p0!!)
                alarm2.cancelAlarm(p0!!)
                alarm3.cancelAlarm(p0!!)
            }
        }
    }
}