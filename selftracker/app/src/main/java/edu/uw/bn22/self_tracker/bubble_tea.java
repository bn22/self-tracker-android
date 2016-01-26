package edu.uw.bn22.self_tracker;

/**
 * Created by bruceng on 1/24/16.
 */
public class bubble_tea {
    public int rating;
    public String name;
    public String location;
    public String time;

    //Creates a new bubble tea object
    public bubble_tea(int curRating, String curName, String curLocation, String curTime) {
        rating = curRating;
        name = curName;
        location = curLocation;
        time = curTime;
    }

    //Returns a String representation of the class
    public String toString() {
        return name + " Entry";
    }
}
