/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.operation.script;

import com.saabre.setup.helper.FileHelper;
import com.saabre.setup.helper.NameHelper;
import com.saabre.setup.system.module.script.ScriptOperation;
import com.x5.template.Chunk;
import java.util.Map;

/**
 *
 * @author Lifaen
 */
public class AllocateSwap extends ScriptOperation {

    // -- Attributes --
    private String amount;
    private long byteCount;
            
    // -- Overrided Methods --
            
    @Override
    public void loadConfig() throws Exception 
    {
        Map<String, Object> config = (Map<String, Object>) this.config;
        amount = (String) config.get("amount");
        byteCount = FileHelper.readableToByteCount(amount);
    }
    
    @Override
    public void run() throws Exception
    {
        Chunk html = getChunk("Main");
        
        String path = "/mnt/";
        String fileName = path + "swap."+ NameHelper.getFileDate() +"."+ amount.replaceAll(" ", "");

        html.set("path", path);
        html.set("fileName", fileName);
        html.set("base", 1024);
        html.set("clusterNb", (long) byteCount / 1024);

        builder.append(html.toString());
    }    
}
