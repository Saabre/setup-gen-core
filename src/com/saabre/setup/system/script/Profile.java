/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.script;

import com.saabre.setup.system.module.remote.RemoteOperation;
import com.saabre.setup.system.module.script.ScriptOperation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lifaen
 */
public class Profile {
    
    // -- Attributes --
    
    private String path;
    private final List<ScriptOperation> scriptOperationList = new ArrayList<>();
    private final List<RemoteOperation> remoteOperationList = new ArrayList<>();
    private String name;

    // -- Methods --
    
    public void addScript(ScriptOperation operation) {
        scriptOperationList.add(operation);
    }

    public void addRemote(RemoteOperation operation) {
        remoteOperationList.add(operation);
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

    public List<ScriptOperation> getScriptOperationList() {
        return scriptOperationList;
    }
    
    public List<RemoteOperation> getRemoteOperationList() {
        return remoteOperationList;
    }
    
    
}
