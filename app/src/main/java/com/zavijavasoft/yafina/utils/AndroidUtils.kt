package com.zavijavasoft.yafina.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.RectF
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

class CharteeGraphicView(val appContext: Context) : View(appContext) {

    private val paint = Paint(ANTI_ALIAS_FLAG)

    private val rectangle = RectF(40f, 40f, 1000f, 1000f)

    lateinit var values: FloatArray
    var colors: IntArray = intArrayOf(0, 0, 0, 0)
    var valueDegrees = floatArrayOf(90f, 90f, 90f, 90f)


    fun update(values: FloatArray, colors: IntArray) {
        this.colors = colors
        this.values = values

        valueDegrees = FloatArray(values.size) { it -> values[it] * 360.0f }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var temp = 0.0f
        for (i in valueDegrees.indices) {
            paint.color = colors[i]
            canvas.drawArc(rectangle, temp, valueDegrees[i], true, paint)
            temp += valueDegrees[i]
        }
    }

}
