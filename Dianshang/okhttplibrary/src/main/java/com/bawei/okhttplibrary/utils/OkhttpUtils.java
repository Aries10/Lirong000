package com.bawei.okhttplibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.bawei.okhttplibrary.app.MyApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 封装okhttp工具类
 * Created by dell-pc on 2017/10/7.
 */

public class OkhttpUtils {

    /**
     * 懒汉、安全  加同步
     * 私有的静态成员变量 只声明不创建
     * 私有的构造方法
     * 提供返回实例的静态方法
     */
    private static OkhttpUtils okhttpUtils = null;
    private static OkHttpClient okHttpClient = null;

    private OkhttpUtils(){

    }

    public static OkhttpUtils getInstance(){
        if(okhttpUtils == null){
            synchronized (OkhttpUtils.class){
                if(okhttpUtils == null){
                    okhttpUtils = new OkhttpUtils();
                }
            }
        }
        return okhttpUtils;
    }

    public synchronized static OkHttpClient getClientInstance(){
        if(okHttpClient == null){
/**
 * 和OkHttp2.x有区别的是不能通过OkHttpClient直接设置超时时间和缓存了，而是通过OkHttpClient.Builder来设置，
 * 通过builder配置好OkHttpClient后用builder.build()来返回OkHttpClient，
 * 所以我们通常不会调用new OkHttpClient()来得到OkHttpClient，而是通过builder.build()：
 */
            //缓存目录
            File file = new File(Environment.getExternalStorageDirectory(),"cache");
            int cacheSize = 10 * 1024 * 1024;

            //okhttp3拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("xxx",message.toString());
                }
            });

            //Okhttp3拦截器日志分类  4种
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(15,TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor) //添加拦截器
                    .addNetworkInterceptor(new CacheInterceptor()) //添加网络拦截器
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20,TimeUnit.SECONDS)
                    .cache(new Cache(file.getAbsoluteFile(),cacheSize))
                    .build();
        }
        return okHttpClient;
    }

    private static Handler handler = null;

    public synchronized static Handler getHandler(){
        if(handler == null){
            handler = new Handler();
        }
        return handler;
    }

    /**
     * get请求数据
     */
    public static void doGet(String url, Callback callback){
        OkHttpClient instance = getClientInstance();
        Request request = new Request.Builder().url(url).build();
        Call call = instance.newCall(request);
        call.enqueue(callback);
    }

    /**
     * post请求数据
     */
    public static void doPost(Map<String,String> map,String url, Callback callback){
        OkHttpClient clientInstance = getClientInstance();
        FormBody.Builder builder = new FormBody.Builder();
        //将参数存入集合中
        for (String key:map.keySet()){
            String value = map.get(key);
            builder.add(key,value);
        }

        FormBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = clientInstance.newCall(request);
        call.enqueue(callback);

    }

    /**
     * post请求上传文件
     * @param url
     * @param file
     * @param fileName
     */
    public static void uploadPic(String url,File file,String fileName){
        //创建okHttpClient请求对象
        OkHttpClient okHttpClient = getClientInstance();
        //创建RequestBody 封装file参数
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        //创建RequestBody 设置类型等
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file",fileName,fileBody).build();
        //创建Request
        Request request = new Request.Builder().url(url).post(requestBody).build();

        //得到Call
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //上传成功回调 目前不需要处理
            }
        });
    }

    /**
     * post请求发送JSON数据
     * @param url  请求url
     * @param jsonParams 请求的Json
     * @param callback 请求回调
     */
    public static void doPostJson(String url, String jsonParams, Callback callback){
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = getClientInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     *下载文件 以流的形式把apk写入的指定文件  得到file后进行安装
     * @param context
     * @param url  保存文件的路径名
     * @param saveDir 保存文件的文件名
     */
    public static void download(final Context context, final String url, final String saveDir){
        Request request = new Request.Builder().url(url).build();
        Call call = getClientInstance().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("xxx",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buffer = new byte[1024];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    //apk保存路径
                    String fileDir = isExistDir(saveDir);
                    //文件
                    File file = new File(fileDir,getNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    while((len = is.read(buffer)) != -1){
                        fos.write(buffer,0,len);
                    }
                    fos.flush();
                    //apk下载完成后， 调用系统的安装方法
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
                    context.startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(is != null) is.close();
                    if(fos != null) fos.close();
                }
            }
        });
    }

    /**
     * 判断下载目录是否存在
     * @param saveDir
     * @return
     */
    public static String isExistDir(String saveDir) throws IOException {
        //下载位置
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File downloadFile = new File(Environment.getExternalStorageDirectory(),saveDir);
            if(!downloadFile.mkdirs()){ //不存在就创建目录
                downloadFile.createNewFile();
            }
            String savePath = downloadFile.getAbsolutePath();
            Log.e("savaPath",savePath);
            return savePath;
        }
        return null;
    }

    /**
     * 从下载链接中解析出文件名
     * @param url
     * @return
     */
    private static String getNameFromUrl(String url){
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            //有网络时  设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            //无网络时设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if(NetWorkUtils.isNetWorkAvailable(MyApp.getInatance())){
                //有网时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            }else{
                //没网时从缓存中获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            Response response = chain.proceed(request);
            if(NetWorkUtils.isNetWorkAvailable(MyApp.getInatance())){
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public, max-age="+maxAge)
                        .build();
            }else{
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public, only-if-cached, max-stale="+maxStale)
                        .build();
            }

            return response;
        }
    }

}
