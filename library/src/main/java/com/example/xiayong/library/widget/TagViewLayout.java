package com.example.xiayong.library.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.xiayong.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiayong on 11/2/15.
 * http://blog.csdn.net/lmj623565791/article/details/46858663
 * http://stackoverflow.com/questions/22865291/why-is-view-dragged-with-viewdraghelper-reset-to-its-original-position-on-layout
 */
public class TagViewLayout extends RelativeLayout implements View.OnTouchListener{

    private List<TagView> tagViews;
    private ImageView imgBackground;
    private ViewDragHelper dragHelper;
    private GestureDetector gestureDetector;


    public TagViewLayout(Context context) {
        super(context);
        init(context, null);
    }

    public TagViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagViewLayout);
        Drawable d = typedArray.getDrawable(R.styleable.TagViewLayout_tl_background);
        int scaleType_int = typedArray.getInt(R.styleable.TagViewLayout_tl_scaleType, 0);
        typedArray.recycle();

        tagViews = new ArrayList<>();
        imgBackground = new ImageView(getContext());
        imgBackground.setImageDrawable(d);
        ImageView.ScaleType scaleType = ImageView.ScaleType.values()[scaleType_int];
        imgBackground.setScaleType(scaleType);
        LayoutParams imgParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imgParams.addRule(CENTER_IN_PARENT, TRUE);
        addView(imgBackground, imgParams);

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d("xiayong_g","onDoubleTap");
                tagViews.get(0).setDirection(TagView.Direction.LEFT);
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d("xiayong_g","onSingleTapConfirmed");
                tagViews.get(0).setDirection(TagView.Direction.RIGHT);
                return true;
            }
        });
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                if (child == imgBackground) {
                    return false;
                }
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - leftBound;

                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.d("xiayong", "onInterceptTouchEvent:" + dragHelper.shouldInterceptTouchEvent(event));
        return dragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("xiayong", "onTouchEvent");
        dragHelper.processTouchEvent(event);
        return true;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("xiayong", "onTouch:" + gestureDetector.onTouchEvent(event));
        return gestureDetector.onTouchEvent(event);
    }

    public void showAllTags() {
        //TODO
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(this, "ScaleX", 1.0f, 1.1f,0.0f);
        final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(this, "ScaleY", 1.0f, 1.1f,0.0f);
//        animatorSet.add
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
    }

    public void hideAllTags() {
        //TODO
    }

    public void addItemRandomly() {
        //TODO
        addItem(300, 300);
    }

    private void addItem(int x, int y) {
        TagView view = null;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.leftMargin = x;
        params.topMargin = y;
        view = new TagView(getContext());
        view.setDirection(TagView.Direction.LEFT);
        if (params.topMargin < 0) {
            params.topMargin = 0;
        } else if ((params.topMargin + TagView.getDefaultHeight()) > getHeight()) {
            params.topMargin = getHeight() - TagView.getDefaultHeight();
        }

        if (params.leftMargin < 0) {
            params.leftMargin = 0;
        } else if ((params.leftMargin + TagView.getDefaultWidth() > getWidth())) {
            params.leftMargin = getWidth() - TagView.getDefaultWidth();
        }

        view.setOnTouchListener(this);
        view.setClickable(true);
        tagViews.add(view);
        this.addView(view, params);
        view.startAnimation();
    }
}
