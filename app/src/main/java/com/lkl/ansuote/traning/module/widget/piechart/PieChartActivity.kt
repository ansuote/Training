package com.lkl.ansuote.traning.module.widget.piechart

import android.graphics.Color
import android.os.Bundle
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R
import kotlinx.android.synthetic.main.pie_chart_activity.*

/**
 *
 * 饼状图测试练习
 * @author huangdongqiang
 * @date 12/02/2019
 */
class PieChartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pie_chart_activity)

        pie_chart_view.setData(mutableListOf<PieChartBean>().apply {
            add(PieChartBean(0.3f, "香蕉 30%", Color.YELLOW))
            add(PieChartBean(0.4f, "苹果 40%", Color.RED))
            add(PieChartBean(0.2f, "青枣 20%", Color.GREEN))
        })
    }
}