package vp.app.everyeng.main.data

import vp.app.everyeng.main.item.EnglishItem

/**
 * Created by Android on 2017. 12. 27..
 */
interface MainDataSource {

    interface CallbackListener {
        fun success(engItem: EnglishItem)
        fun faild()
    }

    fun setData(listener: CallbackListener)

}