package com.frank_ghost.reminders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RemindersActivity extends AppCompatActivity {
    private final static String TAG = "RemindersActivity";

    private ListView mList;
    private CurrencyTaskCallback callback;

    public void setCurrencyTaskCallback(CurrencyTaskCallback currencyTaskCallback) {
        this.callback = currencyTaskCallback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        mList = ((ListView) findViewById(R.id.reminders_list_view));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RemindersActivity.this,
                R.layout.reminders_row, R.id.row_text, new String[]{"first record", "second record", "third record"});
        mList.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                //155 3216  5390
                finish();
                break;
            case R.id.action_new:
                Log.e(TAG, "create new reminder");
                if (callback != null) {
                    callback.executionDone();
                    Log.e(TAG, "回掉");
                }
                break;
            default:
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_reminders, menu);
        return true;
    }

    public static interface CurrencyTaskCallback {
        void executionDone();
    }
}
