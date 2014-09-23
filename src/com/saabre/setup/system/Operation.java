/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system;

/**
 *
 * @author Lifaen
 */
abstract public class Operation {
    
    // -- Attributes --
    private String type;
    private boolean enabled;
    private Object config;
    
    // -- Methods --

    public void generate() {
        System.out.print("  - "+ type +" operation : ");
        run();
        System.out.println("OK ! ");
    }
    
    protected abstract void run();
    
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
