package com.lkl.ansuote.traning.module.json

import android.os.Bundle
import android.util.Log
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
import com.lkl.ansuote.hdqlibrary.base.BaseActivity

/**
 *
 *
 * @author huangdongqiang
 * @date 24/10/2018
 */
class JsonTestActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bean = JsonBean("China")
        val list = mutableListOf<String>().apply {
            this.add("apple")
            this.add("2")
            this.add("3")
        }

        val objList = mutableListOf<JsonBean>().apply {
            this.add(JsonBean("country1", "area1"))
            this.add(JsonBean("country2", "area2"))
        }


        val map = mutableMapOf<String, String>().apply {
            this["key1"] = "value1"
            this["key2"] = "value2"
        }

        Log.i("lkl", "bean.toString -- " + bean.toString())
        //list.toString = [apple, 2, 3]
        Log.i("lkl", "list.toString() --$list")
        // map.toString = {key1=value1, key2=value2}
        Log.i("lkl", "map.toString() -- $map")


        //{"country":"China"}
        Log.i("lkl", "bean -- " + JSON.toJSONString(JsonBean("China")))

        //输出 null 字段        {"area":null,"country":"China"}
        Log.i("lkl", "bean -- " + JSON.toJSONString(bean, SerializerFeature.WriteMapNullValue))

        //输出 list            ["apple","2","3"]
        Log.i("lkl", "bean -- " + JSON.toJSONString(list))

        //输出 objList        [{"area":"area1","country":"country1"},{"area":"area2","country":"country2"}]
        Log.i("lkl", "bean -- " + JSON.toJSONString(objList))


        //输出map {"key1":"value1","key2":"value2"}
        Log.i("lkl", "bean -- " + JSON.toJSONString(map))


    }
}


data class JsonBean(val country: String, val area: String ?= null)