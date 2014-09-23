/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Lifaen
 */
public class Controller {
    
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
        InputStream baseConfigInput = readFile("config");
        Map<String, Object> object = (Map<String, Object>) yaml.load(baseConfigInput);
        
        List<Object> profiles = (List<Object>) object.get("profiles");
        if(profiles == null)
            throw new Exception("profiles not found");
        
        return profiles;
    }

    private Profile loadProfile(String name) throws Exception {
        InputStream baseConfigInput = readFile("profile." + name);
        Map<String, Object> config = (Map<String, Object>) yaml.load(baseConfigInput);
        
        Profile profile = new Profile();
        
        profile.setName(name);
        profile.setPath("profile." + name + ".yaml");
        profile.load(config);
        
        return profile;
    }
    
    // -- Util Methods --
    
    private InputStream readFile(String name) throws Exception
    {
        InputStream input = null;
        
        // Load base config file --
        try {
            input = new FileInputStream(new File("data/config/"+ name +".yaml"));
        } catch (FileNotFoundException ex) {
            throw new Exception("file not found");
        }
        
        return input;
    }
}
