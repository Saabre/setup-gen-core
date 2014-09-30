/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Lifaen
 */
public class NameHelper {

    public static String lower(String str)
    {
        return str.substring(0,1).toLowerCase() + str.substring(1);
    }

    public static String upper(String str)
    {
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }
    
    public static String getFileDate()
    {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(calendar.getTime());
    }
}
