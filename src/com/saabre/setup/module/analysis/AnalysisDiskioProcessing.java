/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saabre.setup.module.analysis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Lifaen
 */
public class AnalysisDiskioProcessing extends AnalysisProcessing 
{
    // -- Attributes --
    
    private String prefix;
    private int lineCount;
    
    private Calendar calendar;
    private Calendar base;
    
    // -- Constructors --
    
    public AnalysisDiskioProcessing()
    {
        prefix = "";
        currentTimestamp = 0;
        lineCount = 0;
        
        calendar = GregorianCalendar.getInstance();
        base = GregorianCalendar.getInstance();
    }

    // -- Methods --
    
    @Override
    protected void onLine(String line) throws Exception {
        if(lineCount < 8)
        {
            lineCount++;
            return; 
        }
        
        if(line.isEmpty())
            return;
        
        String[] cols = line.split(",");
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        Date date = sdf.parse(cols[2]);
        
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, base.get(Calendar.YEAR));
        
        long timestamp = calendar.getTime().getTime() / 1000;
        
        if(timestamp > currentTimestamp)
        {
            currentTimestamp = timestamp;
            printRow();
        }
        
        store(prefix + "read", cols[0]);
        store(prefix + "write", cols[1]);
    }
    
}
