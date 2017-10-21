package com.bwie.shopping.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bwie.shopping.R;
import com.bwie.shopping.adapter.MyAdapter_GoodsLV;
import com.bwie.shopping.api.API;
import com.bwie.shopping.bean.AddressBean;
import com.bwie.shopping.bean.CartBean;
import com.bwie.shopping.bean.GoodsBean;
import com.bwie.shopping.bean.OrderBean;
import com.bwie.shopping.pay.H5PayDemoActivity;
import com.bwie.shopping.pay.PayResult;
import com.bwie.shopping.pay.SignUtils;
import com.bwie.shopping.utils.MyUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class CartPayActivity extends AppCompatActivity {

    private String cart_id, ifcart, address_id;
    private String vat_hash, offpay_hash, offpay_hash_batch;
    private String pay_name, invoice_id, voucher;
    private String pd_pay, password, fcode;
    private String rcb_pay, rpt, pay_message;

    private ImageView line;
    private TextView uname, uphone, address, cartCount;
    private ListView listView;
    private List<CartBean.DatasBean.CartListBean> payList;
    private Button cartOrder, okpay;
    private List<GoodsBean> goodslist;
    private View view;
    private PopupWindow popupWindow;
    private RadioGroup radioGroup;
    private RadioButton balipay,bwechat;

    // 商户PID
    public static final String PARTNER = "2017091508742922";
    // 商户收款账号
    public static final String SELLER = "8@qdbaiu.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCYL2c2ZO1rtd+3l3EIKvsGsfEJY5JdtHnV143tGYfGZJzX8s+f9n370THdCvdp6MoQzES1y075qvOgGHh3ALXJmdMwFZAbWaL2Zv35BkAbZGPjuCW7ZpUxIg77lL9BoCIy25XkNJ5SR6fFR59onStEnwO9whXdNxXMZ2CCKDt21K0XkhRfYwZ1d2FqpoTPygLYiajbGhaQHI71NXFGhQUc+44JwQpO0SUajDo2/Xmgw9pxJ84LykvbB1qwizNruN1WAl0AtyL64qouT0vHsOId+9qOrQCKd6m17+8ZfuB2VW3poilCFw4nW0nQqU5fCN1WKskZarC8I8h/4Kl89onjAgMBAAECggEAIaGgKoolWb9IoFbsWMS+0tzyCoQhrWmb3yESsUoKElLbwnFbZttfPg4d+d5ZjOMv/IRXzuEOI+iejEu8G3hhtBq1bBPn09N8Sv25tJWoByWpWYKxe/eQktoW+4wEtCkCRcD3IAZs0nwZXGSWmtTY4gJOHlpM4KUywQg7cKgZdAn2rteqx6RzMo5eZjLbO367UYPlT6hu73Va6cDS9jiNVORViZCEiTNEMDGTAte3HX++SFbaX+fUZx5w5UPn/R+car0Th6/befC7NAseFwjwXcWcZqnaMzhkk28zUrKR+272pty5LrKTjcNOs8sLjCgqmRAyF/Ro3WJKOeqvGOF40QKBgQDKJQIuNzxFD3ioSWlZeboMEM6mvHZ90vuZMJzOQ/gPBT5mBvxcR8EBGeuQat8hBL4myzfI13GT6xVAJJbYu90dT1oeF5TJxUABqwGxUW+vaR9VnWbhIvX8xSIJoacgvBD4LaupF9lF3h5lkO9dw7zGC0MduDPbhlszeQzyw09HGwKBgQDAuvufZQ73bvtmoFwGFjw+0qH7drj5Vudml15GpxGb8BSAw2YzSokSfsRY57KhRXK/iuijCW0FzO66M1lHdH380y0g3afaBHCP7gHA9RQnHcAlfN+obTSTr1GsqNwjYl28I/GM9HU1pA8e175VzC0XgCqEVL5pAHzrI9WxdyoM2QKBgQDCxD/CmdPHUq3Vr84CuQdqYrNJ8eJVP3jN1Anxr4mNp2bE1UxH+Zbb4caXgnF0leZE6J7aM8MqW2JBSD/TmcblW7Sfs+bXNeeC8+qQl799ilL3oc4jdBUBNEukbg3iu3G6Pf7c0K6249nz0F7hwBvd0bIcK4RDqsOq56JzMX3sKwKBgQCQzqzqWUsCfD+mH0ylQiWn6ijMwKpApZwdyBOMsFCZwqwVBzuNxC3zWI38YrIjXAPYiO08OoPQvOaNvrEQ8CUNRjn9HK3WMEnGysREj5/BuCe7wvRlHxBG+o46VXF5oJnfF36RMseyMv8tZFpdKpiitKi9XtLmH/i7z+PqgCd3GQKBgCKisIhb9Jj3ky2i4JPxU/4Rx6QBjQUkvJ44pC0oQ87gOP6ISb9j7nfcY7/hA300QedxL8cFYk1dfIUZkPd/l4mkKbcajHixeHUNfj1XrvQdLXGFlPInXFqseHi5M40s8h++cCSNacU3EoIN7UKURvghlPpd9GAeq8WsztShagZ7";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCd6rV3vOE578e6V" +
            "lGEakZpPdsX2QmGdIfi/yHe cg1CIEWzX9wn2LNFGtu1EzYQyKACG/RKeog0pUJEVGfBG30zFdNY2YocYJNdPtA" +
            "DqhJbS0GJm7f8 1vRiLKtOwKjdiz9oMEwxhc/5fysfMbercidRmlCDPU9BNL1UPb9bAx25JwIDAQAB";
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(CartPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(CartPayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(CartPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_pay);
        //  商户APP工程中引入微信JAR包，调用API前，需要先向微信注册您的APPID
        api = WXAPIFactory.createWXAPI(this, null);
        // 将该app注册到微信
        api.registerApp("wx8d99aede85c725a5");//wx8d99aede85c725a5
        //获取控件
        initView();
        //设置图片充满
        line.setScaleType(ImageView.ScaleType.FIT_XY);
        //请求得到收货地址，判断是否有默认地址
        postData();
        //得到传过来的值
        Intent intent = getIntent();
        goodslist = (List<GoodsBean>) intent.getSerializableExtra("goodslist");
        //得到合计金额
        float money = 0;
        for (int i = 0; i < goodslist.size(); i++) {
            money += Float.parseFloat(goodslist.get(i).getGoodsTotal());
        }
        cartCount.setText("￥" + money + "");
        //适配数据
        listView.setAdapter(new MyAdapter_GoodsLV(this, goodslist));
        //点击提交订单  向后台上传
        cartOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交订单  返回信息
                postServerData();
            }
        });

        //判断默认选择
        //popupWindow中点击监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.alipay:
                        postServerData2();
                        break;
                    case R.id.wechat:
                        postServerData2();
                        break;
                }
            }
        });

        okpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (balipay.isChecked()){
                    pay();
                }else if ((bwechat.isChecked())){
                    wechatPay("27839139829498");
                }
            }
        });
    }

    //获取控件
    private void initView() {
        line = (ImageView) findViewById(R.id.aff_line);
        uname = (TextView) findViewById(R.id.cart_username);
        uphone = (TextView) findViewById(R.id.cart_userphone);
        address = (TextView) findViewById(R.id.cart_address);
        listView = (ListView) findViewById(R.id.goods_lv);
        cartCount = (TextView) findViewById(R.id.cart_count);
        cartOrder = (Button) findViewById(R.id.cart_order);

        //得到布局
        view = View.inflate(this, R.layout.payway_popupwindow, null);
        //得到控件
        radioGroup = (RadioGroup) view.findViewById(R.id.payway);
        okpay = (Button) view.findViewById(R.id.okpay);
        balipay = (RadioButton) view.findViewById(R.id.alipay);
        bwechat = (RadioButton) view.findViewById(R.id.wechat);
        //实例popupwindow
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置popupWindow
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);

        bwechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //微信支付 专入订单号
                wechatPay("27839139829498");
            }
        });
    }

    //获取收货地址
    private void postData() {
        RequestParams params = new RequestParams(API.ADDRESS_PATH);
        params.addBodyParameter("key", MyUtils.getSharedPreferencesInstance(this).getString("key", ""));
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.optInt("code");
                    if (code == 200) {
                        Gson gson = new Gson();
                        AddressBean addressBean = gson.fromJson(result, AddressBean.class);
                        //遍历地址集合 找到默认地址
                        for (int i = 0; i < addressBean.getDatas().getAddress_list().size(); i++) {
                            if (MyUtils.getSharedPreferencesInstance(CartPayActivity.this).getString("address_id", "").equals(addressBean.getDatas().getAddress_list().get(i).getAddress_id())) {
                                //赋值
                                uname.setText("收货人" + addressBean.getDatas().getAddress_list().get(i).getTrue_name());
                                uphone.setText(addressBean.getDatas().getAddress_list().get(i).getMob_phone());
                                address.setText(addressBean.getDatas().getAddress_list().get(i).getAddress());
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //第一次请求  向后台提交订单信息
    private void postServerData() {
        RequestParams params = new RequestParams(API.PAY_ONE_PATH);
        params.addBodyParameter("key", MyUtils.getSharedPreferencesInstance(this).getString("key", ""));
        String cartid = "";
        for (int i = 0; i < goodslist.size(); i++) {
            if (i == 0) {
                cartid = goodslist.get(i).getCart_id() + "|" + goodslist.get(i).getGoodsNum();
            } else {
                cartid += "," + goodslist.get(i).getCart_id() + "|" + goodslist.get(i).getGoodsNum();
            }
        }
        params.addBodyParameter("cart_id", cartid);
        params.addBodyParameter("ifcart", "1");
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("",result);
                //得到后台订单信息
                Gson gson = new Gson();
                OrderBean orderBean = gson.fromJson(result, OrderBean.class);
                //参数赋值
                ifcart = "1";
                address_id = orderBean.getDatas().getAddress_info().getAddress_id();
                offpay_hash = orderBean.getDatas().getAddress_api().getOffpay_hash();
                offpay_hash_batch = orderBean.getDatas().getAddress_api().getOffpay_hash_batch();
                vat_hash = orderBean.getDatas().getVat_hash();
                pay_name = "online";
                fcode = "";
                rcb_pay = "0";
                voucher = "";
                pd_pay = "0";
                password = "";
                rpt = "";
                invoice_id = "0";
                for (int i = 0; i < orderBean.getDatas().getStore_cart_list().get_$1().getGoods_list().size(); i++) {
                    if (i == 0) {
                        cart_id = orderBean.getDatas().getStore_cart_list().get_$1().getGoods_list().get(i).getCart_id();
                        pay_message = orderBean.getDatas().getStore_cart_list().get_$1().getGoods_list().get(i).getStore_id();
                    } else {
                        cart_id += "," + orderBean.getDatas().getStore_cart_list().get_$1().getGoods_list().get(i).getCart_id();
                        pay_message += "|" + orderBean.getDatas().getStore_cart_list().get_$1().getGoods_list().get(i).getStore_id();
                    }
                }
                //弹出popupwindow
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                    postServerData2();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //第二次请求
    private void postServerData2() {
        RequestParams params = new RequestParams(API.PAY_SECONG_PATH);
        params.addBodyParameter("key", MyUtils.getSharedPreferencesInstance(this).getString("key", ""));
        params.addBodyParameter("ifcart", ifcart);
        params.addBodyParameter("address_id", address_id);
        params.addBodyParameter("vat_hash", vat_hash);
        params.addBodyParameter("offpay_hash", offpay_hash);
        params.addBodyParameter("offpay_hash_batch", offpay_hash_batch);
        params.addBodyParameter("pay_name", pay_name);
        params.addBodyParameter("invoice_id", invoice_id);
        params.addBodyParameter("voucher", voucher);
        params.addBodyParameter("pd_pay", pd_pay);
        params.addBodyParameter("password", password);
        params.addBodyParameter("fcode", fcode);
        params.addBodyParameter("rcb_pay", rcb_pay);
        params.addBodyParameter("rpt", rpt);
        params.addBodyParameter("pay_message", pay_message);
        params.addBodyParameter("cart_id",cart_id);
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("=-=-=-=-=-", result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //微信支付
    public void wechatPay(String order_sn) {
        String url = "http://www.55chai.com/morder/app_pay?order_sn=" + order_sn;//获取微信支付参数的服务端地址
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getApplicationContext(), url, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "订单生成失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("xml", responseString + "");

                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.getInt("code") == 200) {//正常获取了订单所需参数
                        JSONObject paramsJson = jsonObject.getJSONObject("data");

                        PayReq request = new PayReq();

                        request.appId = paramsJson.getString("appid");
                        request.partnerId = paramsJson.getString("partnerid");
                        request.prepayId = paramsJson.getString("prepayid");
                        request.packageValue = paramsJson.getString("package");
                        request.nonceStr = paramsJson.getString("noncestr");
                        request.timeStamp = paramsJson.getLong("timestamp") + "";
                        request.sign = paramsJson.getString("sign");

                        api.sendReq(request);
                    } else {
                        Toast.makeText(getApplicationContext(), "获取订单信息失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "生成支付失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay() {
        paySignFromServer();
        //客户端进行订单的签名
        //paySignFromClient();
    }

    private void paySignFromClient() {
        //获得订单信息
        String orderInfo = getOrderInfo("来自Client测试商品", "购买一部手机", "0.01");
        //进行加密签名
        String sign = sign(orderInfo);
        //通过URLEncoder进行编码
        try {
            sign = URLEncoder.encode(sign, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //拼装最终的支付信息
        StringBuffer sb = new StringBuffer(orderInfo);
        sb.append("&sign=\"");
        sb.append(sign);
        sb.append("\"&");
        sb.append(getSignType());
        //获取必须来自服务端
        final String payInfo = sb.toString();//获得最终的支付信息
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(CartPayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);
                Log.i("TAG", "走了pay支付方法.............");

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void paySignFromServer() {
        //添加参数，暂时写死，项目中，从页面获取
        String url = "http://169.254.26.25:8080/PayServer/AlipayDemo";
        StringBuffer sb = new StringBuffer("?");
        sb.append("subject=");
        sb.append("来自Server测试的商品");
        sb.append("&");
        sb.append("body=");
        sb.append("该测试商品的详细描述");
        sb.append("&");
        sb.append("total_fee=");
        sb.append("0.01");
        url = url + sb.toString();
        //到服务器进行订单加密

        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                // 获取必须来自服务端
                final String signResult = result;
                Log.i("TAG", signResult);

                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(CartPayActivity.this);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(signResult, true);
                        Log.i("TAG", "走了pay支付方法.............");

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
