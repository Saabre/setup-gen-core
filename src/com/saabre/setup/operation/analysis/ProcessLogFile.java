/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saabre.setup.operation.analysis;

import com.saabre.setup.helper.FileHelper;
import com.saabre.setup.module.analysis.AnalysisCpuProcessing;
import com.saabre.setup.module.analysis.AnalysisMainProcessing;
import com.saabre.setup.module.analysis.AnalysisOperation;

/**
 *
 * @author Lifaen
 */
public class ProcessLogFile extends AnalysisOperation 
{
    
    // -- Methods --
    
    @Override
    public void loadConfig() throws Exception 
    {
        
    }
    
    @Override
    public void run() throws Exception 
    {
        AnalysisMainProcessing main = new AnalysisMainProcessing();
        
        main.readFile(FileHelper.getAnalyisOutputFolder() + "monitor.log");        
        main.printFile(FileHelper.getAnalyisOutputFolder() + "monitor.csv");
        
        AnalysisCpuProcessing cpu = new AnalysisCpuProcessing();
        
        cpu.readFile(FileHelper.getAnalyisOutputFolder() + "cpu.log");        
        cpu.printFile(FileHelper.getAnalyisOutputFolder() + "cpu.csv");
    }

    private void processLine(String line) 
    {
        
    }
    
}
