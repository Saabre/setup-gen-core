/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.module.script;

import com.saabre.setup.helper.TemplateHelper;
import com.x5.template.Chunk;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author Lifaen
 */
public class ScriptBuilder
{
    // -- Attributes --
    
    private String result = "";
    private StringBuilder builder;
    private boolean generated = false;
    
    // -- Constructors --
    
    public ScriptBuilder()
    {
        builder = new StringBuilder();
    }
            
    // -- Management Methods --
    
    void generateHeader() 
    {    
        builder.append(generateScriptHeader());
    }

    void generateFooter() {
        builder.append(generateScriptFooter());
    }
    
    void generateOperation(ScriptOperation operation) throws Exception {
        builder.append(generateOperationHeader(operation));
        
        operation.setBuilder(builder);
        operation.activate();
        
        builder.append(generateOperationFooter(operation));
    }
    
    public void write(String path) throws Exception
    {    
        if(!generated)
        {
            result = builder.toString();
            generated = true;
        }
        
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        writer.print(result);
        writer.close();
    }
    
    // -- Generation Methods --

    private String generateScriptHeader() {
        Chunk chunk = getChunk("Header");
        return chunk.toString();
    }
    
    private String generateScriptFooter() {
        Chunk chunk = getChunk("Footer");
        return chunk.toString();
    }
    
    private String generateOperationHeader(ScriptOperation operation) {
        Chunk chunk = getChunk("OperationHeader");
        
        chunk.set("name", operation.getType());
        
        return chunk.toString();
    }
    
    private String generateOperationFooter(ScriptOperation operation) {
        Chunk chunk = getChunk("OperationFooter");
        
        chunk.set("name", operation.getType());
        
        return chunk.toString();
    }
    
    // -- Getters and setters --

    private Chunk getChunk(String name) {
        return TemplateHelper.getSystemChunk("BuildScript", name);
    }
}
