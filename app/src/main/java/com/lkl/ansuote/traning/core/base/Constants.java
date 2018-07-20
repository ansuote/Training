package com.lkl.ansuote.traning.core.base;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author huangdongqiang
 * @date 11/07/2018
 */
public class Constants {

    //性别
    public interface Gender{
        int UNKNOWN = 0;
        int MALE = 1;
        int FEMALE = 2;

        @IntDef({UNKNOWN, MALE, FEMALE})
        @Retention(RetentionPolicy.SOURCE)
        @interface GenderType {}
    }



}
