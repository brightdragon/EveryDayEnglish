package vp.app.everyeng.sponsor

import vp.app.everyeng.R
import vp.app.everyeng.base.BaseActivity
import vp.app.everyeng.base.BaseToolbar

/**
 * Created by Android on 2017. 12. 20..
 */
class SponsorActivity: BaseActivity() {

    private val toolbar: BaseToolbar by lazy { findViewById<BaseToolbar>(R.id.toolbar) }

    override fun getResource(): Int {
        return R.layout.activity_eveng_sponsor_layout
    }

    override fun setInitialize() {

    }

    override fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.layoutType(BaseToolbar.VIEW_TYPE_COMMON)
        toolbar.setTitleCommonName("후원 하기")
    }
}