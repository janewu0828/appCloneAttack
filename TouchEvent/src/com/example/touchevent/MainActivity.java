package com.example.touchevent;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public boolean onTouchEvent(MotionEvent event) {
    	 
        float X = event.getX(); // 觸控的 X 軸位置
        float Y = event.getY(); // 觸控的 Y 軸位置
 
        switch (event.getAction()) { // 判斷觸控的動作
 
        case MotionEvent.ACTION_DOWN: // 按下
            downX = event.getX();
            downY = event.getY();
 
            return true;
        case MotionEvent.ACTION_MOVE: // 拖曳
 
            return true;
        case MotionEvent.ACTION_UP: // 放開
            Log.d("onTouchEvent-ACTION_UP","UP");
            upX = event.getX();
            upY = event.getY();
            float x=Math.abs(upX-downX);
            float y=Math.abs(upY-downY);
            double z=Math.sqrt(x*x+y*y);
            int jiaodu=Math.round((float)(Math.asin(y/z)/Math.PI*180));//角度
             
            if (upY < downY && jiaodu>45) {//上
                Log.d("onTouchEvent-ACTION_UP","角度:"+jiaodu+", 動作:上");
            }else if(upY > downY && jiaodu>45) {//下
                Log.d("onTouchEvent-ACTION_UP","角度:"+jiaodu+", 動作:下");
            }else if(upX < downX && jiaodu< =45) {//左
                Log.d("onTouchEvent-ACTION_UP","角度:"+jiaodu+", 動作:左");
                // 原方向不是向右時，方向轉右
                if (mDirection != EAST) {
                    mNextDirection = WEST;
                }
            }else if(upX > downX && jiaodu< =45) {//右
                Log.d("onTouchEvent-ACTION_UP","角度:"+jiaodu+", 動作:右");
                // 原方向不是向左時，方向轉右
                if (mDirection ! = WEST) {
                    mNextDirection = EAST;
                }
            }
            return true;
        }
 
        return super.onTouchEvent(event);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
