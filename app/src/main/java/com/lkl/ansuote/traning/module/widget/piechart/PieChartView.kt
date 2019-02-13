package com.lkl.ansuote.traning.module.widget.piechart

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.ConvertUtils

/**
 *
 * 饼状图
 * @author huangdongqiang
 * @date 12/02/2019
 */
class PieChartView @JvmOverloads constructor(context: Context? = null, attrs: AttributeSet? = null) : View(context, attrs) {
    private val mPaint: Paint
    private val mTextPaint: Paint
    private val mDataList: MutableList<PieChartBean> = mutableListOf()
    private var mHeight: Int = 0
    private var mWidth: Int = 0
    private var mRectF: RectF? = null
    /**
     * 测量文本框高
     */
    private var mTextBounds: Rect = Rect()

    init {
        mPaint = Paint().apply {
            this.style = Paint.Style.FILL_AND_STROKE
            this.color = Color.GRAY
            this.isAntiAlias = true

        }

        mTextPaint = Paint().apply {
            this.style = Paint.Style.FILL_AND_STROKE
            this.color = Color.GRAY
            this.textSize = ConvertUtils.sp2px(15f).toFloat()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mHeight = h
        mWidth = w
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //将坐标移动到中心点
        val radius = (Math.min(mWidth, mHeight) / 2).toFloat()
        canvas?.translate((mWidth / 2).toFloat(), (mHeight / 2).toFloat())
        if (null == mRectF) {
            mRectF = RectF(-radius, -radius, radius, radius)
        }

        //开始角度，默认是顺时针绘制
        var startAngle = 0f
        mDataList.forEach {
            canvas?.save()
            val sweepAngle = it.percentValue * 360
            mPaint.color = it.color
            canvas?.drawArc(mRectF, startAngle, sweepAngle, true, mPaint)
            val showText = it.showText
            canvas?.rotate(startAngle + sweepAngle/2)
            canvas?.translate(radius / 2, 0f)
            canvas?.rotate(-(startAngle + sweepAngle / 2))

            //测量文本的宽度，适当调整偏移量
            mTextPaint.getTextBounds(showText, 0, showText.length, mTextBounds)
            canvas?.drawText(showText, 0, showText.length, -(mTextBounds.width()/2).toFloat(), 0f, mTextPaint)
            canvas?.restore()
            startAngle += sweepAngle
        }


    }


    fun setData(dataList: MutableList<PieChartBean>) {
        mDataList.clear()
        mDataList.addAll(dataList)
        invalidate()
    }
}