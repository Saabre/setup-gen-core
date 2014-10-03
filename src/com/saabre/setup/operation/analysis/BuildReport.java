/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saabre.setup.operation.analysis;

import com.saabre.setup.helper.FileHelper;
import com.saabre.setup.helper.TemplateHelper;
import com.saabre.setup.module.analysis.AnalysisBuilder;
import com.saabre.setup.module.analysis.AnalysisOperation;
import com.x5.template.Chunk;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Lifaen
 */
public class BuildReport extends AnalysisOperation 
{
    
    @Override
    public void loadConfig() throws Exception 
    {
        
    }
    
    @Override
    public void run() throws Exception 
    {
        // Generate report template --
        StringBuilder stringBuilder = new StringBuilder();
        Chunk chunk;
        
        chunk = getChunk("Main");
        
        DateFormat df = DateFormat.getDateTimeInstance();
        Date today = Calendar.getInstance().getTime(); 
        chunk.set("date", df.format(today));
        
        stringBuilder.append(chunk.toString());
        
        stringBuilder.append(getChunk("Cpu").toString());
        stringBuilder.append(getChunk("Data").toString());
        stringBuilder.append(getChunk("Ram").toString());
        stringBuilder.append(getChunk("Diskspace").toString());
        
        FileHelper.write(FileHelper.getAnalyisOutputFolder() + "Report.Rmd", stringBuilder.toString(), "ISO-8859-1");
        
        // Generate report file --
        AnalysisBuilder builder = new AnalysisBuilder();
        builder.load();
        builder.cd(FileHelper.getAnalyisOutputFolder());
        builder.render("Report");
    }
    
    private Chunk getChunk(String name) {
        return TemplateHelper.getAnalysisChunk("Report", name);
    }
}


