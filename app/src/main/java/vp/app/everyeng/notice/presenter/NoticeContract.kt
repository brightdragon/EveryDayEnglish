package vp.app.everyeng.notice.presenter

import vp.app.everyeng.base.interfaces.BasePresenter
import vp.app.everyeng.base.interfaces.BaseView

/**
 * Created by Android on 2017. 12. 27..
 */
interface NoticeContract {

    interface Presenter: BasePresenter {
        fun setProgress(isShow: Boolean)
    }

    interface View: BaseView<Presenter> {
        fun showProgress()
        fun hideProgress()
    }
}