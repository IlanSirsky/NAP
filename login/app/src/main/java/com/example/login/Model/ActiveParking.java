/** class ActiveParking.java
    it represents a parking that is currently active.
    It contains several fields such as startTime, endTime, price,
    parkingId, status, renterId, and ownerId.
    These fields store information about the parking, including the start and end times,
    the price, the parking's ID, the status, the renter's ID, and the owner's ID.
    The class also contains several getter and setter methods to access these fields.
    Additionally, it has two methods, getStartDataAsString and getEndDataAsString,
    which return the start and end time in a formatted string respectively.  */

package com.example.login.Model;

import com.google.firebase.database.Exclude;

import java.sql.Time;
import java.util.Date;
public class ActiveParking {

    public Date startTime, endTime;
    public String price;
    public String parkingId, status;
    public String renterId, ownerId;
    public String address;

    @Exclude
    public String postedId;


    public ActiveParking() {
    }

    // constructor that initializes the startTime, endTime, price, parkingId, ownerId,
    // and status of the parking

    public ActiveParking(long startTime, long endTime, String price, String parkingId, String ownerId, String status, String address) {

        this.startTime = new Time(startTime);
        this.endTime = new Time(endTime);
        this.price = price;
        this.parkingId = parkingId;
        this.status = status;
        this.ownerId = ownerId;
        this.renterId = "";
        this.address = address;
    }


    public String getPrice() {
        return price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getParkingId() {
        return parkingId;
    }

    public String getStatus() {
        return status;
    }

    public String getRenterId() {
        return renterId;
    }

    public void setRenterId(String renterId) {
        this.renterId = renterId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getAddress() {
        return address;
    }

    @com.google.firebase.firestore.Exclude
    public String getPostedId() {
        return postedId;
    }

    // Method to set ParkingId, returns the same object
    public ActiveParking setPostedId(String postedId) {
        this.postedId = postedId;
        return this;
    }

}
