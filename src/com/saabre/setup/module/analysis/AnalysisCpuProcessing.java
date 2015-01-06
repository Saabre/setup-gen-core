/*

The MIT License (MIT)

Copyright (c) 2015 Saabre

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

 */

package com.saabre.setup.module.analysis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AnalysisCpuProcessing extends AnalysisProcessing 
{
    // -- Attributes --
    
    private String prefix;
    private final Calendar base;
    private final Calendar calendar;
    
    // -- Constructors --
    
    public AnalysisCpuProcessing()
    {
        prefix = "";
        currentTimestamp = 0;
        
        base = GregorianCalendar.getInstance();
        calendar = GregorianCalendar.getInstance();
    }

    // -- Methods --
    
    @Override
    protected void onLine(String line) throws Exception {
        if(line.isEmpty() || line.matches("Linux.*")) 
            return; 
            
        line = line.replaceAll(" {1,}", " ");
        String[] cols = line.split(" ");
        String core = cols[2];
        
        if(core.equals("CPU"))
            return;
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ssaa");
        Date date = sdf.parse(cols[0]+cols[1]);
        
        
        calendar.setTime(date);
        calendar.set(base.get(Calendar.YEAR), base.get(Calendar.MONTH), base.get(Calendar.DATE));
        
        long timestamp = calendar.getTime().getTime() / 1000;
        
        if(timestamp > currentTimestamp)
        {
            currentTimestamp = timestamp;
            printRow();
        }
        
        core = "." + core;
        store(prefix + "usr"    + core, cols[3]);
        store(prefix + "nice"   + core, cols[4]);
        store(prefix + "sys"    + core, cols[5]);
        store(prefix + "iowait" + core, cols[6]);
        store(prefix + "irq"    + core, cols[7]);
        store(prefix + "soft"   + core, cols[8]);
        store(prefix + "steal"  + core, cols[9]);
        store(prefix + "guest"  + core, cols[10]);
        store(prefix + "gnice"  + core, cols[11]);
        store(prefix + "idle"   + core, cols[12]);
    }
    
}
