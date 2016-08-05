package msc.context.demo.notifier;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RemoteViews;

import java.util.ArrayList;

import msc.prototype.context.ContextManager;
import msc.prototype.context.EventData;
import msc.prototype.context.MovieData;


public class Notifier extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifier);

        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, "Movie time!!!", when);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notifier);
        ArrayList<MovieData> movies = ContextManager.getMovies(getContentResolver());
        MovieData selected_movie = null;
        for (MovieData movie : movies) {
            Log.v("XCP", movie.longDescribe());
            if (movie.getEventname().equals("Annabelle")) {
                selected_movie = movie;
                break;
            }
        }
        if (selected_movie != null) {
            contentView.setTextViewText(R.id.tvName, selected_movie.getEventname());
            contentView.setTextViewText(R.id.tvInfo, selected_movie.getDate() + " at " +
                selected_movie.getLocation().getPlacename());
            contentView.setTextViewText(R.id.tvTicket, "TicketID:" + selected_movie.getTicketID() + " Seats:" +
                selected_movie.getSeats());
            contentView.setTextViewText(R.id.tvLink, "movie info: " + selected_movie.getUri().toString());
        } else {
            contentView.setTextViewText(R.id.tvName, "Movie Title");
            contentView.setTextViewText(R.id.tvInfo, "Movie time and location");
            contentView.setTextViewText(R.id.tvTicket, "TicketID and Seats");
            contentView.setTextViewText(R.id.tvLink, "Click here for movie info");
        }
        notification.contentView = contentView;
        notification.bigContentView = contentView;

        Intent notificationIntent = new Intent(this, Notifier.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.contentIntent = contentIntent;

        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.flags |= Notification.DEFAULT_LIGHTS;
        notification.flags |= Notification.DEFAULT_SOUND;

        mNotificationManager.notify(1, notification);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notifier, menu);
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
}
