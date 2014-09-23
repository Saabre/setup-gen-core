/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system;

import com.x5.template.Chunk;
import com.x5.template.Theme;

/**
 *
 * @author Lifaen
 */
abstract public class Operation {
    
    // -- Attributes --
    private String type;
    private boolean enabled;
    private Object config;
    
    protected Theme chunkFactory;
    
    // -- Methods --

    public String generate() {
        System.out.print("  - "+ type +" operation : ");
        
        chunkFactory = new Theme("data/operation", type);
        chunkFactory.setDefaultFileExtension("sh");
        
        String result = run();
        
        System.out.println("OK ! ");
        return result;
    }
    
    protected Chunk getMainChunk()
    {
        return chunkFactory.makeChunk(type + "#Main");
    }
    
    protected abstract String run();
    
    // -- Getters and setters --
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Object getConfig() {
        return config;
    }

    public void setConfig(Object config) {
        this.config = config;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
