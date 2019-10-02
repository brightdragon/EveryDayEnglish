package vp.app.everyeng.notice.adapter

import android.content.Context
import android.content.Intent
import android.support.design.widget.BaseTransientBottomBar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import vp.app.everyeng.R
import vp.app.everyeng.items.NoticeItem
import vp.app.everyeng.notice.EvEngNoticeDetailActivity

/**
 * Created by Android on 2017. 12. 20..
 */
class NoticeAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private val mContext: Context = context
    private var noticeList: ArrayList<NoticeItem>? = null

    fun setList(list: ArrayList<NoticeItem>){
        this.noticeList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        if(holder is ContentHolder){
            val noticeItem: NoticeItem = noticeList!!.get(position)
            holder.bind(noticeItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.adapter_notice_layout, parent, false)
        return ContentHolder(view)
    }

    override fun getItemCount(): Int {
        return noticeList!!.size
    }

    inner class ContentHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val title: TextView by lazy { itemView.findViewById<TextView>(R.id.title) }
//        private val date: TextView by lazy { itemView.findViewById<TextView>(R.id.date) }
        private val layout: RelativeLayout by lazy { itemView.findViewById<RelativeLayout>( R.id.layout) }
        fun bind(item: NoticeItem){
            title.text = item.title
//            date.text = "[ 2018-06-05 ]"
            layout.setOnClickListener {
                val intent = Intent(mContext, EvEngNoticeDetailActivity::class.java)
                intent.putExtra("NOTICE_ITEM", item)
                mContext!!.startActivity(intent)
            }
        }
    }
}