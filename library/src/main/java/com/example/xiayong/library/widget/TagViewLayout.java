package com.example.xiayong.library.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by xiayong on 11/2/15.
 * http://blog.csdn.net/lmj623565791/article/details/46858663
 */
public class TagViewLayout extends RelativeLayout {
//    private static final int CLICKRANGE = 5;

//    private Point startPoint;
    private ViewDragHelper dragHelper;


    public TagViewLayout(Context context) {
        super(context);
        init();
    }

    public TagViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
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
        return dragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            addItem((int)event.getX(),(int)event.getY());
        }
        dragHelper.processTouchEvent(event);
        return true;
    }
    public void addItemRandomly(){
        //TODO
    }
    private void addItem(int x, int y) {
        TagView view = null;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (x > getWidth() * 0.5) {
            params.leftMargin = x - TagView.getDefaultWidth();
            view = new TagView(getContext());
            view.setDirection(TagView.Direction.RIGHT);
        } else {
            params.leftMargin = x;
            view = new TagView(getContext());
            view.setDirection(TagView.Direction.LEFT);
        }

        params.topMargin = y;

        if (params.topMargin < 0) params.topMargin = 0;
        else if ((params.topMargin + TagView.getDefaultWidth()) > getHeight())
            params.topMargin = getHeight() - TagView.getDefaultHeight();


        this.addView(view, params);
    }
}
