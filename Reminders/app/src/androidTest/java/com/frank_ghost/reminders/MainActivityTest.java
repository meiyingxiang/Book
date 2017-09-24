package com.frank_ghost.reminders;

import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by admin on 2017/9/24.
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2<RemindersActivity> {
    private RemindersActivity remindersActivity;
    private Button button;
    private static final String TAG = "MainActivityTest";

    public MainActivityTest() {
        super(RemindersActivity.class);
    }

    public MainActivityTest(Class activityClass) {
        super(activityClass);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        remindersActivity = getActivity();
        button = (Button) remindersActivity.findViewById(R.id.btn);
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(remindersActivity, "Test测试Sleep", Toast.LENGTH_LONG).show();
                }
            });
            SystemClock.sleep(1000000);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("USD|United Stated Dollar");
        arrayList.add("EUR|EURO");
        Intent mIntent = new Intent();
        mIntent.putExtra("key", arrayList);
        setActivityIntent(mIntent);
        remindersActivity = getActivity();
    }

    public void proxyCurrencyConverterTask(final String str) {
        remindersActivity.setCurrencyTaskCallback(new RemindersActivity.CurrencyTaskCallback() {
            @Override
            public void executionDone() {
                Log.e(TAG, "proxyCurrencyConverterTask execTask " + str);
            }
        });
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(remindersActivity, "Test", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void testInteger() throws Exception {
        proxyCurrencyConverterTask("12");
    }

    public void testFloat() throws Exception {
        proxyCurrencyConverterTask("12..3");
    }
}
