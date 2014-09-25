/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Lifaen
 */
public class FileHelper {
    
    public static Map<String, Object> loadFile(String path) throws Exception
    {
        // Get configuration --
        Yaml yaml = new Yaml();
        InputStream input = readConfigFile(path);
        return (Map<String, Object>) yaml.load(input);
    }
    
    public static InputStream readConfigFile(String name) throws Exception
    {
        InputStream input = null;
        
        // Load base config file --
        input = new FileInputStream(new File("data/config/"+ name +".yaml"));
        
        return input;
    }
}
