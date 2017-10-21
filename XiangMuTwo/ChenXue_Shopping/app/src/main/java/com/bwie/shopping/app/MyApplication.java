package com.bwie.shopping.app;

import android.app.Application;
import android.content.Context;

import com.bwie.shopping.bean.User;
import com.bwie.shopping.greendao.DaoMaster;
import com.bwie.shopping.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;
import org.xutils.x;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/4
 */

public class MyApplication extends Application{
    public final static String dbName = "test_db";
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化xutils
        x.Ext.init(this);
    }

    public static DaoMaster getDaoMaster(Context context)
    {
        if (daoMaster == null)
        {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, dbName, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context)
    {
        if (daoSession == null)
        {
            if (daoMaster == null)
            {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
