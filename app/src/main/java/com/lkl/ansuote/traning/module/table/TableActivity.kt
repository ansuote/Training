package com.lkl.ansuote.traning.module.table

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.bin.david.form.core.SmartTable
import com.bin.david.form.data.style.FontStyle
import com.blankj.utilcode.util.ConvertUtils
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R
import com.lkl.ansuote.traning.core.bean.ReportTableBean

/**
 * Created by huangdongqiang on 2018/10/14.
 */
class TableActivity :BaseActivity() {
    private lateinit var mTable: SmartTable<ReportTableBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_activity)
        initTable()

    }

    private fun initTable() {
        mTable = findViewById(R.id.table) as SmartTable<ReportTableBean>
        FontStyle.setDefaultTextSize(ConvertUtils.sp2px(18f))
        mTable.setData(getData())
        mTable.config.isShowTableTitle = false
        mTable.config.isShowXSequence = false
        mTable.config.isShowYSequence = false
        mTable.config.isFixedXSequence = true
        mTable.config.isFixedYSequence = true
        mTable.config.isFixedCountRow = false
        mTable.config.columnTitleHorizontalPadding = ConvertUtils.dp2px(24f)
        mTable.config.columnTitleVerticalPadding = ConvertUtils.dp2px(16f)
        mTable.config.horizontalPadding = ConvertUtils.dp2px(16f)
        mTable.config.verticalPadding = ConvertUtils.dp2px(16f)
        mTable.config.tableTitleStyle = FontStyle(this, ConvertUtils.sp2px(18f), ContextCompat.getColor(this, android.R.color.holo_red_dark))
        mTable.setZoom(true, 2f, 0.2f)
    }

    private fun getData(): MutableList<ReportTableBean> {
        return mutableListOf<ReportTableBean>().apply {
            for (i in 0..100) {
                add(ReportTableBean("sdof", "osdifos"))
            }
        }
    }
}