/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saabre.setup.module.analysis;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Lifaen
 */
public class AnalysisMainProcessing extends AnalysisProcessing{

    // -- Attributes --
    
    private Pattern instructionPattern;
    private List<Section> sectionList;
    
    private Section currentSection;
    
    // -- Constructor --
    
    public AnalysisMainProcessing()
    {
        instructionPattern = Pattern.compile("^#([a-zA-Z]+) ?(.*)");
        sectionList = new LinkedList<>();
        
        sectionList.add(new DfRawSection());
        sectionList.add(new IostatSection());
        sectionList.add(new MeminfoSection());
    }
    
    // -- Methods --
    
    @Override
    protected void onLine(String line) throws Exception {
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
    
    // -- Internal classes --
    
    private abstract class Section {
        public abstract String getTag();
        public abstract void onLine(String line);
    }
    
    private class DfRawSection extends Section {
        @Override public String getTag() { return "DfRaw"; }
            
        String prefix = "diskspace.";

        @Override
        public void onLine(String line) {
            // Ignore Header --
            if(line.matches("Filesystem.*")) 
                return; 
            
            line = line.replaceAll(" {1,}", " ");
            String[] cols = line.split(" ");
            String mount = "." + cols[5];
            
            store(prefix + "total" + mount, cols[1]);
            store(prefix + "used"  + mount, cols[2]);
            store(prefix + "free"  + mount, cols[3]);
        }        
    }
    
    private class IostatSection extends Section {
        @Override public String getTag() { return "Iostat"; }
            
        String prefix = "diskio.";

        @Override
        public void onLine(String line) {
            // Ignore Header --
            if(line.matches("Linux.*") || line.matches("Device:.*")) 
                return; 
            
            line = line.replaceAll(" {2,}", "  ");
            String[] cols = line.split("  ");
            String mount = "." + cols[0];
            
            store(prefix + "tps" + mount, cols[1]); // Transfer per second --
            store(prefix + "read"  + mount, cols[2]); // Block read per second --
            store(prefix + "write"  + mount, cols[3]); // Block written per second --
        }        
    }
    
    private class MpstatAllSection extends Section {
        @Override public String getTag() { return "MpstatAll"; }
        
        String prefix = "cpu.";

        @Override
        public void onLine(String line) {
            // Ignore Header --
            if(line.matches("Linux.*") || line.matches(".*CPU.*")) 
                return; 
            
            line = line.replaceAll(" {1,}", " ");
            String[] cols = line.split(" ");
            String mount = "." + cols[2];
            
            store(prefix + "usr"    + mount, cols[2]);
            store(prefix + "nice"   + mount, cols[3]);
            store(prefix + "sys"    + mount, cols[4]);
            store(prefix + "iowait" + mount, cols[5]);
            store(prefix + "irq"    + mount, cols[6]);
            store(prefix + "soft"   + mount, cols[7]);
            store(prefix + "steal"  + mount, cols[8]);
            store(prefix + "guest"  + mount, cols[9]);
            store(prefix + "gnice"  + mount, cols[10]);
            store(prefix + "idle"   + mount, cols[11]);
        }        
    }
    
    private class MeminfoSection extends Section {
        @Override public String getTag() { return "Meminfo"; }
        
        String[] cols;

        @Override
        public void onLine(String line) {
            
            line = line.replaceAll(" {1,}", " ");
            cols = line.split(" ");
            
            getCol("MemTotal", "ram.total");
            getCol("MemFree", "ram.free");
            getCol("SwapTotal", "swap.total");
            getCol("SwapFree", "swap.free");
        }
        
        private void getCol(String title, String id)
        {
            if(cols[0].equals(title + ":"))
                store(id, cols[1]);
        }
    }
    
}
