package msc.context.demo.message;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import msc.prototype.context.ContactData;
import msc.prototype.context.ContextManager;
import msc.prototype.context.EventData;
import msc.prototype.context.LocationData;


public class Compose extends Activity {
    ContentResolver contentresolver;
    EditText etSms;
    ArrayList<EventData> events;
    int selectedEvent = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compoase);
        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
        }
        etSms = (EditText) findViewById(R.id.etSMS);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compoase, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.select_context:
                selectClick(null);
                break;
        }
        return true;
    }

    public void btnClick(View view) {
        String msg;
        ArrayList<EventData> events = ContextManager.getEvents(getContentResolver());
        if (events == null) {
            msg = "ERROR";
        } else if (events.size() == 0) {
            msg = "EMPTY";
        } else {
            msg = events.get(0).longDescribe();
        }
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }

    public void addClick(View view) {
        String msg;
        String number = "894335741";
        String email = "hp3@cs.ucc.ie";
        ContactData contact = new ContactData("TestSubject", number, email);
        LocationData loc = new LocationData("Gate Cinema", 75.94, 126.83);
        Uri uri = Uri.parse("http://harshp.com");
        EventData event = new EventData("Movie Screening", new Date(), loc, contact, uri);
        int status = ContextManager.insert(getContentResolver(), event);
        if (status != 0) {
            msg = "ERROR INSERT";
        } else {
            msg = "SUCCESS";
        }
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void selectClick(View view) {
        String msg;
        Trace.beginSection("GetEvents");
        events = ContextManager.getEvents(getContentResolver());
        Trace.endSection();
        if (events == null) {
            msg = "ERROR";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        } else if (events.size() == 0) {
            msg = "EMPTY";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> items = new ArrayList<String>();

        for (EventData e : events) {
            items.add(e.shortDescribe());
            //Log.v("MESSAGE", e.shortDescribe());
        }
        String[] choices = items.toArray(new String[items.size()]);
        Log.v("MESSAGE", "size of arraylist: " + items.size());
        Log.v("MESSAGE", "choices: " + choices.toString());
        AlertDialog dialog;
//        String choices2[] = {"one", "two", "three"} ;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Context...");
        builder.setSingleChoiceItems(choices, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getApplicationContext(), "choice:" + i, Toast.LENGTH_SHORT).show();
                selectedEvent = i;
            }
        });
//        builder.setMessage("Select the context you wish to insert");
        builder.setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do something
                if (selectedEvent != -1) {
                    Log.v("MESSAGE", "selected event:" + selectedEvent);
                    Log.v("MESSAGE", "select event text:" + events.get(selectedEvent).longDescribe());
                    Log.v("MESSAGE", "etSMS: " + etSms.getId());
                    selectEventFields();
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
                selectedEvent = -1;
            }
        });

        dialog = builder.create();
        selectedEvent = 0;

        dialog.show();

    }

    private void selectEventFields() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Event fields:");
        final EventData event = events.get(selectedEvent);
        final String[] items = new String[5];

        items[0] = "Title: " + event.getEventname();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm");
        items[1] = "Date/Time: " + sdf.format(event.getDate());
        items[2] = "Location: " + event.getLocation().getPlacename();
        items[3] = "Contact(s): " + event.getContact().getName();
        items[4] = "Info: " + event.getUri().toString();
        // 0 : Title // 1 : Date/Time // 2 : Location // 3 : Contact(s) // 4: URI
        final HashMap<Integer, Boolean> selected = new HashMap<Integer, Boolean>();
        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int indexSelected, boolean isChecked) {
                if (isChecked) {
                    // user has checked this item
                    selected.put(indexSelected, true);
                } else if (selected.get(indexSelected) != null) {
                    selected.remove(indexSelected);
                }
            }
        })
                .setPositiveButton("INSERT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // insert fields here
                        String s = "";
                        if (selected.size() == 0) return;

                        if (selected.get(0) != null) {
                            s = event.getEventname();
                        }
                        if (selected.get(1) != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm");
                            s += " on " + sdf.format(event.getDate());
                        }
                        if (selected.get(2) != null) {
                            s += " at " + event.getLocation().getPlacename();
                        }
                        if (selected.get(3) != null) {
                            s += " with " + event.getContact().getName();
                        }
                        if (selected.get(4) != null) {
                            s += ". Info at " + event.getUri().toString();
                        }
                        etSms.setText(s);
                        EditText etContact = (EditText) findViewById(R.id.etContact);
                        etContact.setText(events.get(selectedEvent).getContact().shortDescribe());
                        selectedEvent = -1;
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // user has cancelled the operation
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
