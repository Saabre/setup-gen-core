/*

The MIT License (MIT)

Copyright (c) 2015 Saabre

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

 */

package com.saabre.setup.module.script;

import com.saabre.setup.helper.FileHelper;
import com.saabre.setup.system.base.Module;
import com.saabre.setup.system.base.Profile;

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
