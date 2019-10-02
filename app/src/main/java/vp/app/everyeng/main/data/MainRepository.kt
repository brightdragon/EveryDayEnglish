package vp.app.everyeng.main.data

import com.google.firebase.database.*
import vp.app.everyeng.main.item.EnglishItem

/**
 * Created by Android on 2017. 12. 27..
 */
object MainRepository : MainDataSource {

    private val mFirebaseDataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val mDatabaseReference: DatabaseReference = mFirebaseDataBase.reference

    override fun setData(listener: MainDataSource.CallbackListener) {

        var engList: ArrayList<EnglishItem>

        try {
            mDatabaseReference.child("-------------").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {
                    if (p0!!.hasChildren()) {

                        engList = arrayListOf()

                        for (child: DataSnapshot in p0!!.children) {
                            val item: EnglishItem? = child!!.getValue(EnglishItem::class.java)

                            if (item!!.isVisible) {
                                engList.add(item!!)
                            }
                        }

                        listener.success(engList.get(0))
                    } else {
                        listener.faild()
                        return
                    }
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    // 강제 업데이트 >> 아직 적용 안함
//    fun getUpdateInfo(listener: IUpdateListener) {
//
//        ProgressDialog.show(this@EvEngMainActivity)
//
//        try {
//            getDatabaseReference().child(UPDATE_INFO).addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onCancelled(p0: DatabaseError?) {
//                }
//
//                override fun onDataChange(p0: DataSnapshot?) {
//                    val item: UpdateItem? = p0!!.getValue(UpdateItem::class.java)
//                    Log.d("TAG", "kypark >> item = " + item!!.isUpdate)
//
//                    if (item!!.isUpdate) {
//                        listener.update(true)
//                    } else {
//                        listener.update(false)
//                    }
//                }
//            })
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }


}