/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.module;

import com.saabre.setup.system.controller.Controller;
import com.saabre.setup.system.script.Profile;
import com.saabre.setup.system.script.ScriptBuilder;
import java.util.List;

/**
 *
 * @author Lifaen
 */
public class ScriptModule extends Module {
    
    // -- Methods --

    @Override
    public void preRun() throws Exception 
    {
        
    }
    
    @Override
    public void run() throws Exception
    {
        println("Generate "+ profile.getName() +" : ");
            
        ScriptBuilder builder = new ScriptBuilder();

        builder.setOperationList(profile.getOperationList());        
        builder.generate();
        builder.write("data/output/script/" + profile.getName() + ".sh");

        println("Profile generated !\n");
    }

    @Override
    public void postRun() throws Exception 
    {
        
    }
    
}
