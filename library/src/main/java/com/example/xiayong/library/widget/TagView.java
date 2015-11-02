package com.example.xiayong.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by xiayong on 11/2/15.
 */
public class TagView extends RelativeLayout{

    private Context context;
    private TextView tvTagLabel;
    private EditText edTagLabel;
    private 

    public TagView(Context context) {
        super(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
