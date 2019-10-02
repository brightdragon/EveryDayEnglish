package vp.app.everyeng.my.adapter

import android.content.Context
import android.media.Image
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import vp.app.everyeng.R
import vp.app.everyeng.interfaces.ISelectListener
import vp.app.everyeng.main.item.EnglishItem

/**
 * Created by Android on 2017. 12. 22..
 */
class MyPageAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext: Context = context
    private lateinit var englishList: ArrayList<EnglishItem>
    private lateinit var selectListener: ISelectListener

    fun setListener(listener: ISelectListener){
        selectListener = listener
    }

    fun setList(list: ArrayList<EnglishItem>) {
        this.englishList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return englishList!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ContentHolder) {
            val noticeItem: EnglishItem = englishList!!.get(position)
            holder.bind(noticeItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.adapter_mypage_layout, parent, false)
        return ContentHolder(view)
    }

    inner class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eng: TextView by lazy { itemView.findViewById<TextView>(R.id.word_english) }
        private val kor: TextView by lazy { itemView.findViewById<TextView>(R.id.word_korea) }
        private val speechButton: CardView by lazy { itemView.findViewById<CardView>(R.id.speech_button) }
        fun bind(item: EnglishItem) {
            eng.text = item.wordEnglish
            kor.text = item.workKorea

            speechButton.setOnClickListener {
                selectListener.onSelect(item)
            }
        }
    }

}