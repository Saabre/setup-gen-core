/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.generator;

/**
 *
 * @author Lifaen
 */
abstract public class Operation extends TemplateBuilder {
    
    // -- Attributes --
    private String type;
    private boolean enabled;
    private Object config;
    
    protected StringBuilder builder;
    
    // -- Methods --

    public void generate(StringBuilder builder) {
        System.out.print("  - "+ type +" operation : ");
        
        loadChunkFactory(type);
        this.builder = builder;
        
        run();
        
        System.out.println("OK ! ");
    }
    
    protected abstract void run();
    
    // -- Alias --
    
    protected void loadChunkFactory(String name)
    {
        loadChunkFactory("script", name);
    }
    
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

    public String getType() {
        return type;
    }
}
