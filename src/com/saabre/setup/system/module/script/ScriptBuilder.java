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
    private List<ScriptOperation> operations;
    private boolean generated = false;
            
    // -- Management Methods --
    
    public void generate() throws Exception
    {
        if(generated) return; // Already generated --
        
        // Build the script --
        StringBuilder builder = new StringBuilder();
        builder.append(generateScriptHeader());
        
        for(ScriptOperation operation : operations)
            generateOperation(builder, operation);
        
        builder.append(generateScriptFooter());
        
        // Rendering --
        result = builder.toString();
        generated = true;
    }
    
    private void generateOperation(StringBuilder builder, ScriptOperation operation) throws Exception {
        builder.append(generateOperationHeader(operation));
        
        operation.setBuilder(builder);
        operation.activate();
        
        builder.append(generateOperationFooter(operation));
    }
    
    public void write(String path) throws Exception
    {    
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
    
    public void setOperationList(List<ScriptOperation> operations) 
    {
        this.operations = operations;
    }    

    private Chunk getChunk(String name) {
        return TemplateHelper.getSystemChunk("BuildScript", name);
    }
}
