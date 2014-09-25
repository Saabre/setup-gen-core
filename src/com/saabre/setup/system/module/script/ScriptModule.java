/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.module.script;

import com.saabre.setup.system.base.Module;
import com.saabre.setup.system.base.Operation;
import com.saabre.setup.system.base.Output;
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
        output.op.println("Generate "+ profile.getName() +": ");
            
        // Prepare and Header --
        ScriptBuilder builder = new ScriptBuilder();
        builder.generateHeader();
        
        // Operation list --
        for(ScriptOperation operation : profile.getScriptOperationList())
        {
            operation.setOutput(new Operation.OperationOutput());
            builder.generateOperation(operation);
        }
        
        // Footer and Writing --
        builder.generateFooter();
        builder.write("data/output/script/" + profile.getName() + ".sh");

        output.op.println("Profile generated !\n");
    }

    @Override
    public void postRun() throws Exception 
    {
        
    }
    
}
