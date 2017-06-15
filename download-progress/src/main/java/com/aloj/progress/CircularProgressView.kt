package com.aloj.progress

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator


@Suppress("unused")
class CircularProgressView : View {

    companion object {
        private val ROTATION_TIME = 2000
        private val RISING_TIME = 500f
        private val CIRCLE_ANIM_STEP = 3
    }


    private val circleRect = RectF()
    private val decelerateInterpolator: DecelerateInterpolator = DecelerateInterpolator()
    private val accelerateInterpolator: AccelerateInterpolator = AccelerateInterpolator()
    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = context.resources.getDimensionPixelSize(R.dimen.default_radial_progress_stroke_size).toFloat()
        color = Color.WHITE
    }
    private val radialPadding = context.resources.getDimensionPixelSize(R.dimen.radial_progress_padding)
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.progress_background)
        strokeCap = Paint.Cap.ROUND
    }


    private var size: Int = context.resources.getDimensionPixelSize(R.dimen.radial_progress_size)
    private var indeterminate = true
    private var lastUpdateTime: Long = 0
    private var radOffset: Float = 0f
    private var currentCircleLength: Float = 0f
    private var risingCircleLength: Boolean = false
    private var currentProgressTime: Float = 0f
    //private var currentProgress = 0f
    private var goalCircleLength = 0f
    var progress: Float = 0f
        set(value) {

            /*field = if (value > 1) {
                1f
            } else if (value <= 0) {
                //currentProgress = 0f
                0f
            } else {
                value
            }*/

            field = when {
                value > 1 -> 1f
                value < 0 -> 0f
                else -> value
            }

            goalCircleLength = 360 * field

            indeterminate = field == 0f

            invalidate()
        }
    var progressStarted: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setProgressColor(color: Int) {
        progressPaint.color = color
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, circlePaint)
        if (progressStarted) {
            val x = (measuredWidth - size) / 2
            val y = (measuredHeight - size) / 2
            val strokeWidth = progressPaint.strokeWidth
            circleRect.set(x.toFloat() + strokeWidth + radialPadding, y.toFloat() + strokeWidth + radialPadding,
                    (x + size).toFloat() - strokeWidth - radialPadding, (y + size).toFloat() - strokeWidth - radialPadding)
            canvas.drawArc(circleRect, radOffset, currentCircleLength, false, progressPaint)
            updateAnimation()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(size, widthMeasureSpec)
        val height = resolveSize(size, heightMeasureSpec)
        size = Math.min(width, height)
        setMeasuredDimension(size, size)
        progressPaint.strokeWidth = size / 17f
    }

    private fun updateAnimation() {
        val newTime = System.currentTimeMillis()
        var dt = newTime - lastUpdateTime
        if (dt > 17) {
            dt = 17
        }
        lastUpdateTime = newTime
        radOffset += 360 * dt / ROTATION_TIME
        val count = (radOffset / 360).toInt()
        radOffset -= (count * 360).toFloat()

        if (indeterminate) {

            currentProgressTime += dt.toFloat()
            if (currentProgressTime >= RISING_TIME) {
                currentProgressTime = RISING_TIME
            }
            if (risingCircleLength) {
                currentCircleLength = 4 + 266 * accelerateInterpolator.getInterpolation(currentProgressTime / RISING_TIME)
            } else {
                currentCircleLength = 4 - 270 * (1.0f - decelerateInterpolator.getInterpolation(currentProgressTime / RISING_TIME))
            }
            if (currentProgressTime == RISING_TIME) {
                if (risingCircleLength) {
                    radOffset += 270f
                    currentCircleLength = -266f
                }
                risingCircleLength = !risingCircleLength
                currentProgressTime = 0f
            }
        } else {
            /*currentCircleLength = 360 * currentProgress
            if (currentProgress != download_progress_layout) {
                currentProgress += if (currentProgress < download_progress_layout) 0.01f else -0.01f
            }*/

            if (Math.abs(currentCircleLength - goalCircleLength) > CIRCLE_ANIM_STEP * 2) {
                currentCircleLength += if (currentCircleLength < goalCircleLength) CIRCLE_ANIM_STEP else -1 * CIRCLE_ANIM_STEP
            }
        }
        invalidate()
    }
}
