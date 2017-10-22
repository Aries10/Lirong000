package test.bwie.com.errorinfostudy;

import android.app.Application;

/**
 * 1.类的用途
 * 2.@author1
 * 3.@data2017/10/4 22:42
 */
public class Applation extends Application {
    private final static float HEAP_UTILIZATION = 0.75f;
    private final static int MIN_HEAP_SIZE = 6* 1024* 1024 ;
    @Override
    public void onCreate() {
        super.onCreate();

              // 异常处理，不需要处理时注释掉这两句即可！
        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(getApplicationContext());
        LogToFile.init(getApplicationContext());

    }
}
