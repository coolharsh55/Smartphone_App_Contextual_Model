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

import java.util.Date;

/**
 * Created by coolharsh55 on 10/09/2014.
 */
public class MovieData extends EventData {
    // EVENT fields
    // String name
    // Date date
    // LocationData location
    // ContactData contact
    // URI uri

    protected String ticketID;
    protected String seats;

    public MovieData(String eventname, Date date, LocationData location, ContactData contact, Uri uri,
                     String ticketID, String seats) {
        super(eventname, date, location, contact, uri);
        this.ticketID = ticketID;
        this.seats = seats;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    @Override
    public String longDescribe() {
        String s = super.longDescribe();
        s += ". TicketID=" + ticketID;
        s += " Seats = " + seats;
        return s;
    }


}
