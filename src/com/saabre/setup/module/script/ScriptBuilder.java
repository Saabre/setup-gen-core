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

package com.saabre.setup.module.script;

import com.saabre.setup.helper.FileHelper;
import com.saabre.setup.helper.TemplateHelper;
import com.x5.template.Chunk;

public class ScriptBuilder
{
    // -- Attributes --
    
    private String result = "";
    private final StringBuilder builder;
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
        
        FileHelper.write(path, result);
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
