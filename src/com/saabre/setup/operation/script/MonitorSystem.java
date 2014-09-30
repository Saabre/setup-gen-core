/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saabre.setup.operation.script;

import com.saabre.setup.module.script.ScriptOperation;
import com.x5.template.Chunk;

/**
 *
 * @author Lifaen
 */
public class MonitorSystem extends ScriptOperation {

    // -- Attributes --
    
    private Chunk chunk;

    // -- Methods --
    
    @Override
    public void loadConfig() throws Exception 
    {
        
    }
    
    @Override
    public void run() throws Exception 
    {
        loadSimpleChunk("Main");
        loadSimpleChunk("LoopStart");
        
        // Disk --
        loadSimpleChunk("DfRaw");
        loadSimpleChunk("Iostat");
        
        // CPU --
        loadSimpleChunk("MpstatAll");
        
        // RAM --
        loadSimpleChunk("Free");
        
        // Disk --
        
        loadSimpleChunk("LoopEnd");
    }
    
    public void loadSimpleChunk(String name)
    {
        chunk = getChunk(name);
        builder.append(chunk.toString());
    }
    
}
