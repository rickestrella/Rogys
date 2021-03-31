package com.techpig.rogys.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class RogysRadioButton(context: Context, attrs: AttributeSet) :
    AppCompatRadioButton(context, attrs) {
    init {
        applyFont()
    }

    private fun applyFont() {
        val typeFace = Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        typeface = typeFace
    }
}