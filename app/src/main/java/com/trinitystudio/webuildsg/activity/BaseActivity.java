package com.trinitystudio.webuildsg.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.trinitystudio.webuildsg.receiver.NetworkChangeReceiver;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Licco on 22/12/15.
 */
public class BaseActivity extends AppCompatActivity {

    private NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    private IntentFilter networkIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkChangeReceiver, networkIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int screenCoordinates[] = new int[2];
            w.getLocationOnScreen(screenCoordinates);
            float x = ev.getRawX() + w.getLeft() - screenCoordinates[0];
            float y = ev.getRawY() + w.getTop() - screenCoordinates[1];

            if (ev.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom())) {
                view.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return true;
        }
    }

    public void showSnackBar(String text, CoordinatorLayout coordinatorLayout) {
        Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_LONG).show();
    }
}
