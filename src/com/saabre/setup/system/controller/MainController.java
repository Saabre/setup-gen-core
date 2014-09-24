/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.controller;

import com.saabre.setup.helper.FileHelper;
import com.saabre.setup.system.script.Profile;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Lifaen
 */
public class MainController {
    
    // -- Attributes --
        
    private Yaml yaml = new Yaml();
    
    // -- Methods --
    
    public void run() throws Exception
    {
        // Load base config --
        System.out.print("Load config.yaml : ");
        
        List<Object> profiles = loadBaseConfig();
        
        System.out.println("Done, "+ profiles.size() +" profile(s) found\n");
        
        // Load profiles
        for(Object obj : profiles)
        {
            String name = (String) obj;
            System.out.println("Load profile."+ name +".yaml : ");
            
            Profile profile = loadProfile(name);
            
            System.out.println("Profile loaded !\n");
            System.out.println("Generate "+ name +" : ");
            
            profile.generate();
            
            System.out.println("Profile generated !\n");
        }
    }
    
    private List<Object> loadBaseConfig() throws Exception
    {
        // Get configuration --
        InputStream baseConfigInput = FileHelper.readConfigFile("config");
        Map<String, Object> object = (Map<String, Object>) yaml.load(baseConfigInput);
        
        // Return profiles names --
        List<Object> profiles = (List<Object>) object.get("profiles");
        if(profiles == null)
            throw new Exception("profiles not found");
        
        return profiles;
    }

    private Profile loadProfile(String name) throws Exception 
    {
        // Get configuration for this profile --
        InputStream baseConfigInput = FileHelper.readConfigFile("profile." + name);
        Map<String, Object> config = (Map<String, Object>) yaml.load(baseConfigInput);
        
        // Store profiles data --
        Profile profile = new Profile();
        
        profile.setName(name);
        profile.setPath("profile." + name + ".yaml");
        profile.load(config);
        
        return profile;
    }
}
