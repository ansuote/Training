package com.lkl.ansuote.traning.core.base;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author huangdongqiang
 * @date 11/07/2018
 */
public class Constants {

    public static String BUGLY_ID = "79c5354a95";

    public static String TINKER_DELEGATE_CLASS_NAME = "com.lkl.ansuote.traning.core.base.tinker.BusinessApplicationLike";

    public static String TINKER_LOADER_CLASS_NAME = "com.tencent.tinker.loader.TinkerLoader";

    //性别
    public interface Gender{
        int UNKNOWN = 0;
        int MALE = 1;
        int FEMALE = 2;

        @IntDef({UNKNOWN, MALE, FEMALE})
        @Retention(RetentionPolicy.SOURCE)
        @interface GenderType {}
    }

    public static final String URL_PREFIX = "/com/lkl/ansuote/training/";
    public static final String URL_AROUTER_JUMP_ACTIVITY = URL_PREFIX + "ARouterJumpActivity";
    public static final String URL_AROUTER_TEST_ACTIVITY = URL_PREFIX + "ARouterTestActivity";


}
