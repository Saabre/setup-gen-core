/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saabre.setup.module.analysis;

import com.saabre.setup.helper.FileHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Lifaen
 */
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
