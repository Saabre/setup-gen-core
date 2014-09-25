/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.module.script;

import com.saabre.setup.helper.TemplateHelper;
import com.saabre.setup.system.base.Operation;
import com.x5.template.Chunk;

/**
 *
 * @author Lifaen
 */
abstract public class ScriptOperation extends Operation {
    
    // -- Attributes --
    
    protected StringBuilder builder;
    
    // -- Alias --
    
    protected Chunk getChunk(String name)
    {
        return TemplateHelper.getScriptChunk(type, name);
    }
    
    // -- Getters and setters --
    public void setBuilder(StringBuilder builder)
    {
        this.builder = builder;
    }
    
}
