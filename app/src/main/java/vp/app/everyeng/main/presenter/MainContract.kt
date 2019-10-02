package vp.app.everyeng.main.presenter

import vp.app.everyeng.base.interfaces.BasePresenter
import vp.app.everyeng.base.interfaces.BaseView
import vp.app.everyeng.main.data.MainRepository
import vp.app.everyeng.main.item.EnglishItem

/**
 * Created by Android on 2017. 12. 27..
 */
interface MainContract {

    interface Presenter: BasePresenter {

        fun setProgress(isShow: Boolean)
        fun setStartActivity(type: Int)
        fun setDataRepository(repository: MainRepository)
        fun setEnlgishItem()
        fun setSaveWord()

    }

    interface View: BaseView<Presenter> {

        fun showToastMessage(message: String)
        fun showProgress()
        fun hideProgress()
        fun showActivity(type: Int)
        fun showEnglishItem(item: EnglishItem)
    }
}