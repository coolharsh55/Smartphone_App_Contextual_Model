package msc.context.demo.moviebooking;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import msc.prototype.context.ContactData;
import msc.prototype.context.ContextManager;
import msc.prototype.context.LocationData;
import msc.prototype.context.MovieData;


public class Booking extends Activity {

    Spinner spnMovieName;
    String[] names = new String[]{"The Decent One", "Men, Women & Children", "Bang Bang", "Annabelle", "Automata"};
    Spinner spnMovieDate;
    String[] dates = new String[]{"Thu 25/09/2014", "Fri 26/09/2014", "Sat 27/09/2014", "Sun 28/09/2014", "Mon 29/09/2014", "Tue 30/09/2014"};
    String[] actualdates = new String[]{"25/09/2014", "26/09/2014", "27/09/2014", "28/09/2014", "29/09/2014", "30/09/2014"};
    Spinner spnMovieTime;
    String[] times = new String[]{"8am", "9am", "10am", "11am", "12pm", "1pm", "2pm", "3pm", "4pm", "5pm", "6pm", "7pm", "8pm", "9pm", "10pm", "11pm"};
    String[] actualtimes = new String[]{"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "22:00"};
    Spinner spnMovieTheater;
    String[] locations = new String[]{"OmniPlex, Mahon Point", "Gate Multiplex, Cork"};
    Spinner spnMovieSeats;
    String[] seats = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    Button btnBookTicket;
    TextView txtMovie;
    HashMap<String, String> movie;
    MovieData movieData;
    boolean booked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        movie = new HashMap<String, String>();
        txtMovie = (TextView) findViewById(R.id.txtMovie);
        // MOVIE NAME
        spnMovieName = (Spinner) findViewById(R.id.spnMovieName);
        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        spnMovieName.setAdapter(nameAdapter);
        spnMovieName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                movie.put("name", names[pos]);
                updateMovieInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // MOVIE DATE
        spnMovieDate = (Spinner) findViewById(R.id.spnMovieDate);
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dates);
        spnMovieDate.setAdapter(dateAdapter);
        spnMovieDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                movie.put("date", dates[pos]);
                movie.put("actualdate", actualdates[pos]);
                updateMovieInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // MOVIE TIME
        spnMovieTime = (Spinner) findViewById(R.id.spnMovieTime);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, times);
        spnMovieTime.setAdapter(timeAdapter);
        spnMovieTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                movie.put("time", times[pos]);
                movie.put("actualtime", actualtimes[pos]);
                updateMovieInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // MOVIE LOCATION
        spnMovieTheater = (Spinner) findViewById(R.id.spnMovieTheater);
        ArrayAdapter<String> locAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        spnMovieTheater.setAdapter(locAdapter);
        spnMovieTheater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                movie.put("location", locations[pos]);
                updateMovieInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // MOVIE SEATS
        spnMovieSeats = (Spinner) findViewById(R.id.spnMovieSeats);
        ArrayAdapter<String> seatAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, seats);
        spnMovieSeats.setAdapter(seatAdapter);
        spnMovieSeats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String ss = "C5";
                if (pos > 1) {
                    ss += " - C" + (5 + pos);
                }
                movie.put("seats", ss);
                updateMovieInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // BOOK TICKET
        btnBookTicket = (Button) findViewById(R.id.btnBookTicket);
        btnBookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBookTicket.setText("TICKET CONFIRMED");
                btnBookTicket.setEnabled(false);
                spnMovieName.setEnabled(false);
                spnMovieDate.setEnabled(false);
                spnMovieTime.setEnabled(false);
                spnMovieTheater.setEnabled(false);
                spnMovieSeats.setEnabled(false);
                movie.put("ticket","ANBC55432");
                ticketBooked();
            }
        });
    }

    private void ticketBooked() {
        booked = true;
        // name = movie.get("name");
        SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy HH:mm");
        Date date = new Date();
        try {
            date = df.parse(movie.get("actualdate") + " " + movie.get("actualtime"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LocationData loc = new LocationData(movie.get("location"),125.98,-8.95349);
        ContactData contactData = new ContactData("Harshvardhan Pandit", "089-433-5741", "coolharsh55@gmail.com");
        Uri uri = Uri.parse("http://www.imdb.com/title/tt3322940/");

        movieData = new MovieData(movie.get("name"),date,loc,contactData,uri,movie.get("ticket"),movie.get("seats"));
        if (ContextManager.insert(getContentResolver(), movieData) != 0) {
            Toast.makeText(getApplicationContext(),"ERROR INSERTING MOVIE CONTEXT",Toast.LENGTH_LONG).show();
        } else {
//            ArrayList<MovieData> movies = ContextManager.getMovies(getContentResolver());
//            for (MovieData movie : movies) {
//                Log.v("X_CP", movie.longDescribe());
//            }
        }
//        updateMovieInfo();
    }

    private void updateMovieInfo() {
        String s;
        if (booked) {
            s = "\n TICKET BOOKED";
        } else {
            s = "\n CONFIRM MOVIE INFO";
        }
        boolean nocontent = true;
        String ss = movie.get("name");
        if (ss != null) {
            nocontent = false;
            s += "\n  " + ss;
        }
        ss = movie.get("date");
        if (ss != null) {
            nocontent = false;
            s += "\n  " + ss;
        }
        ss = movie.get("time");
        if (ss != null) {
            nocontent = false;
            s += "\n  " + ss;
        }
        ss = movie.get("location");
        if (ss != null) {
            nocontent = false;
            s += "\n  " + ss;
        }
        ss = movie.get("seats");
        if (ss != null) {
            nocontent = false;
            s += "\n  Seats : " + ss;
        }
        if (nocontent) {
            s += "\n  No info selected";
        }
        if (booked) {
            s += "\n  TicketID =" + movie.get("ticket");
            s += "\n\n" + movieData.longDescribe();
        }
        txtMovie.setText(s);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.booking, menu);
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
