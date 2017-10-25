package com.bawei.myproject.utils;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.myproject.MainActivity;
import com.bawei.myproject.PhotoViewActivity;
import com.bawei.myproject.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 点击头像退出登录
 * Created by dell-pc on 2017/9/16.
 */

public class FinishLogin {

    public static void showDialog(final MainActivity context, WindowManager windowManager, final SlidingMenu slidingMenu, final ImageView img_touxiang) {
        View view = View.inflate(context,R.layout.finishlogin, null);
        final Dialog dialog = new Dialog(context, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = windowManager.getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        //退出登录
        view.findViewById(R.id.bt_finishlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                slidingMenu.findViewById(R.id.ll_head02).setVisibility(View.GONE);
                slidingMenu.findViewById(R.id.ll_head01).setVisibility(View.VISIBLE);
                img_touxiang.setImageResource(R.mipmap.touxiang);
                Toast.makeText(context,"已退出登录",Toast.LENGTH_SHORT).show();
            }
        });

        //查看头像
        view.findViewById(R.id.bt_lookimg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PhotoViewActivity.class);
                context.startActivity(intent);
            }
        });

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

}
