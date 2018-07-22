package com.zavijavasoft.yafina.utils

import java.math.BigDecimal

fun Float.roundSum() = BigDecimal(this.toDouble()).setScale(2, BigDecimal.ROUND_HALF_EVEN).toFloat()