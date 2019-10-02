package vp.app.everyeng.util

import android.content.Context

/**
 * Created by Android on 2017. 12. 19..
 */
class Pref {

    companion object {

        val KEY_PREF = "ev3eng"
        val BOOL_FIRST_WELCOM: String = "bool_first_welcom"
        val BOOL_WELCOM: String = "bool_welcom"
        val BOOL_PUSH_CHECK: String = "bool_push_check"

        const val FIRST_WORD = "first_word"
        const val SECOND_WORD = "second_word"
        const val THIRD_WORD = "third_word"

        const val TODAY = "today"

        fun save(context: Context, key: String, value: Any) {

            val pref = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)
            val pe = pref.edit()

            when (key) {
                BOOL_WELCOM,
                BOOL_FIRST_WELCOM,
                BOOL_PUSH_CHECK -> {
                    pe.putBoolean(key, value as Boolean)
                }

                TODAY,
                FIRST_WORD,
                SECOND_WORD,
                THIRD_WORD -> {
                    pe.putString(key, value as String)
                }
            }

            pe.commit()
        }

        fun load(context: Context, key: String): Any? {

            val pref = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)

            when (key) {
                BOOL_WELCOM,
                BOOL_FIRST_WELCOM,
                BOOL_PUSH_CHECK -> {
                    return pref.getBoolean(key, false)
                }

                TODAY,
                FIRST_WORD,
                SECOND_WORD,
                THIRD_WORD -> {
                    return pref.getString(key, "")
                }
            }

            return null
        }
    }
}