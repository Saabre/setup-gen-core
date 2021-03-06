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

import com.saabre.setup.helper.ClassHelper;
import com.saabre.setup.helper.FileHelper;
import com.saabre.setup.helper.NameHelper;
import com.saabre.setup.module.analysis.AnalysisOperation;
import com.saabre.setup.module.remote.RemoteOperation;
import com.saabre.setup.module.script.ScriptOperation;
import com.saabre.setup.system.base.Controller;
import com.saabre.setup.system.base.Module;
import com.saabre.setup.system.base.Operation;
import com.saabre.setup.system.base.Profile;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConfigController extends Controller {
    
    // -- Attributes --
    
    private final List<Profile> profileList = new LinkedList<>();
    private final List<Module> moduleList = new LinkedList<>();
    
    // -- Methods --
    
    public void run() throws Exception
    {        
        // Load base config --
        if(listener != null) listener.onRootConfigStart();
        
        Map<String, Object> object = FileHelper.loadFile("config");
        
        List<Object> profiles = (List<Object>) object.get("profiles");
        List<Object> modules = (List<Object>) object.get("modules");
        
        if(profiles == null)
            throw new Exception("profiles not found");
        if(modules == null)
            throw new Exception("modules not found");
        
        if(listener != null) listener.onRootConfigEnd(profiles.size(), modules.size());
        
        // Load profiles --
        for(Object obj : profiles)
        {
            String name = (String) obj;
            if(listener != null) listener.onProfileConfigStart(name);
            
            Profile profile = loadProfile(name);
            profileList.add(profile);
            
            if(listener != null) listener.onProfileConfigEnd(profile);
        }
        
        // Load modules --
        for(Object obj : modules)
        {
            String name = (String) obj;
            if(listener != null) listener.onModuleConfigStart(name);
            
            Module module = loadModule(name);
            moduleList.add(module);
            
            if(listener != null) listener.onModuleConfigEnd(module);
        }
    }
    
    private Profile loadProfile(String name) throws Exception 
    {
        // Get configuration for this profile --
        Map<String, Object> config = FileHelper.loadFile("profile." + name);
        
        // Store profiles data --
        Profile profile = new Profile();
        
        profile.setName(name);
        profile.setPath("profile." + name + ".yaml");
        
        // Parse operations --
        List<Object> scripts = (List<Object>) config.get("script");
        List<Object> remotes = (List<Object>) config.get("remote");
        List<Object> analysis = (List<Object>) config.get("analysis");
        
        if(scripts != null)
            for(Object line : scripts)
                profile.addScript((ScriptOperation) loadOperation((Map<String, Object>) line, "script"));
        
        if(remotes != null)
            for(Object line : remotes)
                profile.addRemote((RemoteOperation) loadOperation((Map<String, Object>) line, "remote"));
        
        if(analysis != null)
            for(Object line : analysis)
                profile.addAnalysis((AnalysisOperation) loadOperation((Map<String, Object>) line, "analysis"));
        
        return profile;
    }
    
    private Operation loadOperation(Map<String, Object> config, String operationType) throws Exception    
    {
        String type = (String) config.get("type");
        if(listener != null) listener.onOperationConfigStart(type);
        
        // Load the object --
        String folder = "com.saabre.setup.operation." + operationType;
        String className = folder + "." + config.get("type");
        Operation operation = (Operation) ClassHelper.newInstance(className);
        
        // Store operation data --
        operation.setConfig(config.get("config"));
        operation.setType(type);
        
        if(config.containsKey("enabled"))
            operation.setEnabled((boolean) config.get("enabled"));
        else
            operation.setEnabled(true);
        
        if(listener != null) listener.onOperationConfigEnd(operation);
        return operation;
    }
    
    private Module loadModule(String name) throws Exception {
        // Load the object --
        String folder = "com.saabre.setup.module." + name;
        String className = folder + "." + NameHelper.upper(name) + "Module";
        Module module = (Module) ClassHelper.newInstance(className);
        
        module.setName(name);
        module.setProfileList(profileList);
        
        try {
            module.setConfig(FileHelper.loadFile(name));
        }
        catch(FileNotFoundException e)
        {
        }
        
        return module;
    }
    
    // -- Getters and setters --
    
    public List<Profile> getProfileList() {
        return profileList;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }
    
    // -- Listener --
    private Listener listener;
    public void setListener(Listener l) { this.listener = l; }
    
    public static interface Listener 
    {
        public void onRootConfigStart();
        public void onRootConfigEnd(int profileCount, int moduleCount);
        
        public void onProfileConfigStart(String name);
        public void onProfileConfigEnd(Profile profile);
        
        public void onModuleConfigStart(String name); 
        public void onModuleConfigMissingFile(String path); 
        public void onModuleConfigEnd(Module module);
        
        public void onOperationConfigStart(String type);  
        public void onOperationConfigEnd(Operation operation);

    }
}
