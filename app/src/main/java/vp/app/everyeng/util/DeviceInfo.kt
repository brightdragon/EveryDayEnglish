package vp.app.everyeng.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 * Created by Android on 2017. 12. 20..
 */
object DeviceInfo {

    fun appVersionName(context: Context?): String {
        var pInfo: PackageInfo? = null

        try {
            pInfo = context?.packageManager?.getPackageInfo(context?.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return pInfo!!.versionName
    }
}