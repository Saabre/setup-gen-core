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

package com.saabre.setup.system.base;

import com.saabre.setup.module.analysis.AnalysisOperation;
import com.saabre.setup.module.remote.RemoteOperation;
import com.saabre.setup.module.script.ScriptOperation;
import java.util.ArrayList;
import java.util.List;

public class Profile {
    
    // -- Attributes --
    
    private String path;
    private final List<ScriptOperation> scriptOperationList = new ArrayList<>();
    private final List<RemoteOperation> remoteOperationList = new ArrayList<>();
    private final List<AnalysisOperation> analysisOperationList = new ArrayList<>();
    private String name;

    // -- Methods --
    
    public void addScript(ScriptOperation operation) {
        scriptOperationList.add(operation);
    }

    public void addRemote(RemoteOperation operation) {
        remoteOperationList.add(operation);
    }
    
    public void addAnalysis(AnalysisOperation operation) 
    {
        analysisOperationList.add(operation);
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
    
    public List<AnalysisOperation> getAnalysisOperationList() {
        return analysisOperationList;
    }
}
