/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.base;

import com.saabre.setup.helper.NameHelper;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lifaen
 */
public abstract class Module extends Controller {
    
    // -- Attributes --
    
    private List<Profile> profileList;
    protected Map<String, Object> config;
    
    protected String name;
    protected Profile profile;
    
    // -- Methods --
    
    public void activate() throws Exception
    {
        println("/* -- "+ NameHelper.upper(name) +" process -- */\n");
        
        preRun();
        
        for(Profile current : profileList)
        {
            profile = current;
            run();
        }
        
        postRun();
    }
    
    protected abstract void preRun() throws Exception;
    protected abstract void run() throws Exception;
    protected abstract void postRun() throws Exception;
    
    // -- Getters and setters --
    
    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
