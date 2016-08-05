package msc.prototype.contentprovider;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import msc.prototype.contextprovider.R;

public class MainActivity extends Activity {

    static boolean servicestatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BroadcastReceiver broadcastreceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateUI();
            }
        };
        //registerReceiver(broadcastreceiver, new IntentFilter(RecordMetric.BROADCAST_ACTION));
        startService(new Intent(this, RecordMetric.class));
        updateUI();
    }

    private void updateUI() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    public void click(View view) {
        updateUI();
    }

    public void serviceOp(View view) {
        ToggleButton tb = (ToggleButton) findViewById(R.id.tbService);
        if (tb.isChecked()) {
            stopService(new Intent(this, RecordMetric.class));
            //tb.setChecked(false);
        } else {
            startService(new Intent(this, RecordMetric.class));
            //tb.setChecked(true);
        }

    }
}
