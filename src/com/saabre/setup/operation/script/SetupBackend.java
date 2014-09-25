/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.operation.script;

import com.saabre.setup.system.module.script.ScriptOperation;
import com.x5.template.Chunk;

/**
 *
 * @author Lifaen
 */
public class SetupBackend extends ScriptOperation {

    @Override
    public void loadConfig() throws Exception 
    { 
        
    }
    
    @Override
    public void run() 
    {
        Chunk html = getChunk("Main");
        
        String path = "/mnt/";
        String fileName = path + "swap1.2g";

        html.set("path", path);
        html.set("fileName", fileName);
        html.set("base", 1024);
        html.set("clusterNb", 2097152);

        builder.append(html.toString());
    }
    
}
