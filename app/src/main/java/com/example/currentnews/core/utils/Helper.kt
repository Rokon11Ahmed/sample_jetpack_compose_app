package com.example.currentnews.core.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object Helper {

    //hide keyboard
    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun toReadableDate(dateInput: String): String {
        return try {
            val dateFormat: DateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val date: Date =
                dateFormat.parse(dateInput) as Date
            val formatter: DateFormat =
                SimpleDateFormat(
                    "MMMM dd, yyyy",
                    Locale.getDefault()
                )
            formatter.format(date)
        } catch (e: java.lang.Exception) {
            dateInput
        }
    }

}