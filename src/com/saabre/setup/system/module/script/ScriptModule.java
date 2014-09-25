/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.module.script;

import com.saabre.setup.system.base.Module;
import com.saabre.setup.system.module.script.ScriptBuilder;

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

        builder.setOperationList(profile.getScriptOperationList());        
        builder.generate();
        builder.write("data/output/script/" + profile.getName() + ".sh");

        println("Profile generated !\n");
    }

    @Override
    public void postRun() throws Exception 
    {
        
    }
    
}
