package vp.app.everyeng.dialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import vp.app.everyeng.R

/**
 * Created by Android on 2017. 12. 26..
 */
class UpdateDialog: DialogFragment() {

    private val confimButton: Button by lazy { view!!.findViewById<Button>(R.id.confirm_button) }

    companion object {

        fun newIntance(): UpdateDialog {
            return UpdateDialog()
        }

        fun show(activity: FragmentActivity) {
            val dialog: UpdateDialog = UpdateDialog.newIntance()
            dialog.show(activity.supportFragmentManager, "UpdateDialog")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
        isCancelable = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.dialog_update_layout, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        confimButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=vp.app.everyeng")
            startActivity(intent)
        }
    }
}