package com.example.xiayong.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by xiayong on 11/2/15.
 */
public class TagView extends RelativeLayout implements TextView.OnEditorActionListener {

    private TextView tvTagLabel;
    private EditText edTagLabel;
    private View loTag;
    private Direction direction = Direction.LEFT;
    private InputMethodManager imm;

    private static final int DEFAULT_WIDTH = 80;
    private static final int DEFAULT_HEIGHT = 50;

    public enum Status{NORMAL,EDIT};
    public enum Direction{LEFT,RIGHT};

    public TagView(Context context) {
        super(context);
        init(context,null);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        directionChange();
        initView(context);
        initEvents();
    }
    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.picturetagview, this,true);
        tvTagLabel = (TextView) findViewById(R.id.tvPictureTagLabel);
        edTagLabel = (EditText) findViewById(R.id.etPictureTagLabel);
        loTag = findViewById(R.id.loTag);
    }
    private void directionChange(){
        switch(direction){
            case LEFT:
                loTag.setBackgroundResource(R.drawable.bg_picturetagview_tagview_left);
                break;
            case RIGHT:
                loTag.setBackgroundResource(R.drawable.bg_picturetagview_tagview_right);
                break;
        }
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        setStatus(Status.NORMAL);
        return true;
    }
    /** ��ʼ���¼� **/
    protected void initEvents(){
        edTagLabel.setOnEditorActionListener(this);
    }
    public void setStatus(Status status){
        switch(status){
            case NORMAL:
                tvPictureTagLabel.setVisibility(View.VISIBLE);
                etPictureTagLabel.clearFocus();
                tvPictureTagLabel.setText(etPictureTagLabel.getText());
                etPictureTagLabel.setVisibility(View.GONE);
                //���ؼ���
                imm.hideSoftInputFromWindow(etPictureTagLabel.getWindowToken() , 0);
                break;
            case EDIT:
                tvPictureTagLabel.setVisibility(View.GONE);
                etPictureTagLabel.setVisibility(View.VISIBLE);
                etPictureTagLabel.requestFocus();
                //��������
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                break;
        }
    }
    @Override
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
    }
    public static int getDefaultWidth(){
        return DEFAULT_WIDTH;
    }
    public static int getDefaultHeight(){
        return DEFAULT_HEIGHT;
    }
}
