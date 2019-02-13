package com.lkl.ansuote.traning.module.widget.piechart

/**
 *
 *
 * @author huangdongqiang
 * @date 13/02/2019
 */
data class PieChartBean(
        /**
         * 百分比 0.2
         */
        var percentValue: Float,
        /**
         * 文本
         */
        var showText: String,
        /**
         * 对应的颜色
         */
        var color: Int
)