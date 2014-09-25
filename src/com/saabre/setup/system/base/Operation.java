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
    protected OperationOutput output;
    
    // -- Output --
    
    public static class OperationOutput extends Output
    {
        public Channel data = new Data();
        public Channel op = new Operation();
    }
    
    // -- Methods --
    
    public void printBefore()
    {
        output.op.print(type+": ");
    }
    
    public void printAfter()
    {
        output.op.append("OK !\n");
    }
    
    public void activate() throws Exception
    {      
        printBefore();
        run();
        printAfter();
    }

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

    public void setOutput(OperationOutput output) {
        this.output = output;
    }

}
