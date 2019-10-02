package vp.app.everyeng.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import vp.app.everyeng.R
import vp.app.everyeng.util.Pref

/**
 * Created by Android on 2017. 12. 19..
 */
class HowToDialog : DialogFragment() {

    private val confimButton: Button by lazy { view!!.findViewById<Button>(R.id.confirm_button) }

    companion object {
        fun getInstnace(): HowToDialog {
            val dialog = HowToDialog()
            return dialog
        }

        fun show(activity: FragmentActivity) {
            val dialog: HowToDialog = HowToDialog.getInstnace()
            dialog.show(activity.supportFragmentManager, "HowToDialog")
        }

        fun hide(activity: FragmentActivity) {
            val prev: Fragment = activity.supportFragmentManager.findFragmentByTag("HowToDialog")

            if (prev != null) {
                val pd = prev as HowToDialog
                pd.dismiss()
                pd.dismissAllowingStateLoss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.dialog_howto_layout, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        confimButton.setOnClickListener {
            Pref.save(activity, Pref.BOOL_FIRST_WELCOM, true)
            dismiss()
        }
    }
}