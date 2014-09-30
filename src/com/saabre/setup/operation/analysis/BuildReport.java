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
        Chunk chunk = getChunk("Main");
        FileHelper.write(FileHelper.getAnalyisOutputFolder() + "Report.Rmd", chunk.toString());
        
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


