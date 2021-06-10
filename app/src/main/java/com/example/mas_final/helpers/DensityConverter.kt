package com.example.mas_final.helpers

import android.content.res.Resources
import android.util.TypedValue

val Int.dp: Int
	get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
	get() = (this * Resources.getSystem().displayMetrics.density)

val Double.dp: Double
	get() = (this * Resources.getSystem().displayMetrics.density)

val Long.dp: Long
	get() = (this * Resources.getSystem().displayMetrics.density).toLong()


val Int.sp: Int
	get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(),
			Resources.getSystem().displayMetrics)
			.toInt()

val Float.sp: Float
	get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this,
			Resources.getSystem().displayMetrics)

val Double.sp: Double
	get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(),
			Resources.getSystem().displayMetrics)
			.toDouble()

val Long.sp: Long
	get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(),
			Resources.getSystem().displayMetrics)
			.toLong()


val Int.hours: Int
	get() = this * 60 * 60 * 1.seconds

val Long.hours: Long
	get() = this * 60 * 60 * 1.seconds

val Float.hours: Float
	get() = this * 60 * 60 * 1.seconds

val Double.hours: Double
	get() = this * 60 * 60 * 1.seconds

val Int.minutes: Int
	get() = this * 60 * 1.seconds

val Long.minutes: Long
	get() = this * 60 * 1.seconds

val Float.minutes: Float
	get() = this * 60 * 1.seconds

val Double.minutes: Double
	get() = this * 60 * 1.seconds


val Int.seconds: Int
	get() = this * 1000

val Long.seconds: Long
	get() = this * 1000

val Float.seconds: Float
	get() = this * 1000

val Double.seconds: Double
	get() = this * 1000

