package com.example.weather.ui.fragment

import android.graphics.Typeface
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weather.R

abstract class BaseFragment : Fragment() {

    fun setTitle(title: String) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.setHomeButtonEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val textView = TextView(activity)
        textView.text = title
        textView.textSize = 20f
        textView.setTypeface(null, Typeface.NORMAL)
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        textView.gravity = Gravity.CENTER
        textView.setTextColor(resources.getColor(R.color.colorWhite))
        (activity as AppCompatActivity?)!!.supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        (activity as AppCompatActivity?)!!.supportActionBar!!.customView = textView
    }
}