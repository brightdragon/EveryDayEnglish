package vp.app.everyeng.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vp.app.everyeng.R

/**
 * Created by Android on 2017. 12. 19..
 */
class ProgressDialog: DialogFragment() {

    companion object {

        fun newIntance(): ProgressDialog {
            val dialog = ProgressDialog()
            return dialog
        }

        fun show(activity: FragmentActivity) {
            val dialog: ProgressDialog = ProgressDialog.newIntance()
            dialog.show(activity.supportFragmentManager, "ProgressDialog")
        }

        fun hide(activity: FragmentActivity) {
            val prev: Fragment = activity.supportFragmentManager.findFragmentByTag("ProgressDialog")

            if (prev != null) {
                val pd = prev as ProgressDialog
                pd.dismissAllowingStateLoss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.MyProgress)
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.dialog_progress_layout, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}