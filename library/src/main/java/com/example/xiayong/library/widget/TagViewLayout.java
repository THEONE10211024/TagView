package com.example.xiayong.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.xiayong.library.R;

/**
 * Created by xiayong on 11/2/15.
 * http://blog.csdn.net/lmj623565791/article/details/46858663
 */
public class TagViewLayout extends RelativeLayout implements View.OnTouchListener{

    private ImageView imgBackground;
    private ViewDragHelper dragHelper;
    private GestureDetector gestureDetector;


    public TagViewLayout(Context context) {
        super(context);
        init(context,null);
    }

    public TagViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagViewLayout);
        Drawable d = typedArray.getDrawable(R.styleable.TagViewLayout_tl_background);
        int scaleType_int = typedArray.getInt(R.styleable.TagViewLayout_tl_scaleType,0);
        typedArray.recycle();

        imgBackground = new ImageView(getContext());
        imgBackground.setImageDrawable(d);
        ImageView.ScaleType scaleType = ImageView.ScaleType.values()[scaleType_int];
        imgBackground.setScaleType(scaleType);
        LayoutParams imgParams=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        imgParams.addRule(CENTER_IN_PARENT, TRUE);
        addView(imgBackground, imgParams);

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(getContext(),"onDoubleTap",Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                Toast.makeText(getContext(),"onDoubleTapEvent",Toast.LENGTH_SHORT).show();
                return super.onDoubleTapEvent(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Toast.makeText(getContext(),"onSingleTapConfirmed",Toast.LENGTH_SHORT).show();
                return super.onSingleTapConfirmed(e);
            }
        });
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                if (child == imgBackground){
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
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.d("xiayong","onInterceptTouchEvent:"+dragHelper.shouldInterceptTouchEvent(event));
        return dragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }
    public void showAllTags(){
        //TODO
    }
    public void hideAllTags(){
        //TODO
    }
    public void addItemRandomly(){
        //TODO
        addItem(100, 100);
    }
    private void addItem(int x, int y) {
        TagView view = null;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.leftMargin = x;
        params.topMargin = y;
        view = new TagView(getContext());
        view.setDirection(TagView.Direction.LEFT);
        /*if (x > getWidth() * 0.5) {
            params.leftMargin = x - TagView.getDefaultWidth();
            view = new TagView(getContext());
            view.setDirection(TagView.Direction.RIGHT);
        } else {
            params.leftMargin = x;
            view = new TagView(getContext());
            view.setDirection(TagView.Direction.LEFT);
        }*/

        if (params.topMargin < 0) {
            params.topMargin = 0;
        }
        else if ((params.topMargin + TagView.getDefaultHeight()) > getHeight()){
            params.topMargin = getHeight() - TagView.getDefaultHeight();
        }

        if(params.leftMargin <0){
            params.leftMargin = 0;
        }else if((params.leftMargin + TagView.getDefaultWidth()>getWidth())){
            params.leftMargin = getWidth() - TagView.getDefaultWidth();
        }

//        view.setOnClickListener(this);//TODO TouchEvent conflicts with OnClick
        view.setOnTouchListener(this);
        this.addView(view, params);
        view.startAnimation();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
