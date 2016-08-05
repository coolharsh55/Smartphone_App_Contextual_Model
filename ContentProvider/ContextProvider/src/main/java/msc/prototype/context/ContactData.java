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

// TODO correct documentation

/**
 * Contact context
 *
 * @author harsh
 * @version 1.0
 */
public class ContactData extends ContextData {
    public static final Creator<ContactData> CREATOR
            = new Creator<ContactData>() {
        public ContactData createFromParcel(Parcel in) {
            return new ContactData(in);
        }

        public ContactData[] newArray(int size) {
            return new ContactData[size];
        }
    };
    protected String name;
    protected String number;
    protected String email;

    /**
     * Param Constructor
     *
     * @param name   name of contact
     * @param number ArrayList of numbers
     * @param email  ArrayList of emails
     */
    public ContactData(String name, String number, String email) {
        setName(name);
        setNumber(number);
        setEmail(email);
    }

    public ContactData(Parcel in) {
        super();
        name = in.readString();
        number = in.readString();
        email = in.readString();
    }

    /**
     * short description of contact object
     * will contain name and main number
     * or if number not present, then email
     *
     * @return String containing short description
     */
    @Override
    public String shortDescribe() {
        return getName() + " <" + getNumber() + ">";
    }

    /**
     * longer description of contact object
     * will contain full name
     * all the numbers and all emails
     *
     * @return String containing long description
     */
    @Override
    public String longDescribe() {
        return shortDescribe();
    }

    /**
     * checks the fields and returns if object is valid
     *
     * @return true if all fields are valid, false otherwise
     */
    @Override
    public boolean checkFields() {
        if (!checkName(name)) return false;
        if (!checkEmail(email)) return false;
        if (!checkEmail(number)) return false;

        return true;
    }

    /**
     * @return name of contact
     */
    public String getName() {
        if (!checkName(name)) {
            return null;
        }
        return name;
    }

    /**
     * @param name valid String name
     */
    public void setName(String name) {
        if (!checkName(name)) {
            return;
        }
        this.name = name;
    }

    protected boolean checkName(String name) {
        if (name == null || name.equals("")) {
            return false;
        }
        return true;
    }

    /**
     * @return ArrayList containing numbers
     */
    public String getNumber() {
        if (!checkNumber(number)) return null;
        return number;
    }

    /**
     * @param number ArrayList of numbers
     */
    public void setNumber(String number) {
        if (!checkNumber(number)) return;
        this.number = number;
    }

    /**
     * @return true if valid, false otherwise
     */
    protected boolean checkNumber(String number) {
        // TODO parse and check Number
        return true;
    }

    /**
     * @return true if valid, false otherwise
     */
    protected boolean checkEmail(String email) {
        // TODO parse and check Email
        return true;
    }

    /**
     * @return ArrayList of emails
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email ArrayList of emails
     */
    public void setEmail(String email) {
        if (!checkEmail(email)) {
            return;
        }
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeSerializable(number);
        parcel.writeSerializable(email);
    }

    // TODO add individual number
    // TODO add individual email
}
