/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.controller;

import com.saabre.setup.system.base.Controller;
import com.saabre.setup.system.base.Module;
import java.util.List;

/**
 *
 * @author Lifaen
 */
public class MainController extends Controller {
    
    // -- Attributes --
        
    private ConfigController config;
    private List<Module> moduleList;
    
    private boolean configLoaded;

    // -- Methods --
    
    public void load() throws Exception
    {
        // -- Load Configuration --
        config = new ConfigController();
        if(listener != null) listener.onConfigLoading(config);
        
        config.run();
        moduleList = config.getModuleList();
        
        configLoaded = true;
    }
    
    public void run() throws Exception
    {
        if(!configLoaded) 
            throw new Exception("config not loaded");
        
        for(Module module : moduleList)
        {
            if(listener != null) listener.onModuleStart(module);
            module.activate();
            if(listener != null) listener.onModuleEnd();
        }
    }
    
    // -- Listener --
    
    private Listener listener;
    public void setListener(Listener l) { this.listener = l; }
    
    public static interface Listener 
    {
        void onConfigLoading(ConfigController controller);  
        
        void onModuleStart(Module module);
        void onModuleEnd();
    }
    
    // -- Getters and Setters --

    public List<Module> getModuleList() {
        return moduleList;
    }    
}
