package msc.prototype.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.util.Log;

import java.util.Date;

public class ContextProvider extends ContentProvider {
    // todo move all the magic numbers and string to seperate class
    /**
     * URI table constants
     */
    static final int uri_Contact = 1;
    static final int uri_Location = 2;
    static final int uri_Event = 3;
    static final int uri_Movie = 4;
    /**
     * URI authority
     */
    static String uri_authority = "msc.prototype.contextprovider.cp";
    /**
     * parses and matches incoming URI to database table constants
     */
    static UriMatcher uriMatcher = new UriMatcher(0);

    static {
        uriMatcher.addURI(uri_authority, "Contact", uri_Contact);
        uriMatcher.addURI(uri_authority, "Location", uri_Location);
        uriMatcher.addURI(uri_authority, "Event", uri_Event);
        uriMatcher.addURI(uri_authority, "Movie", uri_Movie);
    }

    /**
     * SQLite database class
     */
    DatabaseSQLite db_helper = null;

    public ContextProvider() {
    }

    /**
     * this prototype does not support delete operations
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * get the type of the table
     */
    @Override
    public String getType(Uri uri) {
        return "not implemented";
    }

    /**
     * INSERT CONTACT
     *
     * @param val String name, number, email
     * @return long 0: ERROR ; 0< ID
     */
    private long insert_contact(ContentValues val) {
        long time_send = new Date().getTime();
        long contactid = 0; // 0 : ERROR
        SQLiteDatabase db = db_helper.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT _id FROM " + DatabaseSQLite.tContact
                        + " WHERE name = ? AND number = ? AND email = ?",
                new String[]{val.getAsString("name"),
                        val.getAsString("number"),
                        val.getAsString("email")});
        if (cur != null) {
            if (cur.getCount() == 0) { // NO DUPLICATES FOUND
                Log.v("PA_CP", "No duplicates found in Contact");
                contactid = db.insert(DatabaseSQLite.tContact, "", val);
                if (contactid <= 0) { // error insert
                    db.endTransaction();
                    contactid = 0;
                }
            } else { // THERE ARE DUPLICATES IN TABLE
                cur.moveToFirst();
                contactid = cur.getLong(0);
                Log.v("PA_CP", "Duplictae contact id : " + contactid);
                // ASSIGN DUPLICATE ID
            }
            time_send = new Date().getTime() - time_send;
            Log.v("MSC_CP_I_C", "insert time: " + time_send);
            cur.close();
        } else {
            Log.v("PA_CP", "error in duplicate query for contact");
        }
        return contactid;
    }

    /**
     * INSERT LOCATIOM
     *
     * @param val String place, long xpos,ypos
     * @return long 0 : ERROR ; 0< : ID
     */
    private long insert_location(ContentValues val) {
        long time_send = new Date().getTime();
        long locationid = 0; // 0 : ERROR
        SQLiteDatabase db = db_helper.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT _id FROM " + DatabaseSQLite.tLocation
                        + " WHERE place = ? AND xpos = ? AND ypos = ?",
                new String[]{val.getAsString("place"),
                        String.valueOf(val.getAsDouble("xpos")),
                        String.valueOf(val.getAsDouble("ypos"))});
        if (cur != null) {
            if (cur.getCount() == 0) {
                // NO DUPLICATES FOUND
                Log.v("PA_CP", "No duplicates found in Location");
                locationid = db.insert(DatabaseSQLite.tLocation, "", val);
                if (locationid <= 0) { // error insert
                    db.endTransaction();
                    locationid = 0;
                }
            } else {
                cur.moveToFirst();
                locationid = cur.getLong(0);
                // ASSIGN DUPLICATE ID
                Log.v("PA_CP", "Duplictae location id : " + locationid);
            }
            time_send = new Date().getTime() - time_send;
            Log.v("MSC_CP_I_L", "insert time: " + time_send);
            cur.close();
        } else {
            Log.v("PA_CP", "error in duplicate query for location");
        }
        return locationid;
    }

    /**
     * INSERT EVENT
     *
     * @param val String title, longname, number, email
     * @return long 0: ERROR ; 0< ID
     */
    private long insert_event(ContentValues val) {
        long time_send = new Date().getTime();
        long eventid = 0; // 0 : EVENT
        SQLiteDatabase db = db_helper.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT _id FROM " + DatabaseSQLite.tEvent
                        + " WHERE title = ? AND date= ?",
                new String[]{val.getAsString("title"),
                        String.valueOf(val.getAsLong("date"))});
        if (cur != null) {
            if (cur.getCount() == 0) {
                // NO DUPLICATES FOUND
                Log.v("PA_CP", "No duplicates found in Event");
                eventid = db.insert(DatabaseSQLite.tEvent, "", val);
                if (eventid <= 0) { // error insert
                    db.endTransaction();
                    eventid = 0;
                }
            } else {
                cur.moveToFirst();
                eventid = cur.getLong(0);
                // ASSIGN DUPLICATE ID
                Log.v("PA_CP", "Duplictae location eventid : " + eventid);
            }
            time_send = new Date().getTime() - time_send;
            Log.v("MSC_CP_I_E", "insert time: " + time_send);
            cur.close();
        } else {
            Log.v("PA_CP", "error in duplicate query for Event");
        }
        return eventid;
    }

    /**
     * insert operation
     *
     * @param uri    URI containing authority/table
     * @param values entry values, 'assumed' correct since checked by libContextProvider
     * @return URI of inserted entry on success, null on failure
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long time_send = new Date().getTime();
        SQLiteDatabase db = db_helper.getWritableDatabase();
        long id = 0;
        switch (uriMatcher.match(uri)) {
            ////////////////// CONTACT
            case uri_Contact: // contact
//                Log.v("PA_CP", "contact request");
                id = insert_contact(values);
                if (id > 0) { // successfull inserts return natural integers
                    Uri result = new Uri.Builder() // create return URI
                            .scheme("content")
                            .authority(uri_authority)
                            .path("Contact")
                            .appendQueryParameter("id", Long.toString(id))
                            .build();
//                    Log.v("PA_CP_RET", result.toString());
                    return result;
                }
                break;

            //////////////////// LOCATION
            case uri_Location: // location
//                Log.v("PA_CP", "location request");
                id = insert_location(values);
                if (id > 0) { // successfull inserts return natural integers
                    Uri result = new Uri.Builder() // return URI
                            .scheme("content")
                            .authority(uri_authority)
                            .path("Location")
                            .appendQueryParameter("id", Long.toString(id))
                            .build();
//                    Log.v("PA_CP_RET", result.toString());
                    return result;
                }
                break;

            ////////////////////// EVENT
            case uri_Event: // event
            {
//                Log.v("PA_CP", "event request");
                db.beginTransaction();
                ContentValues val = new ContentValues();

                long contactid = values.getAsLong("contactid");
                if (contactid == 0) { // new contact
                    val.put("name", values.getAsString("contactname"));
                    val.put("number", values.getAsString("contactnumber"));
                    val.put("email", values.getAsString("contactemail"));
                    contactid = insert_contact(val);
                    if (contactid == 0) {
                        return null;
                    }
                }

                val.clear();
                long locationid = values.getAsLong("locationid");
                if (locationid == 0) { // new location
                    val.put("place", values.getAsString("locationplace"));
                    val.put("xpos", values.getAsDouble("locationxpos"));
                    val.put("ypos", values.getAsDouble("locationypos"));
                    locationid = insert_location(val);
                    if (locationid == 0) {
                        return null;
                    }
                }

                val.clear();
                val.put("title", values.getAsString("eventtitle"));
                val.put("date", values.getAsString("eventdate"));
                val.put("uri", values.getAsString("eventuri"));
                id = insert_event(val);
                if (id == 0) {
                    return null;
                }

                //////////////////// REL TABLE  INSERTS
                // Event x Contact
                if (insert_eventxcontact(id, contactid) != 0) {
                    return null;
                }
                // Event x Loc
                if (eventxloc(id, locationid) != 0) {
                    return null;
                }

                db.setTransactionSuccessful();
                db.endTransaction();

                Uri result = new Uri.Builder()
                        .scheme("content")
                        .authority(uri_authority)
                        .path("Event")
                        .appendQueryParameter("id", Long.toString(id))
                        .appendQueryParameter("cid", Long.toString(contactid))
                        .appendQueryParameter("lid", Long.toString(locationid))
                        .build();
//                Log.v("PA_CP_RET", result.toString());
                time_send = new Date().getTime() - time_send;
                Log.v("MSC_CP_IC_E", "insert time: " + time_send);
                return result;
                // break
            }

            /////////////////// MOVIE
            case uri_Movie: {
//                Log.v("PA_CP", "event request");
                db.beginTransaction();
                long transtime = new Date().getTime();
                ContentValues val = new ContentValues();

                long contactid = values.getAsLong("contactid");
                if (contactid == 0) { // new contact
                    val.put("name", values.getAsString("contactname"));
                    val.put("number", values.getAsString("contactnumber"));
                    val.put("email", values.getAsString("contactemail"));
                    contactid = insert_contact(val);
                    if (contactid == 0) {
                        return null;
                    }
                }

                val.clear();
                long locationid = values.getAsLong("locationid");
                if (locationid == 0) { // new location
                    val.put("place", values.getAsString("locationplace"));
                    val.put("xpos", values.getAsDouble("locationxpos"));
                    val.put("ypos", values.getAsDouble("locationypos"));
                    locationid = insert_location(val);
                    if (locationid == 0) {
                        return null;
                    }
                }

                val.clear();
                val.put("title", values.getAsString("eventtitle"));
                val.put("date", values.getAsString("eventdate"));
                val.put("uri", values.getAsString("eventuri"));
                long eventid = insert_event(val);
                if (eventid == 0) {
                    return null;
                }

                val.clear();
                val.put("_id", eventid);
                val.put("ticketID", values.getAsString("movieticketID"));
                val.put("seats", values.getAsString("movieseats"));
                id = insert_movie(val);
                if (id == 0) {
                    return null;
                }

                //////////////////// REL TABLE  INSERTS
                // Event x Contact
                if (insert_eventxcontact(id, contactid) != 0) {
                    return null;
                }
                // Event x Loc
                if (eventxloc(id, locationid) != 0) {
                    return null;
                }

                db.setTransactionSuccessful();
                db.endTransaction();

                Uri result = new Uri.Builder()
                        .scheme("content")
                        .authority(uri_authority)
                        .path("Movie")
                        .appendQueryParameter("id", Long.toString(id))
                        .appendQueryParameter("cid", Long.toString(contactid))
                        .appendQueryParameter("lid", Long.toString(locationid))
                        .build();
//                Log.v("PA_CP_RET", result.toString());
                time_send = new Date().getTime() - time_send;
                Log.v("MSC_CP_IC_M", "insert time: " + time_send);
                return result;
                // break
            }
            default:
//                Log.v("PA_CP", "unknown request");
        }

        return null; // unsuccessfull operation
    }

    private long insert_movie(ContentValues val) {
        long time_send = new Date().getTime();
        long movieid = 0; // 0 : EVENT
        SQLiteDatabase db = db_helper.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT _id FROM " + DatabaseSQLite.tMovie
                        + " WHERE ticketID = ? AND seats = ?",
                new String[]{val.getAsString("ticketID"),
                        String.valueOf(val.getAsString("seats"))});
        if (cur != null) {
            if (cur.getCount() == 0) {
                // NO DUPLICATES FOUND
                Log.v("PA_CP", "No duplicates found in Movie");
                movieid = db.insert(DatabaseSQLite.tMovie, "", val);
                if (movieid <= 0) { // error insert
                    db.endTransaction();
                    movieid = 0;
                }
            } else {
                cur.moveToFirst();
                movieid = cur.getLong(0);
                // ASSIGN DUPLICATE ID
                Log.v("PA_CP", "Duplictae location movieid : " + movieid);
            }
            time_send = new Date().getTime() - time_send;
            Log.v("MSC_CP_I_M", "insert time: " + time_send);
            cur.close();
        } else {
            Log.v("PA_CP", "error in duplicate query for Movie");
        }
        return movieid;
    }

    private int eventxloc(long id, long locationid) {
        SQLiteDatabase db = db_helper.getWritableDatabase();
        SQLiteStatement stmt;
        Cursor cur = db.rawQuery("SELECT e_id, l_id FROM " + DatabaseSQLite.trEventAndLocation
                        + " WHERE e_id = ? AND l_id = ? AND e_type = ? AND l_type = ?",
                new String[]{String.valueOf(id), String.valueOf(locationid),
                        String.valueOf(DatabaseSQLite.tidEvent),
                        String.valueOf(DatabaseSQLite.tidLocation)});
        if (cur != null) {
            if (cur.getCount() == 0) {
                // NO DUPLICATES FOUND
                Log.v("PA_CP", "No duplicates found in Event x Location");
                stmt = db.compileStatement("insert into " + DatabaseSQLite.trEventAndLocation
                        + "(e_id,l_id,e_type,l_type) values"
                        + "(?,?,?,?);");
                stmt.bindLong(1, id);
                stmt.bindLong(2, locationid);
                stmt.bindLong(3, DatabaseSQLite.tidEvent);
                stmt.bindLong(4, DatabaseSQLite.tidLocation);
                stmt.executeInsert();
                stmt.clearBindings();
            } else {
                cur.moveToFirst();
                Log.v("PA_CP", "Duplicate Event x Location : [" + cur.getLong(0) + ","
                        + cur.getLong(2) + "]");
            }
            cur.close();
        } else {
            Log.v("PA_CP", "error in duplicate query for Event x Location");
            return -1;
        }
        return 0;
    }

    private int insert_eventxcontact(long id, long contactid) {
        SQLiteDatabase db = db_helper.getWritableDatabase();
        SQLiteStatement stmt;
        Cursor cur = db.rawQuery("SELECT e_id, c_id FROM " + DatabaseSQLite.trEventAndContact
                        + " WHERE e_id = ? AND c_id = ? AND e_type = ? AND c_type = ?",
                new String[]{String.valueOf(id), String.valueOf(contactid),
                        String.valueOf(DatabaseSQLite.tidEvent),
                        String.valueOf(DatabaseSQLite.tidContact)});
        if (cur != null) {
            if (cur.getCount() == 0) {
                // NO DUPLICATES FOUND
                Log.v("PA_CP", "No duplicates found in Event x Contact");
                stmt = db.compileStatement("insert into " + DatabaseSQLite.trEventAndContact
                        + "(e_id,c_id,e_type,c_type) values"
                        + "(?,?,?,?);");
                stmt.bindLong(1, id);
                stmt.bindLong(2, contactid);
                stmt.bindLong(3, DatabaseSQLite.tidEvent);
                stmt.bindLong(4, DatabaseSQLite.tidContact);
                stmt.executeInsert();
                stmt.clearBindings();
            } else {
                cur.moveToFirst();
                Log.v("PA_CP", "Duplicate Event x Contact : [" + cur.getLong(0) + ","
                        + cur.getLong(2) + "]");
            }
            cur.close();
        } else {
            Log.v("PA_CP", "error in duplicate query for Event x Contact");
            return -1;
        }
        return 0;
    }

    /**
     * called on creation of the content provider
     *
     * @return true
     */
    @Override
    public boolean onCreate() {
        db_helper = new DatabaseSQLite(getContext());
        return true;
    }

    /**
     * query the database for values
     *
     * @param uri           contains authority/table
     * @param projection    return columns
     * @param selection     selection criteria
     * @param selectionArgs selection arguments
     * @param sortOrder     sort criteria
     * @return Cursor containing results, null on failure
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        long time_send = new Date().getTime();
        SQLiteDatabase db = db_helper.getReadableDatabase();
        Cursor result = null;
        switch (uriMatcher.match(uri)) {
            case 1: // contact
//                Log.v("PA_CP", "contact request");
                // TODO limit clause
                result = db.rawQuery("select * from " + DatabaseSQLite.tContact, null);
                time_send = new Date().getTime() - time_send;
                Log.v("MSC_CP_G_C", "insert time: " + time_send);
                break;
            case 2: // location
//                Log.v("PA_CP", "location request");
                // TODO limit clause
                result = db.rawQuery("select * from " + DatabaseSQLite.tLocation, null);
                time_send = new Date().getTime() - time_send;
                Log.v("MSC_CP_G_L", "insert time: " + time_send);
                break;
            case 3: // event
//                Log.v("PA_CP", "event request");
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
                // TODO limit clause
                result = db.rawQuery("SELECT e._id, e.title, e.date,"
                        + "l._id, l.place, l.xpos, l.ypos,"
                        + "c._id, c.name, c.number, c.email, "
                        + "e.uri "
                        + "FROM " + DatabaseSQLite.tEvent + " e "
                        + "LEFT OUTER JOIN " + DatabaseSQLite.trEventAndContact + " ec on ec.e_id=e._id "
                        + "LEFT OUTER JOIN " + DatabaseSQLite.tContact + " c on c._id=ec.c_id "
                        + "LEFT OUTER JOIN " + DatabaseSQLite.trEventAndLocation + " el on el.e_id=e._id "
                        + "LEFT OUTER JOIN " + DatabaseSQLite.tLocation + " l on l._id=el.l_id;", null);
                time_send = new Date().getTime() - time_send;
                Log.v("MSC_CP_G_E", "insert time: " + time_send);
                break;
            case 4: // movie
//            Log.v("PA_CP", "movie request");
                result = db.rawQuery("SELECT e._id, e.title, e.date,"
                        + "l._id, l.place, l.xpos, l.ypos,"
                        + "c._id, c.name, c.number, c.email, "
                        + "m.ticketID, m.seats, "
                        + "e.uri "
                        + "FROM " + DatabaseSQLite.tEvent + " e "
                        + "LEFT OUTER JOIN " + DatabaseSQLite.trEventAndContact + " ec on ec.e_id=e._id "
                        + "LEFT OUTER JOIN " + DatabaseSQLite.tContact + " c on c._id=ec.c_id "
                        + "LEFT OUTER JOIN " + DatabaseSQLite.trEventAndLocation + " el on el.e_id=e._id "
                        + "LEFT OUTER JOIN " + DatabaseSQLite.tLocation + " l on l._id=el.l_id "
                        + "LEFT OUTER JOIN " + DatabaseSQLite.tMovie + " m on m._id=e._id;", null);
                time_send = new Date().getTime() - time_send;
                Log.v("MSC_CP_G_M", "insert time: " + time_send);
                break;
            default:
//                Log.v("PA_CP", "unknown request");
        }
        return result;
    }

    /**
     * update some entry
     *
     * @param uri           contains authority/table/entryid
     * @param values        entry values, 'assumed' correct since checked by libContextProvider
     * @param selection     selection criteria
     * @param selectionArgs selection arguments
     * @return id on success, 0 on failure
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
