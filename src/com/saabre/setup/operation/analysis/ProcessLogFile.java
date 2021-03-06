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

package com.saabre.setup.operation.analysis;

import com.saabre.setup.helper.FileHelper;
import com.saabre.setup.module.analysis.AnalysisCpuProcessing;
import com.saabre.setup.module.analysis.AnalysisDiskioProcessing;
import com.saabre.setup.module.analysis.AnalysisMainProcessing;
import com.saabre.setup.module.analysis.AnalysisOperation;

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
        
        AnalysisDiskioProcessing diskio = new AnalysisDiskioProcessing();
        
        diskio.readFile(FileHelper.getAnalyisOutputFolder() + "diskio.log");        
        diskio.printFile(FileHelper.getAnalyisOutputFolder() + "diskio.csv");
    }

    private void processLine(String line) 
    {
        
    }
    
}
