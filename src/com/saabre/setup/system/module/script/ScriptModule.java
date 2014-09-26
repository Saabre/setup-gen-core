/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.module.script;

import com.saabre.setup.helper.FileHelper;
import com.saabre.setup.system.base.Module;
import com.saabre.setup.system.base.Profile;

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
        if(listener != null) listener.onProfileStart(profile);
            
        // Prepare and Header --
        ScriptBuilder builder = new ScriptBuilder();
        builder.generateHeader();
        
        // Operation list --
        for(ScriptOperation operation : profile.getScriptOperationList())
        {
            if(listener != null) listener.onOperationStart(operation);
            builder.generateOperation(operation);
            if(listener != null) listener.onOperationEnd();
        }
        
        // Footer and Writing --
        builder.generateFooter();
        builder.write(FileHelper.getScriptOutputFolder() + profile.getName() + ".sh");

        if(listener != null) listener.onProfileEnd();
    }

    @Override
    public void postRun() throws Exception 
    {
        
    }
    
    // -- Listener --
    
    private Listener listener;
    public void setListener(Listener l) { this.listener = l; }
    
    public static interface Listener 
    {        
        public void onProfileStart(Profile profile);
        public void onProfileEnd();

        public void onOperationStart(ScriptOperation operation);
        public void onOperationEnd();

    }
    
}
