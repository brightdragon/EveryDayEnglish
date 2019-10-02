package vp.app.everyeng.base

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import vp.app.everyeng.R

/**
 * Created by Android on 2017. 12. 18..
 */
class BaseToolbar : Toolbar {

    private var mContext: Context? = null
    private var view: View? = null

    private val titleCommonName: TextView by lazy { view!!.findViewById<TextView>(R.id.title_common_name) }
    private val wordEnglish: TextView by lazy { view!!.findViewById<TextView>(R.id.word_eng) }
    private val wordKorea: TextView by lazy { view!!.findViewById<TextView>(R.id.word_kr) }
    private val wordTitle: TextView by lazy { view!!.findViewById<TextView>(R.id.word_title) }
    private val timeInfo: TextView by lazy { view!!.findViewById<TextView>(R.id.time_info) }

    companion object {
        const val VIEW_TYPE_MAIN = 0
        const val VIEW_TYPE_COMMON = 1
        const val VIEW_TYPE_SECRET = 2
    }

    constructor(context: Context) : super(context) {
        this.mContext = context
        this.setContentInsetsRelative(0, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mContext = context
        this.setContentInsetsRelative(0, 0)
    }

    constructor(context: Context, attrs: AttributeSet, def: Int) : super(context, attrs, def) {
        this.mContext = context
        this.setContentInsetsRelative(0, 0)
    }

    fun layoutType(type: Int) {

        when (type) {
            VIEW_TYPE_MAIN -> {
                view = LayoutInflater.from(context).inflate(R.layout.view_titlebar_layout, this)
            }

            VIEW_TYPE_COMMON -> {
                view = LayoutInflater.from(context).inflate(R.layout.view_titlebar_setting_layout, this)
            }

            VIEW_TYPE_SECRET -> {
                view = LayoutInflater.from(context).inflate(R.layout.view_titlebar_secrect_layout, this)
            }
        }
    }

    fun setTitleEnglishCount(type: String) {

        if (type == "1") {
            wordTitle.text = mContext!!.getString(R.string.today_word_01)
            timeInfo.text = mContext!!.getString(R.string.open_time_01)
        } else if (type == "2") {
            wordTitle.text = mContext!!.getString(R.string.today_word_02)
            timeInfo.text = mContext!!.getString(R.string.open_time_02)
        } else if (type == "3") {
            wordTitle.text = mContext!!.getString(R.string.today_word_03)
            timeInfo.text = mContext!!.getString(R.string.open_time_03)
        }
    }

    fun setTitleCommonName(title: String) {
        this.titleCommonName?.text = title
    }

    fun setWordEnglish(eng: String) {
        wordEnglish.text = eng
//        wordEnglish.paintFlags = Paint.UNDERLINE_TEXT_FLAG // 밑줄
    }

    fun setWordKorea(kr: String) {
        wordKorea.text = kr
    }
}