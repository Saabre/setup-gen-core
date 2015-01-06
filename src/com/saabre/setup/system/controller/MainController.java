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

package com.saabre.setup.system.controller;

import com.saabre.setup.system.base.Controller;
import com.saabre.setup.system.base.Module;
import java.util.List;

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
