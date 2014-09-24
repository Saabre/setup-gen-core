/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.controller;

import com.saabre.setup.helper.ClassHelper;
import com.saabre.setup.helper.FileHelper;
import com.saabre.setup.system.module.Module;
import com.saabre.setup.system.script.Operation;
import com.saabre.setup.system.script.Profile;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lifaen
 */
public class ConfigController extends Controller {
    
    // -- Attributes --
    
    private final List<Profile> profileList = new LinkedList<>();
    private final List<Module> moduleList = new LinkedList<>();
    
    // -- Methods --
    
    public void run() throws Exception
    {        
        // Load base config --
        print("Load config.yaml : ");
        
        Map<String, Object> object = FileHelper.loadFile("config");
        
        List<Object> profiles = (List<Object>) object.get("profiles");
        List<Object> modules = (List<Object>) object.get("modules");
        
        if(profiles == null)
            throw new Exception("profiles not found");
        if(modules == null)
            throw new Exception("modules not found");
        
        println("Done, "+ profiles.size() +" profile(s) and "+ modules.size() +" found\n");
        
        // Load profiles --
        for(Object obj : profiles)
        {
            String name = (String) obj;
            println("Load profile."+ name +".yaml : ");
            profileList.add(loadProfile(name));
            println("Profile loaded !\n");
        }
        
        // Load modules --
        for(Object obj : modules)
        {
            String name = (String) obj;
            print("Load module "+ name +" : ");
            moduleList.add(loadModule(name));
            println("OK !");
        }
        
        println("");
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
        List<Object> operations = (List<Object>) config.get("operations");
        
        for(Object line : operations)
            profile.add(loadOperation((Map<String, Object>) line));
        
        return profile;
    }
    
    private Operation loadOperation(Map<String, Object> config) throws Exception    
    {
        System.out.print("  - "+ config.get("type") +" operation : ");
        
        // Load the object --
        String folder = "com.saabre.setup.operation.script";
        String className = folder + "." + config.get("type");
        Operation operation = (Operation) ClassHelper.newInstance(className);
        
        // Store operation data --
        operation.setConfig(config.get("config"));
        operation.setType((String) config.get("type"));
        
        if(config.containsKey("enabled"))
            operation.setEnabled((boolean) config.get("enabled"));
        else
            operation.setEnabled(true);
        
        System.out.println("OK !");
        return operation;
    }
    
    private Module loadModule(String name) throws Exception {
        // Load the object --
        String folder = "com.saabre.setup.system.module";
        String className = folder + "." + name + "Module";
        Module module = (Module) ClassHelper.newInstance(className);
        
        module.setName(name);
        module.setProfileList(profileList);
        
        return module;
    }
    
    // -- Getters and setters --
    
    public List<Profile> getProfileList() {
        return profileList;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

}
