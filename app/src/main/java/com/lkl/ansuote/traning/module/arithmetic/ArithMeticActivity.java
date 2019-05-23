package com.lkl.ansuote.traning.module.arithmetic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.lkl.ansuote.hdqlibrary.base.BaseActivity;
import com.lkl.ansuote.traning.R;
import com.lkl.ansuote.traning.module.util.ArithMeticUtil;

/**
 * @author huangdongqiang
 * @date 23/05/2019
 */
public class ArithMeticActivity extends BaseActivity implements View.OnClickListener {
    private int[] dataList = {32, 4, 2, 6, 1, 8, 10, 53, 12, 18};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.arithmetic_activity);

        findViewById(R.id.btn_bubble_sort).setOnClickListener(this);
        findViewById(R.id.btn_quick_sort).setOnClickListener(this);
        findViewById(R.id.btn_selection_sort).setOnClickListener(this);
        findViewById(R.id.btn_insert_sort).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bubble_sort :
                ArithMeticUtil.bubbleSort(dataList);
                break;

            case R.id.btn_quick_sort:
                ArithMeticUtil.quickSort(dataList);
                break;

            case R.id.btn_selection_sort:
                ArithMeticUtil.selectionSort(dataList);
                break;
            case R.id.btn_insert_sort:
                ArithMeticUtil.insertSort(dataList);
                break;
            default:
        }

        Log.i("lkl", formatString(dataList));
    }

    private String formatString(int[] dataList) {
        StringBuilder builder = new StringBuilder();
        for(int i: dataList) {
            builder.append(i);
            builder.append(" ");
        }
        return builder.toString();
    }
}
