/*Copyright [2014] [Harshvardhan J Pandit]

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License. */

package msc.prototype.context;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by harsh on 21/07/14.
 */
public class ContextManager {

    static final String uri_Contact = "Contact";
    static final String uri_Location = "Location";
    static final String uri_Event = "Event";
    static final String uri_Movie = "Movie";
    // ERROR CODES
    static final int FIELDS_INCORRECT = 2;
    private static final int OPERATION_ERROR = -1;
    // todo move all these magic numbers and strings to seperate class
    static String uri_authority = "content://msc.prototype.contextprovider.cp/";

    /**
     * stub
     *
     * @param context abstract context object
     * @return 0
     */
    public static int insert(ContextData context) {
        return 0;
    }

    /**
     * inserts Contact context in database
     *
     * @param resolver contentresolver of calling activity
     * @param contact  Contact context object
     * @return 0 on success, ERROR_CODE otherwise
     */
    public static int insert(ContentResolver resolver, ContactData contact) {
        long time_send = new Date().getTime();
        if (!contact.checkFields()) return FIELDS_INCORRECT; //check fields are correct
        // todo arbitrary error, move to error codes file
        Uri uri = Uri.parse(uri_authority + uri_Contact);
        ContentValues values = new ContentValues();
        values.put("time_send", time_send);
        values.put("name", contact.getName());
        values.put("number", contact.getNumber());
        values.put("email", contact.getEmail());
        Uri response = resolver.insert(uri, values);
        if (response == null) {
            return OPERATION_ERROR;
        }
        long id = Long.parseLong(response.getQueryParameter("id"));
        contact.set_id(SystemToken.token, id);
        time_send = new Date().getTime() - time_send;
        Log.v("MSC_CM_I_C", "insert time: " + time_send);
        return 0;
    }

    /**
     * inserts Location context in database
     *
     * @param resolver contentresolver of calling activity
     * @param location Location context object
     * @return 0 on success, ERROR_CODE otherwise
     */
    public static int insert(ContentResolver resolver, LocationData location) {
        long time_send = new Date().getTime();
        if (!location.checkFields()) return FIELDS_INCORRECT; //check fields are correct
        // todo arbitrary error, move to error codes file
        Uri uri = Uri.parse(uri_authority + uri_Location);
        ContentValues values = new ContentValues();
        values.put("time_send", time_send);
        values.put("place", location.getPlacename());
        values.put("xpos", location.getXpos());
        values.put("ypos", location.getYpos());
        Uri response = resolver.insert(uri, values);
        if (response == null) {
            return OPERATION_ERROR;
        }
        long id = Long.parseLong(response.getQueryParameter("id"));
        location.set_id(SystemToken.token, id);
        time_send = new Date().getTime() - time_send;
        Log.v("MSC_CM_I_L", "insert time: " + time_send);
        return 0;
    }

    /**
     * inserts Event context in database
     *
     * @param resolver contentresolver of calling activity
     * @param event    Event context object
     * @return 0 on success, ERROR_CODE otherwise
     */
    public static int insert(ContentResolver resolver, EventData event) {
        long time_send = new Date().getTime();
        // todo remove :: if (!event.checkFields()) return FIELDS_INCORRECT; //check fields are correct
        // todo arbitrary error, move to error codes file
        Uri uri = Uri.parse(uri_authority + uri_Event);
        ContentValues values = new ContentValues();
        values.put("time_send", time_send);
        values.put("eventtitle", event.getEventname());
        values.put("eventdate", event.getDate().getTime());
        values.put("eventuri", event.getUri().toString());

        if (event.getLocation().get_id(SystemToken.token) == 0) {
            values.put("locationid", 0);
            values.put("locationplace", event.getLocation().getPlacename());
            values.put("locationxpos", event.getLocation().getXpos());
            values.put("locationypos", event.getLocation().getYpos());
        } else {
            values.put("locationid", event.getLocation().get_id(SystemToken.token));
        }
        ContactData contact = event.getContact();
        if (contact.get_id(SystemToken.token) == 0) {
            values.put("contactid", 0);
            values.put("contactname", event.getContact().getName());
            values.put("contactnumber", event.getContact().getNumber());
            values.put("contactemail", event.getContact().getEmail());
        } else {
            values.put("contactid", contact.get_id(SystemToken.token));
        }

        Uri response = resolver.insert(uri, values);
        if (response == null) {
            return OPERATION_ERROR;
        }
        long id = Long.parseLong(response.getQueryParameter("id"));
        event.set_id(SystemToken.token, id);
        id = Long.parseLong(response.getQueryParameter("cid"));
        event.getContact().set_id(SystemToken.token, id);
        id = Long.parseLong(response.getQueryParameter("lid"));
        event.getLocation().set_id(SystemToken.token, id);
        time_send = new Date().getTime() - time_send;
        Log.v("MSC_CM_I_E", "insert time: " + time_send);
        return 0;
    }

    /**
     * inserts Movie context in database
     *
     * @param resolver contentresolver of calling activity
     * @param movie    Movie context object
     * @return 0 on success, ERROR_CODE otherwise
     */
    public static int insert(ContentResolver resolver, MovieData movie) {
        long time_send = new Date().getTime();
        // todo remove :: if (!movie.checkFields()) return FIELDS_INCORRECT; //check fields are correct
        // todo arbitrary error, move to error codes file
        Uri uri = Uri.parse(uri_authority + uri_Movie);
        ContentValues values = new ContentValues();
        values.put("eventtitle", movie.getEventname());
        values.put("eventdate", movie.getDate().getTime());
        values.put("eventuri", movie.getUri().toString());

        values.put("movieticketID", movie.getTicketID());
        values.put("movieseats", movie.getSeats());

        if (movie.getLocation().get_id(SystemToken.token) == 0) {
            values.put("locationid", 0);
            values.put("locationplace", movie.getLocation().getPlacename());
            values.put("locationxpos", movie.getLocation().getXpos());
            values.put("locationypos", movie.getLocation().getYpos());
        } else {
            values.put("locationid", movie.getLocation().get_id(SystemToken.token));
        }
        ContactData contact = movie.getContact();
        if (contact.get_id(SystemToken.token) == 0) {
            values.put("contactid", 0);
            values.put("contactname", movie.getContact().getName());
            values.put("contactnumber", movie.getContact().getNumber());
            values.put("contactemail", movie.getContact().getEmail());
        } else {
            values.put("contactid", contact.get_id(SystemToken.token));
        }

        Uri response = resolver.insert(uri, values);
        if (response == null) {
            return OPERATION_ERROR;
        }
        long id = Long.parseLong(response.getQueryParameter("id"));
        movie.set_id(SystemToken.token, id);
        id = Long.parseLong(response.getQueryParameter("cid"));
        movie.getContact().set_id(SystemToken.token, id);
        id = Long.parseLong(response.getQueryParameter("lid"));
        movie.getLocation().set_id(SystemToken.token, id);
        time_send = new Date().getTime() - time_send;
        Log.v("MSC_CM_I_M", "insert time: " + time_send);
        return 0;
    }

    /**
     * stub
     *
     * @return null
     */
    public static ArrayList<ContextData> getContext() {
        return null;
    }

    /**
     * inserts Contact context in database
     *
     * @param resolver contentresolver of calling activity
     * @return ArrayList<ContactData> on success, null otherwise
     */
    public static ArrayList<ContactData> getContact(ContentResolver resolver) {
        long time_send = new Date().getTime();
        Uri uri = Uri.parse(uri_authority + uri_Contact);
        Cursor result = resolver.query(uri, null, null, null, null);
        if (result != null) {
            if (result.getCount() == 0) {
                result.close();
                return new ArrayList<ContactData>();
            }
            ArrayList<ContactData> contacts = new ArrayList<ContactData>(result.getCount());
            result.moveToFirst();
            ContactData contact;
            do {
                long id = result.getLong(0);
                contact = new ContactData(result.getString(1), result.getString(2),
                        result.getString(3));
                contact.set_id(SystemToken.token, id);
                contacts.add(contact);
            } while (result.moveToNext());
            result.close();
            time_send = new Date().getTime() - time_send;
            Log.v("MSC_CM_G_C", "insert time: " + time_send);
            return contacts;
        }
        return null;
    }

    /**
     * inserts Location context in database
     *
     * @param resolver contentresolver of calling activity
     * @return ArrayList<LocationData> on success, null otherwise
     */
    public static ArrayList<LocationData> getLocations(ContentResolver resolver) {
        long time_send = new Date().getTime();
        Uri uri = Uri.parse(uri_authority + uri_Location);
        Cursor result = resolver.query(uri, null, null, null, null);
        if (result != null) {
            if (result.getCount() == 0) {
                result.close();
                return new ArrayList<LocationData>();
            }
            ArrayList<LocationData> locations = new ArrayList<LocationData>(result.getCount());
            result.moveToFirst();
            do {
                long id = result.getLong(0);
                String place = result.getString(1);
                double xpos = result.getDouble(2);
                double ypos = result.getDouble(3);
                LocationData location = new LocationData(place, xpos, ypos);
                location.set_id(SystemToken.token, id);
                locations.add(location);
            } while (result.moveToNext());
            result.close();
            time_send = new Date().getTime() - time_send;
            Log.v("MSC_CM_G_L", "insert time: " + time_send);
            return locations;
        }
        return null;
    }

    /**
     * inserts Event context in database
     *
     * @param resolver contentresolver of calling activity
     * @return ArrayList<EventData> on success, null otherwise
     */
    public static ArrayList<EventData> getEvents(ContentResolver resolver) {
        long time_send = new Date().getTime();
        Uri uri = Uri.parse(uri_authority + uri_Event);
        Cursor result = resolver.query(uri, null, null, null, null);
        if (result != null) {
            if (result.getCount() == 0) {
                result.close();
                return new ArrayList<EventData>();
            }
            ArrayList<EventData> events = new ArrayList<EventData>(result.getCount());
            result.moveToFirst();
            String nil = "nil";
            // loc
            LocationData location;
            // contact
            ContactData contact;
            // movie
            EventData event;
            Uri urival;
            do {
                /*
                0   event       id          long
                1   event       title       string
                2   event       date        long
                3   location    id          long
                4   location    place       string
                5   location    xpos        double
                6   location    ypos        double
                7   contact     id          long
                8   contact     name        string
                9   contact     number      string
                10  contact     email       string
                11  event       uri         string
                 */

                location = new LocationData(result.getString(4), result.getDouble(5),
                        result.getDouble(6));
                location.set_id(SystemToken.token, result.getLong(3));

                contact = new ContactData(result.getString(8), result.getString(9),
                        result.getString(10));
                contact.set_id(SystemToken.token, result.getLong(7));
                urival = Uri.parse(result.getString(11));
                event = new EventData(result.getString(1), new Date(result.getLong(2)),
                        location, contact, urival);
                event.set_id(SystemToken.token, result.getLong(0));

                events.add(event);
            } while (result.moveToNext());
            result.close();
            time_send = new Date().getTime() - time_send;
            Log.v("MSC_CM_G_E", "insert time: " + time_send);
            return events;
        }
        return null;
    }

    /**
     * inserts Movie context in database
     *
     * @param resolver contentresolver of calling activity
     * @return ArrayList<EventData> on success, null otherwise
     */
    public static ArrayList<MovieData> getMovies(ContentResolver resolver) {
        long time_send = new Date().getTime();
        Uri uri = Uri.parse(uri_authority + uri_Movie);
        Cursor result = resolver.query(uri, null, null, null, null);
        if (result != null) {
            if (result.getCount() == 0) {
                result.close();
                return new ArrayList<MovieData>();
            }
            ArrayList<MovieData> movies = new ArrayList<MovieData>(result.getCount());
            result.moveToFirst();
            String nil = "nil";
            // loc
            LocationData location;
            // contact
            ContactData contact;
            // movie
            MovieData movie;
            Uri urival;

            do {
                /*
                0   event       id          long
                1   event       title       string
                2   event       date        long
                3   location    id          long
                4   location    place       string
                5   location    xpos        double
                6   location    ypos        double
                7   contact     id          long
                8   contact     name        string
                9   contact     number      string
                10  contact     email       string
                11  movie       ticketID    string
                12  movie       seats       string
                13  event       uri         string
                 */

                location = new LocationData(result.getString(4), result.getDouble(5),
                        result.getDouble(6));
                location.set_id(SystemToken.token, result.getLong(3));

                contact = new ContactData(result.getString(8), result.getString(10),
                        result.getString(10));
                contact.set_id(SystemToken.token, result.getLong(7));

                urival = Uri.parse(result.getString(13));
                movie = new MovieData(result.getString(1), new Date(result.getLong(2)),
                        location, contact, urival, result.getString(11), result.getString(12));
                movie.set_id(SystemToken.token, result.getLong(0));

                movies.add(movie);
            } while (result.moveToNext());
            result.close();
            time_send = new Date().getTime() - time_send;
            Log.v("MSC_CM_G_M", "insert time: " + time_send);
            return movies;
        }
        return null;
    }
}
