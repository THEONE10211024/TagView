package com.example.xiayong.library.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xiayong.library.R;

/**
 * Created by xiayong on 11/2/15.
 * TODO 1.添加单击改变方向功能；2.双击TagView时切换Edit和Normal状态；3.需要添加工厂模式创建TagView么？
 * TODO 1.处理UI，更美观和灵活 2.双击TagViewLayout的空白页面实现TagView的显示和隐藏切换
 */
public class TagView extends RelativeLayout implements TextView.OnEditorActionListener {

    private TextView tvTagLabel;
    private EditText edTagLabel;
    private RelativeLayout loTag;
    private RippleBackground rippleContent;
    private Direction direction = Direction.LEFT;
    private Status status = Status.NORMAL;
    private InputMethodManager imm;
    private RippleCore imgLeftDrawable;
    private float alpha;//TODO
    private LayoutParams loTagParams;

    private static final int DEFAULT_WIDTH = 80;
    private static final int DEFAULT_HEIGHT = 50;
    private static final float DEFAULT_ALPHA = 0.8f;


    public enum Status {NORMAL, EDIT}

    ;

    public enum Direction {LEFT, RIGHT}

    ;

    public TagView(Context context) {
        super(context);
        init(context, null);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        initView(context);
        initEvents();
        directionChange();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.picture_tagview, this, true);
        tvTagLabel = (TextView) findViewById(R.id.tvPictureTagLabel);
        edTagLabel = (EditText) findViewById(R.id.etPictureTagLabel);
        loTag = (RelativeLayout) findViewById(R.id.loTag);
        loTag.setAlpha(0.8f);//TODO
        rippleContent = (RippleBackground) findViewById(R.id.ripple_content);
        imgLeftDrawable = (RippleCore) findViewById(R.id.img_left_drawable);
        loTagParams = (LayoutParams) loTag.getLayoutParams();
    }

    private void directionChange() {
        //TODO 根据direction来判断左旋转还是右旋转
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "rotationY",0f,180f);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setDuration(500);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                resetText();
            }
        });
        objectAnimator.start();
    }


    private void resetText(){
       //TODO
    }
    public void show(){
        //TODO
    }
    public void hide(){

    }
    public void startAnimation() {

        Animator backgroundAnimator = rippleContent.getRippleAnimator();
        Animator coreAnimator = imgLeftDrawable.getCoreAnimator();
        backgroundAnimator.removeAllListeners();
        coreAnimator.removeAllListeners();
        rippleContent.stopRippleAnimation();
        imgLeftDrawable.stopRippleAnimation();

        backgroundAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rippleContent.setAnimationRunning(false);
                imgLeftDrawable.startRippleAnimation();
            }
        });
        coreAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imgLeftDrawable.setAnimationRunning(false);
                rippleContent.startRippleAnimation();
            }
        });

        imgLeftDrawable.startRippleAnimation();


    }

    public void setDirection(Direction direction) {
        if (this.direction != direction) {
            this.direction = direction;
            directionChange();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        setStatus(Status.NORMAL);
        return true;
    }

    /**
     * 初始化事件 *
     */
    protected void initEvents() {
        edTagLabel.setOnEditorActionListener(this);
    }

    public void setStatus(Status status) {
        switch (status) {
            case NORMAL:
                tvTagLabel.setVisibility(View.VISIBLE);
                edTagLabel.clearFocus();
                tvTagLabel.setText(edTagLabel.getText());
                edTagLabel.setVisibility(View.GONE);
                //隐藏键盘
                imm.hideSoftInputFromWindow(edTagLabel.getWindowToken(), 0);
                break;
            case EDIT:
                tvTagLabel.setVisibility(View.GONE);
                edTagLabel.setVisibility(View.VISIBLE);
                edTagLabel.requestFocus();
                //弹出键盘
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                break;
        }
    }

    /*@Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View parent = (View) getParent();
        int halfParentW = (int) (parent.getWidth()*0.5);
        int center = (int) (l + (this.getWidth()*0.5));
        if(center<=halfParentW){
            direction = Direction.LEFT;
        }
        else{
            direction = Direction.RIGHT;
        }
        directionChange();
    }*/
    public static int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    public static int getDefaultHeight() {
        return DEFAULT_HEIGHT;
    }
}