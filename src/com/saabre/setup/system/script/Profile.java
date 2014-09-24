/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.script;

import com.saabre.setup.helper.ClassHelper;
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
    private final List<Operation> operationList = new ArrayList<>();
    private String name;

    // -- Methods --
    
    public void add(Operation operation) {
        operationList.add(operation);
    }

    // -- Getters and setters --
    
    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public List<Operation> getOperationList() {
        return operationList;
    }
    
    
}
