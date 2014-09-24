/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.script;

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
    private String name;

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
        
        operation.setConfig(config.get("config"));
        operation.setType((String) config.get("type"));
        
        if(config.containsKey("enabled"))
            operation.setEnabled((boolean) config.get("enabled"));
        else
            operation.setEnabled(true);
        
        System.out.println("OK !");
        return operation;
    }
    
    public void generate() throws Exception {
        ScriptBuilder builder = new ScriptBuilder();
        
        builder.setOperationList(operationList);        
        builder.generate();
        builder.write("data/output/script/" + name + ".sh");
    }

    // -- Getters and setters --
    
    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }
}
