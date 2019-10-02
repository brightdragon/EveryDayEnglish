package vp.app.everyeng.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import vp.app.everyeng.R
import vp.app.everyeng.interfaces.ILoginListener

/**
 * Created by Android on 2017. 12. 19..
 */
class LoginDialog : DialogFragment() {

    private val email: EditText by lazy { view!!.findViewById<EditText>(R.id.email) }
    private val password: EditText by lazy { view!!.findViewById<EditText>(R.id.password) }
    private val loginButton: Button by lazy { view!!.findViewById<Button>(R.id.login_button) }
    private var iLoginListener: ILoginListener? = null

    companion object {
        fun newIntance(listener: ILoginListener): LoginDialog {
            val dialog = LoginDialog()
            dialog.setListener(listener)
            return dialog
        }

        fun show(activity: FragmentActivity, listener: ILoginListener) {
            val dialog: LoginDialog = LoginDialog.newIntance(listener)
            dialog.show(activity.supportFragmentManager, "LoginDialog")
        }

        fun hide(activity: FragmentActivity) {
            val prev: Fragment = activity.supportFragmentManager.findFragmentByTag("LoginDialog")

            if (prev != null) {
                val pd = prev as LoginDialog
                pd.dismiss()
                pd.dismissAllowingStateLoss()
            }
        }
    }

    fun setListener(listener: ILoginListener) {
        this.iLoginListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
        isCancelable = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.dialog_login_layout, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            iLoginListener!!.login(email.text.toString(), password.text.toString())
        }
    }
}