package vp.app.everyeng.base.interfaces

/**
 * Created by Android on 2017. 12. 18..
 */
interface BaseView<in T: BasePresenter> {
    fun setPresenter(presenter: T)
}