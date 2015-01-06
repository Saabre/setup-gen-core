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

package com.saabre.setup.operation.script;

import com.saabre.setup.module.script.ScriptOperation;
import com.x5.template.Chunk;
import java.util.List;
import java.util.Map;

public class SetupBackend extends ScriptOperation {

    private String source;
    private String path;
    private List<String> stepList;
    
    @Override
    public void loadConfig() throws Exception 
    { 
        Map<String, Object> config = (Map<String, Object>) this.config;
        
        path = "/opt/" + "osrm-backend" + "/";
        source = (String) config.get("source");
        
        if(source == null)
            throw new Exception("No source defined");
        
        stepList = (List<String>) config.get("step");
    }
    
    @Override
    public void run() 
    {
        for(String step : stepList)
        {
            if(step.equals("Extract") || step.equals("Prepare"))
            {
                addChunk("BindData");
            }
            
            addChunk(step);
        }
    }
    
    private void addChunk(String name)
    {
        Chunk chunk = getChunk(name);
       
        chunk.set("path", path);
        chunk.set("source", source);

        builder.append(chunk.toString());
    }
    
}
