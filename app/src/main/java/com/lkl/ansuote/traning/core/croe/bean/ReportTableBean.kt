package com.lkl.ansuote.traning.core.croe.bean

import com.bin.david.form.annotation.SmartColumn
import com.bin.david.form.annotation.SmartTable

/**
 * Created by huangdongqiang on 2018/10/14.
 */
@SmartTable(name = "数据报表", count = false)
class ReportTableBean() {

    @JvmField
    @SmartColumn(id = 1, name = "员工", autoCount = true, fixed = true)
    var Member: String = ""

    @SmartColumn(id = 2, name = "总标记数", autoCount = true)
    var MarkTotalCount: String = ""

    //呼入标记数
    @SmartColumn(id = 3, name = "呼入标记数", autoCount = true)
    var CallInMarkTotalCount: String = ""

    //呼出标记数
    @SmartColumn(id = 4, name = "呼出标记数", autoCount = true)
    var CallOutMarkTotalCount: String = ""

    //标记率
    @SmartColumn(id = 5, name = "标记率", autoCount = true)
    var MarkRate: String = ""

    //60s+标记数
    @SmartColumn(id = 6, name = "60s+标记数", autoCount = true)
    var MarkMore60Count: String = ""

    //60s+标记率
    @SmartColumn(id = 7, name = "60s+标记率", autoCount = true)
    var MarkMore60Rate: String = ""

    //总详细备注数
    @SmartColumn(id = 8, name = "总详细备注数", autoCount = true)
    var RemarkTotalCount: String = ""

    //呼入备注数
    @SmartColumn(id = 9, name = "呼入备注数", autoCount = true)
    var CallInRemarkTotalCount: String = ""

    //呼出备注数
    @SmartColumn(id = 10, name = "呼出备注数", autoCount = true)
    var CallOutRemarkTotalCount: String = ""

    //详细备注率
    @SmartColumn(id = 11, name = "详细备注率", autoCount = true)
    var RemarkRate: String = ""

    //60s+备注数
    @SmartColumn(id = 12, name = "60s+备注数", autoCount = true)
    var RemarkMore60Count: String = ""

    //60s+备注率
    @SmartColumn(id = 13, name = "60s+备注率", autoCount = true)
    var RemarkMore60Rate: String = ""

    //总转化数
    @SmartColumn(id = 14, name = "总转化数", autoCount = true)
    var ConverTotalWithHisCount: String = ""

    //呼入转化数
    @SmartColumn(id = 15, name = "呼入转化数", autoCount = true)
    var CallInConverTotalCount: String = ""

    //呼出转化数
    @SmartColumn(id = 16, name = "呼出转化数", autoCount = true)
    var CallOutConverTotalCount: String = ""

    //转化率
    @SmartColumn(id = 17, name = "转化率", autoCount = true)
    var ConverRate: String = ""

    //新增转化数
    @SmartColumn(id = 18, name = "新增转化数", autoCount = true)
    var ConverTotalCount: String = ""

    //总次数
    @SmartColumn(id = 19, name = "总次数", autoCount = true)
    var TotalCallCount: String = ""

    //总时间长
    @SmartColumn(id = 20, name = "总时间长", autoCount = true)
    var TotalCallTotalSec: String = ""

    //均时间长
    @SmartColumn(id = 21, name = "均时间长", autoCount = true)
    var CallAveSec: String = ""

    //最长时间
    @SmartColumn(id = 22, name = "最长时间", autoCount = true)
    var TotalCallMaxLenCall: String = ""

    //呼入次数
    @SmartColumn(id = 23, name = "呼入次数", autoCount = true)
    var CallInTotalCount: String =""

    //接听次数
    @SmartColumn(id = 24, name = "接听次数", autoCount = true)
    var CallInCount: String = ""

    //接听率
    @SmartColumn(id = 25, name = "接听率", autoCount = true)
    var CallInRate: String = ""

    constructor(Member: String, MarkTotalCount: String) : this() {
        this.Member = Member
        this.MarkTotalCount = MarkTotalCount

    }
}