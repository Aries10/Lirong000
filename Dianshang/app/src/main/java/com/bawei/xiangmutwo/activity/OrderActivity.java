package com.bawei.xiangmutwo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bawei.okhttplibrary.utils.OkhttpUtils;
import com.bawei.xiangmutwo.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView aff_back;
    private TextView order_name;
    private TextView order_phone;
    private TextView order_address;
    private LinearLayout ll_orderaddress;
    private TextView aff_shopname;
    private ImageView order_goodspic;
    private TextView order_goodsname;
    private TextView order_goodsprice;
    private TextView order_goodsnum;
    private Button bt_order;
    private TextView order_count;
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();

        Intent intent = getIntent();
        String pay = intent.getStringExtra("pay");
        if(pay.equals("buynow")){
            String goodsname = intent.getStringExtra("goodsname");
            String goodsprice = intent.getStringExtra("goodsprice");
            String goodspic = intent.getStringExtra("goodspic");
            order_goodsname.setText(goodsname);
            order_goodsprice.setText(goodsprice);
//            ImageLoader.getInstance().displayImage(goodspic,order_goodspic, MyApp.getDisplay());
        }
    }

    private void initView() {
        aff_back = (ImageView) findViewById(R.id.aff_back);
        order_name = (TextView) findViewById(R.id.order_name);
        order_phone = (TextView) findViewById(R.id.order_phone);
        order_address = (TextView) findViewById(R.id.order_address);
        ll_orderaddress = (LinearLayout) findViewById(R.id.ll_orderaddress);
        aff_shopname = (TextView) findViewById(R.id.aff_shopname);
        order_goodspic = (ImageView) findViewById(R.id.order_goodspic);
        order_goodsname = (TextView) findViewById(R.id.order_goodsname);
        order_goodsprice = (TextView) findViewById(R.id.order_goodsprice);
        order_goodsnum = (TextView) findViewById(R.id.order_goodsnum);
        bt_order = (Button) findViewById(R.id.bt_order);
        order_count = (TextView) findViewById(R.id.order_count);

        bt_order.setOnClickListener(this);
        ll_orderaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderActivity.this,"点击了条目",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderActivity.this,AddressActivity.class);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_order:
                //立即支付
                postData();
                break;
            case R.id.aff_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 3){
            String name = data.getStringExtra("order_name");
            String phone = data.getStringExtra("order_phone");
            String address = data.getStringExtra("order_address");
            order_name.setText(name);
            order_phone.setText(phone);
            order_address.setText(address);
        }
    }

    private void postData() {
        //添加参数，url中的ip可以换成我们自己的后台ip
        String url = "http://169.254.255.250:8080/PayServer/AlipayDemo";
        StringBuffer sb = new StringBuffer("?");
        sb.append("subject=");
        sb.append("来自Server测试的商品");
        sb.append("&");
        sb.append("body=");
        sb.append("该测试商品的详细描述");
        sb.append("&");
        sb.append("total_fee=");
        sb.append("0.01");
        String urll = url + sb.toString();

        OkhttpUtils.doGet(urll, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(OrderActivity.this);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(string, true);
                        //  Log.i("TAG", "走了pay支付方法.............");

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
        });

    }
    //这里需要一个handler
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
                        Toast.makeText(OrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }


    };

}
