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
    	 
        float X = event.getX(); // Ĳ���� X �b��m
        float Y = event.getY(); // Ĳ���� Y �b��m
 
        switch (event.getAction()) { // �P�_Ĳ�����ʧ@
 
        case MotionEvent.ACTION_DOWN: // ���U
            downX = event.getX();
            downY = event.getY();
 
            return true;
        case MotionEvent.ACTION_MOVE: // �즲
 
            return true;
        case MotionEvent.ACTION_UP: // ��}
            Log.d("onTouchEvent-ACTION_UP","UP");
            upX = event.getX();
            upY = event.getY();
            float x=Math.abs(upX-downX);
            float y=Math.abs(upY-downY);
            double z=Math.sqrt(x*x+y*y);
            int jiaodu=Math.round((float)(Math.asin(y/z)/Math.PI*180));//����
             
            if (upY < downY && jiaodu>45) {//�W
                Log.d("onTouchEvent-ACTION_UP","����:"+jiaodu+", �ʧ@:�W");
            }else if(upY > downY && jiaodu>45) {//�U
                Log.d("onTouchEvent-ACTION_UP","����:"+jiaodu+", �ʧ@:�U");
            }else if(upX < downX && jiaodu< =45) {//��
                Log.d("onTouchEvent-ACTION_UP","����:"+jiaodu+", �ʧ@:��");
                // ���V���O�V�k�ɡA��V��k
                if (mDirection != EAST) {
                    mNextDirection = WEST;
                }
            }else if(upX > downX && jiaodu< =45) {//�k
                Log.d("onTouchEvent-ACTION_UP","����:"+jiaodu+", �ʧ@:�k");
                // ���V���O�V���ɡA��V��k
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
