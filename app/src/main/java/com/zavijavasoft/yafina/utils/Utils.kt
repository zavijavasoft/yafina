package com.zavijavasoft.yafina.utils

import android.graphics.Color
import java.math.BigDecimal
import java.util.*

fun Float.roundSum() = BigDecimal(this.toDouble()).setScale(2, BigDecimal.ROUND_HALF_EVEN).toFloat()

object ColorSelector {

    private val mColors = arrayOf("#39add1", // light blue
            "#3079ab", // dark blue
            "#c25975", // mauve
            "#e15258", // red
            "#f9845b", // orange
            "#838cc7", // lavender
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#e0ab18", // mustard
            "#637a91", // dark gray
            "#f092b0", // pink
            "#b7c0c7"  // light gray
    )

    fun getColorByLeadingLetter(letter: Char): Int {
        val x = letter.toInt() % mColors.size
        return Color.parseColor(mColors[x])
    }


    val randomColor: Int
        get() {
            val color: String

            val randomGenerator = Random()
            val randomNumber = randomGenerator.nextInt(mColors.size)

            color = mColors[randomNumber]


            return Color.parseColor(color)
        }
}