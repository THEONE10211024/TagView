package com.example.xiayong.library.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.example.xiayong.library.R;

/**
 * Created by xiayong on 11/4/15.
 */
public class RippleCore extends ImageView {
    private static final int DEFAULT_DURATION_TIME=500;
    private static final float DEFAULT_SCALE=1.25f;
    private static final int DEFAULT_FILL_TYPE = 0;

    private boolean animationRunning=false;
    private AnimatorSet animatorSet;
    private int duration;
    private float scale;
    private Type type;

    public enum Type{
        TAG,
        PLACE,
        PEOPLE
    }

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
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleCore);
        int t=typedArray.getInt(R.styleable.RippleCore_rc_type,DEFAULT_FILL_TYPE);
        type = Type.values()[t];
        scale = typedArray.getFloat(R.styleable.RippleCore_rc_scale,DEFAULT_SCALE);
        duration = typedArray.getInt(R.styleable.RippleBackground_rb_duration,DEFAULT_DURATION_TIME);
        typedArray.recycle();

        setImageByType();
        animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(this, "ScaleX", 1.0f, 1.0f/scale,1.0f*scale,1.0f);
        final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(this, "ScaleY", 1.0f, 1.0f/scale,1.0f*scale,1.0f);
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);

    }
    private void setImageByType(){
        switch (type){
            case TAG:
                setImageResource(R.drawable.tag_custom_icon);
                break;
            case PLACE:
                setImageResource(R.drawable.tag_poi_icon);
                break;
            case PEOPLE:
                setImageResource(R.drawable.tag_user_icon);
                break;
        }
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

    private void setType(int t){
        switch (t){
            case 0:
                type = Type.TAG;
                break;
            case 1:
                type = Type.PLACE;
                break;
            case 2:
                type = Type.PEOPLE;
                break;
            default:
                break;
        }
    }
    public boolean isRippleAnimationRunning(){
        return animationRunning;
    }
}
