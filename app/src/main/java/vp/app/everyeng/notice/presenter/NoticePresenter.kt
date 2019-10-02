package vp.app.everyeng.notice.presenter

/**
 * Created by Android on 2017. 12. 27..
 */
class NoticePresenter: NoticeContract.Presenter {

    var mView: NoticeContract.View

    constructor(view: NoticeContract.View){
        mView = view
    }

    override fun onAttach() {

    }

    override fun onDetach() {

    }

    override fun setProgress(isShow: Boolean) {
        if(isShow) mView.showProgress() else mView.hideProgress()
    }
}