package vp.app.everyeng.setting

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import vp.app.everyeng.R
import vp.app.everyeng.base.BaseActivity
import vp.app.everyeng.base.BaseToolbar


/**
 * Created by Android on 2018. 2. 7..
 */
class EvEngVoiceSettingActivity : BaseActivity(), View.OnClickListener {


    private val toolbar: BaseToolbar by lazy { findViewById<BaseToolbar>(R.id.toolbar) }
    private val leftTitle: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.title_left) }

    private val mViewPager: ViewPager by lazy { findViewById<ViewPager>(R.id.viewpager) }
    private val indicator: LinearLayout by lazy { findViewById<LinearLayout>(R.id.minidot_container) }
    private val voiceText: TextView by lazy { findViewById<TextView>(R.id.voice_text) }
    private lateinit var mAdapter: VoicePagerAdapter

    override fun getResource(): Int {
        return R.layout.activity_voice_setting_layout
    }

    override fun setInitialize() {

        leftTitle.setOnClickListener(this@EvEngVoiceSettingActivity)

        mAdapter = VoicePagerAdapter(this@EvEngVoiceSettingActivity)
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                setSelectMinidot(position)

                if (position === 0) {
                    voiceText.setText(getString(R.string.voice_set_01))
                } else if (position === 1) {
                    voiceText.setText(getString(R.string.voice_set_02))
                } else if (position === 2) {
                    voiceText.setText(getString(R.string.voice_set_03))
                } else if (position === 3) {
                    voiceText.setText(getString(R.string.voice_set_04))
                } else {
                    voiceText.setText(getString(R.string.voice_set_05))
                }
            }
        })
        mViewPager.adapter = mAdapter

        initMiniDot()
    }

    override fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.layoutType(BaseToolbar.VIEW_TYPE_COMMON)
        toolbar.setTitleCommonName(getString(R.string.voice_set_title))
    }

    private fun initMiniDot() {
        val count = mViewPager.adapter.count

        for (i in 0 until count) {
            val dot = View(this@EvEngVoiceSettingActivity)
            dot.setBackgroundResource(R.drawable.selector_minidot)
            dot.isSelected = i == 0

            val lp = RelativeLayout.LayoutParams(pxFromDp(this@EvEngVoiceSettingActivity, 7f), pxFromDp(this@EvEngVoiceSettingActivity, 7f))
            lp.rightMargin = pxFromDp(this@EvEngVoiceSettingActivity, 8f)
            indicator.addView(dot, lp)
        }
    }

    private fun setSelectMinidot(position: Int) {
        val count = indicator.childCount
        for (i in 0 until count) {
            indicator.getChildAt(i).isSelected = false
        }
        indicator.getChildAt(position).isSelected = true
    }

    private fun pxFromDp(context: Context, dp: Float): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    inner class VoicePagerAdapter(context: Context) : PagerAdapter() {

        private val mContext: Context = context
        private val mLayoutInflate: LayoutInflater = (LayoutInflater.from(context))
        private val imgList: Array<Int> = arrayOf(
                R.drawable.kakao_04,
                R.drawable.kakao_03,
                R.drawable.kakao_06,
                R.drawable.kakao_01,
                R.drawable.kakao_05
        )

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            val mView: View = mLayoutInflate.inflate(R.layout.adapter_voice_layout, container, false)
            val mImage: ImageView = mView.findViewById(R.id.img)
            mImage.setImageResource(imgList.get(position))
            container!!.addView(mView)
            return mView
        }

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view === `object` as View
        }

        override fun getCount(): Int {
            return imgList.size
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container!!.removeView(`object` as View)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.title_left -> {
                finish()
            }
        }
    }
}