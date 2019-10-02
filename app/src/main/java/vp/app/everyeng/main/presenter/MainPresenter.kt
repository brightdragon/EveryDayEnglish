package vp.app.everyeng.main.presenter

import vp.app.everyeng.main.data.MainDataSource
import vp.app.everyeng.main.data.MainRepository
import vp.app.everyeng.main.item.EnglishItem

/**
 * Created by Android on 2017. 12. 27..
 */
class MainPresenter : MainContract.Presenter {


    lateinit var mRepository: MainRepository

    var mContext: MainContract.View

    constructor(context: MainContract.View) {
        mContext = context
    }

    override fun onAttach() {

    }

    override fun onDetach() {
    }

    override fun setProgress(isShow: Boolean) {
        if (isShow) mContext.showProgress() else mContext.hideProgress()
    }

    override fun setStartActivity(type: Int) {
        mContext.showActivity(type)
    }

    override fun setDataRepository(repository: MainRepository) {
        mRepository = repository
    }

    override fun setEnlgishItem() {
        mRepository.setData(object : MainDataSource.CallbackListener {
            override fun success(engItem: EnglishItem) {
                mContext.showEnglishItem(engItem)
            }

            override fun faild() {
                mContext.showToastMessage("NOT DATA!!!!")
            }
        })
    }

    override fun setSaveWord() {
    }

}