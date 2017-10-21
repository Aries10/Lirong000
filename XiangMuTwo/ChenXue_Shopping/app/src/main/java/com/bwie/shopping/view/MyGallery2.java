package com.bwie.shopping.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/8
 */

public class MyGallery2 extends Gallery{
    /**
     * 这里的数值，限制了每次滚动的最大长度，图片宽度为480PX。这里设置600效果好一些。 这个值越大，滚动的长度就越大。
     * 也就是会出现一次滚动跨多个Image。这里限制长度后，每次滚动只能跨一个Image
     */
    private static final int timerAnimation = 1;
    private final Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case timerAnimation:
                    int position = getSelectedItemPosition();
                    Log.i("msg", "position:"+position);
                    if (position >= (getCount() - 1))
                    {
                        onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
                    } else
                    {
                        onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                    }
                    break;

                default:
                    break;
            }
        }
    };

    private final Timer timer = new Timer();
    private final TimerTask task = new TimerTask()
    {
        public void run()
        {
            mHandler.sendEmptyMessage(timerAnimation);
        }
    };

    public MyGallery2(Context context) {
        super(context);
        timer.schedule(task, 300000, 300000);
    }

    public MyGallery2(Context context, AttributeSet attrs) {
        super(context, attrs);
        timer.schedule(task, 300000, 300000);
    }

    public MyGallery2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        timer.schedule(task, 300000, 300000);
    }

    private boolean isScrollingLeft(MotionEvent paramMotionEvent1,
                                    MotionEvent paramMotionEvent2)
    {
        float f2 = paramMotionEvent2.getX();
        float f1 = paramMotionEvent1.getX();
        if (f2 > f1)
            return true;
        return false;
    }

    public boolean onFling(MotionEvent paramMotionEvent1,
                           MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
    {
        int keyCode;
        if (isScrollingLeft(paramMotionEvent1, paramMotionEvent2))
        {
            keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
        } else
        {
            keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(keyCode, null);
        return true;
    }
}
