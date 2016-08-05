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

import android.os.Parcel;

/**
 * Location Context
 * used to describe a particular location
 *
 * @author harsh
 * @version 1.0
 */
public class LocationData extends ContextData {
    public static final Creator<LocationData> CREATOR
            = new Creator<LocationData>() {
        public LocationData createFromParcel(Parcel in) {
            return new LocationData(in);
        }

        public LocationData[] newArray(int size) {
            return new LocationData[size];
        }
    };
    protected String placename;
    protected double xpos;
    protected double ypos;

    /**
     * Param Constructor
     *
     * @param placename place represented by location
     * @param xpos      x-co-ordinate
     * @param ypos      y-co-ordinate
     */
    public LocationData(String placename, double xpos, double ypos) {
        setPlacename(placename);
        this.xpos = xpos;
        this.ypos = ypos;
    }

    protected LocationData(Parcel in) {
        super();
        placename = in.readString();
        xpos = in.readDouble();
        ypos = in.readDouble();
    }

    /**
     * short description of location object
     * describes the place or name of place
     *
     * @return String containing short description
     */
    @Override
    public String shortDescribe() {
        return placename;
    }

    /**
     * longer description of location object
     * provides co-ordinates along with name
     *
     * @return String containing long description
     */
    @Override
    public String longDescribe() {
        return placename;
    }

    /**
     * checks the location object fields
     *
     * @return true if all fields are valid, false otherwise
     */
    @Override
    public boolean checkFields() {
        if (placename == null || placename.equals("")) {
            placename = "Unknown";
        }
        if (xpos == 0 && ypos == 0) {
            return false;
        }
        // TODO String parse and cleaning
        // TODO check location co-ordinates
        return true;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        if (placename == null || placename.equals("")) {
            placename = "Unknown";
        }
        this.placename = placename;
    }

    public double getXpos() {
        return xpos;
    }

    public void setXpos(double xpos) {
        this.xpos = xpos;
    }

    public double getYpos() {
        return ypos;
    }

    public void setYpos(double ypos) {
        this.ypos = ypos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(placename);
        parcel.writeDouble(xpos);
        parcel.writeDouble(ypos);
    }

    // TODO get/set POS through double[]
}
