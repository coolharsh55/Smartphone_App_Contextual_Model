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

import android.net.Uri;
import android.os.Parcel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Event Context
 *
 * @author harsh
 * @version 1.0
 */
public class EventData extends ContextData {
    public static final Creator<EventData> CREATOR
            = new Creator<EventData>() {
        public EventData createFromParcel(Parcel in) {
            return new EventData(in);
        }

        public EventData[] newArray(int size) {
            return new EventData[size];
        }
    };
    protected String eventname;
    protected Date date;
    protected LocationData location;
    protected ContactData contact;
    protected Uri uri;

    /**
     * Param Constructor
     *
     * @param eventname Name of the Event
     * @param date      Date for the event
     * @param location  LocationData object
     * @param contact   ArrayList of ContactData objects
     */
    public EventData(String eventname, Date date, LocationData location,
                     ContactData contact, Uri uri) {
        setEventname(eventname);
        setDate(date);
        setLocation(location);
        setContact(contact);
        setUri(uri);
    }

    public EventData(Parcel in) {
        super();
        eventname = in.readString();
        date = new Date(in.readLong());
        location = in.readParcelable(LocationData.class.getClassLoader());
        contact = in.readParcelable(ContactData.class.getClassLoader());
        uri = Uri.parse(in.readString());
    }

    /**
     * short description of context object
     * will contain only vital fields and information
     *
     * @return String containing short description
     */
    @Override
    public String shortDescribe() {
        String s;
        s = eventname + " on ";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
        s += sdf.format(date);
        return s;
    }

    /**
     * longer description of context object
     * will contain all the fields and more information
     *
     * @return String containing long description
     */
    @Override
    public String longDescribe() {
        String s;
        s = eventname + " on ";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
        s += sdf.format(date);
        if (location != null) {
            s = s + " at " + location.placename;
        }
        if (contact != null) {
            s = s + " with " + contact.getName();
        }
        if (uri != null) {
            s = s + ". More info at " + uri.toString();
        }
        return s;
    }

    /**
     * checks the fields and returns if object is valid
     *
     * @return true if all fields are valid, false otherwise
     */
    @Override
    public boolean checkFields() {
        if (!checkEventname(eventname)) return false;
        if (!location.checkFields()) return false;
        if (!contact.checkFields()) return false;
        if (!checkUri()) return false;
        return true;
    }

    protected boolean checkUri() {
        // TODO check URI
        return true;
    }

    /**
     *
     * @return String event name
     */
    public String getEventname() {
        return eventname;
    }

    /**
     *
     * @param eventname String event name
     */
    public void setEventname(String eventname) {
        if (!checkEventname(eventname)) return;
        this.eventname = eventname;
    }

    /**
     *
     * @param eventname String event name
     * @return true if valid, false otherwise
     */
    protected boolean checkEventname(String eventname) {
        if (eventname == null || eventname.equals("")) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return LocationData object
     */
    public LocationData getLocation() {
        return location;
    }

    /**
     *
     * @param location LocationData object
     */
    public void setLocation(LocationData location) {
        this.location = location;
    }

    /**
     * @return ArrayList of ContactData objects
     */
    public ContactData getContact() {
        return contact;
    }

    /**
     *
     * @param contact ArrayList of ContactData objects
     */
    public void setContact(ContactData contact) {
        this.contact = contact;
    }

    /**
     *
     * @return java.util.Date object
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date java.util.Date object
     */
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(eventname);
        parcel.writeLong(date.getTime());
        parcel.writeParcelable(location, i);
        parcel.writeParcelable(contact, i);
        parcel.writeString(uri.toString());
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    // TODO add contact individually
}
