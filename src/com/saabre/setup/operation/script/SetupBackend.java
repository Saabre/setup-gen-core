/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.operation.script;

import com.saabre.setup.module.script.ScriptOperation;
import com.x5.template.Chunk;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lifaen
 */
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
