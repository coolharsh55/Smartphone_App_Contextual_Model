package msc.prototype.contentprovider;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RecordMetric extends IntentService {

    SharedPreferences pref;
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "msc.prototype.contentprovider.action.FOO";
    private static final String ACTION_BAZ = "msc.prototype.contentprovider.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "msc.prototype.contentprovider.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "msc.prototype.contentprovider.extra.PARAM2";

    public static final String BROADCAST_ACTION = "msc.prototype.contentprovider.updateMetrics";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, RecordMetric.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, RecordMetric.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public RecordMetric() {
        super("RecordMetric");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("PA_CP_Service", "SERVICE STARTED");
        MainActivity.servicestatus = true;
//        pref = getSharedPreferences("RecordMetric", Context.MODE_PRIVATE);
//
//        if (pref.getLong("testval",0) == 0) {
//            Log.v("PA_CP_Service", "ERROR LOADING SHARED PREFERENCES");
//        }
//        Metrics.cQuery = pref.getLong("cQuery", 0);
//        Metrics.cInsert = pref.getLong("cInsert", 0);
//        Metrics.cQContact = pref.getLong("cQContact", 0);
//        Metrics.cQLocation = pref.getLong("cQLocation", 0);
//        Metrics.cQEvent = pref.getLong("cQEvent", 0);
//
//        Metrics.tQuery = pref.getLong("tQuery", 0);
//        Metrics.tInsert = pref.getLong("tInsert", 0);
//        Metrics.tReceive = pref.getLong("tReceive", 0);
//        Metrics.tTotal = pref.getLong("tTotal", 0);
//        Metrics.tQContact = pref.getLong("tQContact", 0);
//        Metrics.tIContact = pref.getLong("tIContact", 0);
//        Metrics.tQLocation = pref.getLong("tQLocation", 0);
//        Metrics.tILocation = pref.getLong("tILocation", 0);
//        Metrics.tQEvent = pref.getLong("tQEvent", 0);
//        Metrics.tIEvent = pref.getLong("tIEvent", 0);

//        if (Metrics.loadRowCount() == false) {
//            Log.v("PA_CP_Service", "ERROR LOADING ROWCOUNT FROM DATABASE");
//        }
//        Metrics.servicestatus = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.servicestatus = false;
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putLong("cQuery", Metrics.cQuery);
//        editor.putLong("cInsert", Metrics.cInsert);
//        editor.putLong("cQContact", Metrics.cQContact);
//        editor.putLong("cQLocation", Metrics.cQLocation);
//        editor.putLong("cQEvent", Metrics.cQEvent);
//
//        editor.putLong("tQuery", Metrics.tQuery);
//        editor.putLong("tInsert", Metrics.tInsert);
//        editor.putLong("tReceive", Metrics.tReceive);
//        editor.putLong("tTotal", Metrics.tTotal);
//        editor.putLong("tQContact", Metrics.tQContact);
//        editor.putLong("tIContact", Metrics.tIContact);
//        editor.putLong("tQLocation", Metrics.tQLocation);
//        editor.putLong("tILocation", Metrics.tILocation);
//        editor.putLong("tQEvent", Metrics.tQEvent);
//        editor.putLong("tIEvent", Metrics.tIEvent);
//
//        editor.putLong("testval",1);
//
//        editor.apply();
        Log.v("PA_CP_Service", "SERVICE STOPPED");
//        Metrics.servicestatus = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStart(intent, flags);
        return START_STICKY;
    }
}
