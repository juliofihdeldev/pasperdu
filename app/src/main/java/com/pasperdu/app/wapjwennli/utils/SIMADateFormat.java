package com.pasperdu.app.wapjwennli.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by KeitelRobespierre on 11/21/2016.
 */

public class SIMADateFormat {

    /**
     * return a String of formatted Date dd-MMM-yyyy for any date
     * @param date java.util.Date
     * @return String
     */
    public static String formatCollecteDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        // Ex: 21-Nov-2016 11h37 AM
      //  sdf.applyPattern("dd-MMM-yyyy hh'h'mm a");
        sdf.applyPattern("MMM,d");

        if (date != null) return sdf.format(date.getTime());

        return "Maintenant";
    }

}
