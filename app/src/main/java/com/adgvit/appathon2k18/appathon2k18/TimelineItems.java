package com.adgvit.appathon2k18.appathon2k18;

/**
 * Created by Shashank Gutgutia on 08-02-2018.
 */

public class TimelineItems {

    String time;
    String heading;
    String descrpt;

    public TimelineItems(String time, String heading, String descrpt) {
        this.time = time;
        this.heading = heading;
        this.descrpt = descrpt;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescrpt() {
        return descrpt;
    }

    public void setDescrpt(String descrpt) {
        this.descrpt = descrpt;
    }
}
