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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AnalysisProcessing {
    
    // -- Attributes --
    
    protected long currentTimestamp;
    protected Map<String, String> store; 
    protected StringBuilder builder;
    protected boolean headerPrinted;
    
    // -- Constructor --
    
    public AnalysisProcessing()
    {
        currentTimestamp = 0;
        store = null;
        builder = new StringBuilder();
        headerPrinted = false;
    }
    
    // -- Methods --
    
    public void readFile(String path) throws Exception
    {
        File f = new File(path);
        
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
           onLine(line);
        }
        br.close();
    }
    
    public void printFile(String path) throws Exception
    {
        // Print CSV file --
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        writer.append(builder);
        writer.close();
    }

    protected abstract void onLine(String line) throws Exception;
    
    // -- Storing Methods --
    
    public void store(String key, String value)
    {
        store.put(key, value);
    }
    
    public void printRow()
    {
        if(store != null)
        {
            if(!headerPrinted) // Second call, Header --
            {
                builder.append("time");
                printSet(store.keySet());
                headerPrinted = true;
            }
            
            // Since second call, Row --
            builder.append(currentTimestamp);
            printSet(store.values());
        }
        else // First call, Initialisation --
        {
            store = new LinkedHashMap<>();
        }
    }
    
    public void printSet(Collection<String> set)
    {
        for (String key : set) 
            builder.append(";").append(key);
        
        builder.append("\n");
    }
    
}
