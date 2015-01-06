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
import com.saabre.setup.helper.TemplateHelper;
import com.saabre.setup.module.analysis.AnalysisBuilder;
import com.saabre.setup.module.analysis.AnalysisOperation;
import com.x5.template.Chunk;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

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
        stringBuilder.append(getChunk("Diskio").toString());
        
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


