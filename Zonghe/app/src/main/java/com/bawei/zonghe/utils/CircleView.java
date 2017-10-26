package com.bawei.zonghe.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bawei.zonghe.activity.MainActivity;

/**
 * Created by dell-pc on 2017/10/25.
 */

public class CircleView extends View {

    /**
     * 定义画笔
     */
    private Paint paint;

    private Point currentPoint;

    public static final float RADIUS = 50f;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 定义原点类
     */
    public class Point{
        private float x;
        private float y;
        public Point(float x,float y){
            this.x=x;
            this.y=y;
        }
        public float getX(){
            return x;
        }
        public float getY(){
            return y;
        }
    }

    /**
     * 在自定义模式里计算从开始到结束 原点的差值
     */
    class PointEvalustor implements TypeEvaluator {
        /**
         *   值 = 开始值 + 分段 * (结束值 - 开始值)
         * @param fraction  部分
         * @param startValue  开始值
         * @param endValue  结束值
         * @return  返回原点类对象
         */
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Point startPoint= (Point) startValue;
            Point endPoint= (Point) endValue;

            float x=startPoint.getX()+fraction*(endPoint.getX()-startPoint.getX());
            float y=startPoint.getY()+fraction*(endPoint.getY()-startPoint.getY());

            return new Point(x,y);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(currentPoint == null){
            paint.setColor(Color.BLUE);
            currentPoint = new Point(RADIUS,RADIUS);
            canvas.drawCircle(currentPoint.getX(),currentPoint.getY(),RADIUS,paint);
            //设置动画效果的方法
            startAnim();
        }else{
            canvas.drawCircle(currentPoint.getX(),currentPoint.getY(),RADIUS,paint);
        }
    }

    /**
     * 设置动画效果的方法
     */
    private void startAnim() {
        Point startPoint = new Point(RADIUS,RADIUS);
        Point endPoint = new Point(getWidth()-RADIUS,getHeight()-RADIUS);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvalustor(), startPoint, endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if(valueAnimator != null){
                    currentPoint = (Point) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(valueAnimator);
        animatorSet.setDuration(5000);
        animatorSet.start();

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //动画结束后  跳转至下一页面
                getContext().startActivity(new Intent(getContext(), MainActivity.class));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }
}
