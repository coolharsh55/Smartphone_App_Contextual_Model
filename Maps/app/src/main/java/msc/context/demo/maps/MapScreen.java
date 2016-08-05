package msc.context.demo.maps;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import msc.prototype.context.ContextManager;
import msc.prototype.context.EventData;


public class MapScreen extends ListActivity {

    ArrayList<String> items;
    ArrayList<String> items2;
    ArrayList<EventData> events;
    ListView listview;
    ListAdapter adapter;
    ListView listview2;
    ListAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        listview = getListView();
        listview2 = (ListView) findViewById(R.id.list2);
        items = new ArrayList<String>();
        items2 = new ArrayList<String>();
        adapter = new ListAdapter(this, R.layout.eventlist, items);
        adapter2 = new ListAdapter(this,R.layout.eventlist, items2);
        listview.setAdapter(adapter);
        listview2.setAdapter(adapter2);

//        for (int i=0; i<10; i++) items.add("event " + i);
        events = ContextManager.getEvents(getContentResolver());

        if (events.size() < 3) {
            for (EventData event : events) {
                items.add(event.getEventname());
                items2.add(event.getEventname());
            }
        } else {
            for (int i=0; i<3; i++) {
                items.add((events.get(i).getEventname()));
                items2.add((events.get(i).getEventname()));
            }
        }
        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_screen, menu);
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

    public class ListAdapter extends ArrayAdapter<String> {

        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public ListAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.eventlist, null);
            }
            String p = getItem(position);
            if (p != null) {
                TextView title = (TextView) v.findViewById(R.id.txtEventTitle);
                title.setText(events.get(position).getEventname());
                TextView desc = (TextView) v.findViewById(R.id.txtEventDateTime);
//                desc.setText(events.get(position).getDate().toString());
                desc.setText("test event at test location");
                TextView tt = (TextView) v.findViewById(R.id.txtEventETA);
                tt.setText("ETA: " + (10 + position * 5) + " mins.");
            }
            return v;
        }
    }
}
