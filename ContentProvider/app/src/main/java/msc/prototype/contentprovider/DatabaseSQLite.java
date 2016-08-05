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
package msc.prototype.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;
import java.util.Random;

/**
 * Initializes the database with Context Tables
 * Currently supports following tables:
 * Events
 * Location
 * Contacts
 *
 * @author harsh
 * @version 1.0
 * @see android.database.sqlite.SQLiteDatabase
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class DatabaseSQLite extends SQLiteOpenHelper {
    /**
     * table names
     * event = "Event"
     * location = "Location"
     * contact = "Contact"
     * eventrelation = "Rel_Event"
     */
    final static String tContext = "ContextDataTypes";
    final static int tidEvent = 1;
    final static String tEvent = "Event";
    final static int tidLocation = 2;
    final static String tLocation = "Location";
    final static int tidContact = 3;
    final static String tContact = "Contact";
    final static int tidMovie = 4;
    final static String tMovie = "Movie";
    final static String trEventAndContact = "Rel_Event_Contact";
    final static String trEventAndLocation = "Rel_Event_Location";
    /**
     * database name = Contexts.db
     * REVISION HISTORY
     * database version = 4
     * 3 : ADDED MOVIE TABLE
     */
    private static final String database_name = "Contexts.db";
    private static final int database_version = 2;

    public DatabaseSQLite(Context context) {
        super(context, database_name, null, database_version);
    }


    /**
     * Called when System creates Database
     * creates all tables and schemas
     *
     * @param db handler of database created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + tContext
                + "(contextid INTEGER PRIMARY KEY, contextname TEXT NOT NULL);";
        db.execSQL(sql);

        String cID = "_id";

        //
        //Event Table
        //_id      INTEGER PRIMARY KEY AUTOINCREMENT
        //title    TEXT    NOT NULL
        //date     INTEGER NOT NULL
        //uri      TEXT    NOT NULL
        //
        sql = "create table if not exists " + tEvent
                + "(" + cID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT NOT NULL, date INTEGER NOT NULL, uri TEXT NOT NULL);";
        db.execSQL(sql);
        db.execSQL("insert into " + tContext + " values(" + tidEvent + ", '" + tEvent + "');");

        //
        //Location table
        //_id      INTEGER PRIMARY KEY AUTOINCREMENT
        //place    TEXT    NOT NULL
        //XPOS     REAL    NOT NULL
        //YPOS     REAL    NOT NULL
        //
        sql = "create table if not exists " + tLocation
                + "(" + cID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "place TEXT NOT NULL, "
                + "xpos REAL NOT NULL, ypos REAL NOT NULL);";
        db.execSQL(sql);
        db.execSQL("insert into " + tContext + " values(" + tidLocation + ", '" + tLocation + "');");

        //
        //Contact table
        //_id      INTEGER PRIMARY KEY AUTOINCREMENT
        //name     TEXT    NOT NULL
        //number   TEXT    NOT NULL
        //email    TEXT    NOT NULL
        //
        sql = "create table if not exists " + tContact
                + "(" + cID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL,"
                + "number TEXT NOT NULL,"
                + "email TEXT NOT NULL);";
        db.execSQL(sql);
        db.execSQL("insert into " + tContext + " values(" + tidContact + ", '" + tContact + "');");

        //
        //Movie Table
        //_id       INTEGER FOREIGN KEY
        //ticketID  TEXT    NOT NULL
        //seats     TEXT    NOT NULL
        //
        sql = "create table if not exists " + tMovie
                + "(" + cID + " INTEGER, "
                + "ticketID TEXT NOT NULL, "
                + "seats TEXT NOT NULL, "
                + "FOREIGN KEY(" + cID + ") REFERENCES " + tEvent + "(" + cID + "));";
        db.execSQL(sql);
        db.execSQL("insert into " + tContext + " values(" + tidMovie + ", '" + tMovie + "');");

        //
        //Relation Table : Event and Contact
        //e_id     INTEGER FOREIGN KEY REFERENCES Event(_id)
        //c_id     INTEGER FOREIGN KEY REFERENCES Contact(_id)
        //e_type   INTEGER FOREIGN KEY REFERENCES Context(contextid)
        //c_type   INTEGER FOREIGN KEY REFERENCES Context(contextid)
        //
        sql = "create table if not exists "
                + trEventAndContact
                + "(e_id INTEGER, c_id INTEGER,"
                + "e_type TEXT NOT NULL, c_type TEXT NOT NULL,"
                + "FOREIGN KEY(e_id) REFERENCES " + tEvent + "(" + cID + "),"
                + "FOREIGN KEY(c_id) REFERENCES " + tContact + "(" + cID + "),"
                + "FOREIGN KEY(e_type) REFERENCES " + tContext + "(" + "contextid " + "),"
                + "FOREIGN KEY(c_type) REFERENCES " + tContext + "(" + "contextid" + "));";
        db.execSQL(sql);

        //
        //Relation Table : Event and Location

        //e_id     INTEGER FOREIGN KEY REFERENCES Event(_id)
        //l_id     INTEGER FOREIGN KEY REFERENCES Location(_id)
        //e_type   INTEGER FOREIGN KEY REFERENCES Context(contextid)
        //l_type   INTEGER FOREIGN KEY REFERENCES Context(contextid)
        //
        sql = "create table if not exists "
                + trEventAndLocation
                + "(e_id INTEGER, l_id INTEGER,"
                + "e_type TEXT NOT NULL, l_type TEXT NOT NULL,"
                + "FOREIGN KEY(e_id) REFERENCES " + tEvent + "(" + cID + "),"
                + "FOREIGN KEY(l_id) REFERENCES " + tLocation + "(" + cID + "),"
                + "FOREIGN KEY(e_type) REFERENCES " + tContext + "(" + "contextid " + "),"
                + "FOREIGN KEY(l_type) REFERENCES " + tContext + "(" + "contextid" + "));";
        db.execSQL(sql);

        Random random = new Random();
        db.beginTransaction();
        long time_send = new Date().getTime();
        int lim = 5000;
        for (int i = 0; i < lim; i++) {
            sql = "insert into " + tEvent + " (title,date,uri) values"
                    + "('Event" + i + "'," + 1412017002 + (i * 5000) + ",'harshp.com');";
            db.execSQL(sql);
            sql = "insert into " + tLocation + " (place,xpos,ypos) values"
                    + "('Location" + i + "'," + 1 + (0.5 * i) + "," + (255 - (0.5 * i)) + ");";
            db.execSQL(sql);
            sql = "insert into " + tContact + " (name,number,email) values"
                    + "('Contact" + i + "','Number" + i + "','contact" + i + "@cs.ucc.ie');";
            db.execSQL(sql);
        }
        time_send = new Date().getTime() - time_send;
        Log.v("MSC_DB_IT", "insert time: " + time_send);

        Log.v("MSC_DB", "lim Events, Locations, Contacts added to database");

        for (int i = 0; i < lim; i++) {
            sql = "insert into " + trEventAndLocation + " values"
                    + "(" + random.nextInt(lim) + "," + random.nextInt(lim) + ","
                    + tidEvent + "," + tidLocation + ");";
            db.execSQL(sql);
            sql = "insert into " + trEventAndContact + " values"
                    + "(" + random.nextInt(lim) + "," + random.nextInt(lim) + ","
                    + tidEvent + "," + tidContact + ");";
            db.execSQL(sql);
        }
        Log.v("MSC_DB", "10000 relations between Events and Locations, Contacts added to database");
        db.setTransactionSuccessful();
        db.endTransaction();
        time_send = new Date().getTime() - time_send;
        Log.v("MSC_DB_IR", "insert time: " + time_send);

        Log.v("PA_SQL", "Tables created : event, location, contact, rel_event");
    }

    /**
     * Called when system updates database (through app)
     * deletes previous records if needed
     * re-init tables
     *
     * @param db      handler of database
     * @param ver_old old version
     * @param ver_new new (if) version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int ver_old, int ver_new) {
        if (ver_new > ver_old) {
            clean(db);
        }
    }


    public void clean(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + tContext);
        db.execSQL("DROP TABLE IF EXISTS " + tEvent);
        db.execSQL("DROP TABLE IF EXISTS " + tLocation);
        db.execSQL("DROP TABLE IF EXISTS " + tContact);
        db.execSQL("DROP TABLE IF EXISTS " + tMovie);
        db.execSQL("DROP TABLE IF EXISTS " + trEventAndContact);
        db.execSQL("DROP TABLE IF EXISTS " + trEventAndLocation);
        onCreate(db);
        Log.v("PA_SQL", "Database cleaned");
    }
}
