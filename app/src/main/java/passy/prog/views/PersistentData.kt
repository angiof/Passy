package passy.prog.views

import android.content.Context
import com.google.gson.Gson
class PersistentData(){
    fun saveParam(c: Context, key: String?, value: Any?) {
        val preferences = c.getSharedPreferences("code", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        when (value) {
            is String -> {
                editor.putString(key, value)
            }
            is Int -> {
                editor.putString(key, value.toString())
            }
            is Boolean -> {
                editor.putBoolean(key, value)
            }
            else -> {
                editor.putString(key, Gson().toJson(value))
            }
        }
        editor.apply()
    }

    fun getParam(c: Context?, key: String?): String? {
        if (c == null) return null
        val preferences = c.getSharedPreferences("code", Context.MODE_PRIVATE)
        return preferences.getString(key, null)
    }
}

