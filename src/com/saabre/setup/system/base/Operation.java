/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.base;

/**
 *
 * @author Lifaen
 */
public abstract class Operation {
    
    // -- Attributes --
    protected String type;
    protected boolean enabled;
    protected Object config;
    
    // -- Methods --
    
    public abstract void activate() throws Exception;
    public abstract void run() throws Exception;
    
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
