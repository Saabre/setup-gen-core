/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saabre.setup.operation.analysis;

import com.saabre.setup.helper.FileHelper;
import com.saabre.setup.module.analysis.AnalysisOperation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Lifaen
 */
public class ProcessLogFile extends AnalysisOperation 
{
    // -- Attributes --
    
    private Pattern instructionPattern;
    private List<Section> sectionList;
    
    private boolean headerPrinted;
    private Section currentSection;
    private long currentTimestamp;
    
    private Map<String, String> store; 
    private StringBuilder builder;
    
    // -- Methods --
    
    @Override
    public void loadConfig() throws Exception 
    {
        instructionPattern = Pattern.compile("^#([a-zA-Z]+) ?(.*)");
        sectionList = new LinkedList<>();
        currentTimestamp = 0;
        store = null;
        builder = new StringBuilder();
        headerPrinted = false;
    }
    
    @Override
    public void run() throws Exception 
    {
        // Load section classes --
        
        sectionList.add(new DfRawSection());
        
        // Read monitor file --
        File f = new File(FileHelper.getAnalyisOutputFolder() + "monitor.log");
        
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
           processLine(line);
        }
        br.close();
        
        // Print CSV file --
        PrintWriter writer = new PrintWriter(FileHelper.getAnalyisOutputFolder() + "monitor.csv", "UTF-8");
        writer.append(builder);
        writer.close();
    }

    private void processLine(String line) 
    {
        if(line.matches("#.+")) // Instruction --
        {
            Matcher m = instructionPattern.matcher(line);
            if(!m.find())
                return; // Bad instruction --
            
            String instruction = m.group(1);
            String param = m.group(2);
            
            if(instruction.equals("TIME")) // Handle time instruction --
            {
                currentTimestamp = Long.parseLong(param);
                printRow();
            }
            else // Handle section instructions --
            {
                currentSection = null;
                for (Section section : sectionList) {
                    if(section.getTag().equals(instruction)) {
                        currentSection = section;
                        break;
                    }
                }
            }
        }
        else if(!line.isEmpty())
        {
            if(currentSection != null)
                currentSection.onLine(line);
        }
    }
    
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
    
    // -- Internal classes --
    
    private abstract class Section {
        public abstract String getTag();
        public abstract void onLine(String line);
    }
    
    private class DfRawSection extends Section {
        @Override public String getTag() { return "DfRaw"; }

        @Override
        public void onLine(String line) {
            // Ignore Header --
            if(line.matches("Filesystem.*")) 
                return; 
            
            line = line.replaceAll(" {1,}", " ");
            String[] cols = line.split(" ");
            String prefix = "disk.";
            String mount = cols[5];
            
            store(prefix + "total." + mount, cols[1]);
            store(prefix + "used."  + mount, cols[2]);
            store(prefix + "free."  + mount, cols[3]);
        }        
    }
    
    

    
}
