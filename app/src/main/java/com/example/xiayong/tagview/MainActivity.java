package com.example.xiayong.tagview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.xiayong.library.widget.TagViewLayout;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TagViewLayout tagViewLayout;
    private Button showTag;
    private Button addTag;
    private Button hideTag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tagViewLayout = (TagViewLayout) findViewById(R.id.tagviewLayout);
        showTag = (Button) findViewById(R.id.btn_show_tag);
        addTag = (Button) findViewById(R.id.btn_add);
        hideTag = (Button) findViewById(R.id.btn_hide_tag);
        showTag.setOnClickListener(this);
        addTag.setOnClickListener(this);
        hideTag.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_show_tag:
                break;
            case R.id.btn_add:
                tagViewLayout.addItemRandomly();
                break;
            case R.id.btn_hide_tag:
                break;
        }

    }
}
