package com.example.xiayong.library.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by xiayong on 11/4/15.
 */
public class RippleCore extends ImageView {
    private static final int DEFAULT_DURATION_TIME=3000;
    private static final float DEFAULT_SCALE=6.0f;

    private boolean animationRunning=false;
    private AnimatorSet animatorSet;
    private int duration;
    private float scale;

    public RippleCore(Context context) {
        super(context);
        init(context, null);
    }

    public RippleCore(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RippleCore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public RippleCore(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        scale = 1.5f;
        duration = 1000;
        animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(this, "ScaleX", 1.0f, scale);
        scaleXAnimator.setRepeatCount(1);
//        scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
//        scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);
//        scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
        final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(this, "ScaleY", 1.0f, scale);
//        scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);
//        scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
        scaleYAnimator.setRepeatCount(1);
//        scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);

        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
    }
    public Animator getCoreAnimator(){
        return animatorSet;
    }

    public void setAnimationRunning(boolean running){
        this.animationRunning = running;
    }
    public void startRippleAnimation(){
        if(!isRippleAnimationRunning()){
            animatorSet.start();
            animationRunning=true;
        }
    }

    public void stopRippleAnimation(){
        if(isRippleAnimationRunning()){
            animatorSet.end();
            animationRunning=false;
        }
    }

    public boolean isRippleAnimationRunning(){
        return animationRunning;
    }
}
