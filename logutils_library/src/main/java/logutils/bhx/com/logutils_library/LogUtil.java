package logutils.bhx.com.logutils_library;

import android.util.Log;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/10/8 18:34
 */

public class LogUtil {
    static String className;
    static String methodName;
    static int lineNumber;

    public LogUtil() {

    }

    //控制是否打印日志
    public static boolean isDebuggable() {
        return true;
    }
    //日志的格式
    private static String createLog( String log ) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("DebugLog");
        buffer.append("[");
        buffer.append(methodName);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }
    //获取文件名名、方法名、行数
    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }
    //打印异常
    public static void e(String message){
        if (!isDebuggable())
            return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }
    //打印重要的数据
    public static void i(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }
    //打印一些调试的信息
    public static void d(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }
    //打印那些最为繁琐的，意义最小的日志信息
    public static void v(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }
    //用于打印一些警告信息
    public static void w(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }
}
