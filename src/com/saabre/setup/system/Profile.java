/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lifaen
 */
public class Profile {
    
    // -- Attributes --
    
    private String path;
    private List<Operation> operationList = new ArrayList<>();

    // -- Methods --
    
    public void load(Map<String, Object> config) throws Exception {
        
        List<Object> operations = (List<Object>) config.get("operations");
        
        for(Object line : operations)
            operationList.add(loadOperation((Map<String, Object>) line));
    }
    
    private Operation loadOperation(Map<String, Object> config) throws Exception    
    {
        System.out.print("  - "+ config.get("type") +" operation : ");
        
        // -- Load the class --
        ClassLoader classLoader = Profile.class.getClassLoader();
        Class aClass;
        
        try {
            aClass = classLoader.loadClass("com.saabre.setup.operation."+ config.get("type") + "");
        } catch (ClassNotFoundException e) {
            throw new Exception(path + ": class com.saabre.setup.operation."+ config.get("type") + " not found");
        }
        
        // -- Load the object --
        Operation operation = (Operation) aClass.newInstance();
        
        if(config.containsKey("enabled"))
            operation.setEnabled((boolean) config.get("enabled"));
        
        operation.setConfig(config.get("config"));
        operation.setType((String) config.get("type"));
        
        System.out.println("OK !");
        
        return operation;
    }
    
    public void generate() {
        for(Operation op : operationList)
            op.generate();
    }

    // -- Getters and setters --
    
    void setPath(String path) {
        this.path = path;
    }
}
