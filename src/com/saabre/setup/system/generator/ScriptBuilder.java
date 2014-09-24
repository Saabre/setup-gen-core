/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.generator;

import com.x5.template.Chunk;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author Lifaen
 */
public class ScriptBuilder extends TemplateBuilder
{
    // -- Attributes --
    
    private String result = "";
    private List<Operation> operations;
    private boolean generated = false;
            
    // -- Management Methods --
    
    public void generate()
    {
        if(generated) return; // Already generated --
        
        // Initialisation --
        loadChunkFactory("system", "BuildScript");
        
        // Build the script --
        StringBuilder builder = new StringBuilder();
        builder.append(generateScriptHeader());
        
        for(Operation operation : operations)
            generateOperation(builder, operation);
        
        builder.append(generateScriptFooter());
        
        // Rendering --
        result = builder.toString();
        generated = true;
    }
    
    private void generateOperation(StringBuilder builder, Operation operation) {
        builder.append(generateOperationHeader(operation));
        
        operation.generate(builder);
        
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
        Chunk chunk = getMainChunk();
        return chunk.toString();
    }
    
    private String generateScriptFooter() {
        Chunk chunk = getChunk("Footer");
        return chunk.toString();
    }
    
    private String generateOperationHeader(Operation operation) {
        Chunk chunk = getChunk("OperationHeader");
        
        chunk.set("name", operation.getType());
        
        return chunk.toString();
    }
    
    private String generateOperationFooter(Operation operation) {
        Chunk chunk = getChunk("OperationFooter");
        
        chunk.set("name", operation.getType());
        
        return chunk.toString();
    }
    
    // -- Getters and setters --
    
    public void setOperationList(List<Operation> operations) 
    {
        this.operations = operations;
    }    
}
