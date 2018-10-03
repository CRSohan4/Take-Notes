package com.example.crplayer.takenotesnew;

import android.content.Context;

public class GetTimeAgo {

    private static final int secondMillis = 1000;
    private static final int minuteMillis = 60 * secondMillis;
    private static final int hourMillies = 60 * minuteMillis;
    private static final int dayMillis = 24 * hourMillies;

    public static String getTimeAgo(long time, Context ctx){
        if(time < 1000000000000L){
            //If timeStamp is given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();

        if(time> now || time <= 0){
            return null;
        }

        //locallise

        final long diff = now - time;

        if(diff < minuteMillis){
            return "just now";
        }else if(diff < 2 * minuteMillis){
            return "a minute ago";
        }else if(diff < 50 * minuteMillis){
            return(diff / minuteMillis + " minutes ago");
        }else if(diff < 90 * minuteMillis){
            return "an hour ago";
        }else if(diff < 24 * hourMillies){
            return diff / hourMillies + " hours ago";
        }else if(diff < 48 * hourMillies){
            return "yesterday";
        }else{
            return diff / dayMillis + " days ago";
        }
    }


}
