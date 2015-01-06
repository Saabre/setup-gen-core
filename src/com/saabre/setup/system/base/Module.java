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

import java.util.List;
import java.util.Map;

public abstract class Module extends Controller {
    
    // -- Attributes --
    
    private List<Profile> profileList;
    protected Map<String, Object> config;
    
    protected String name;
    protected Profile profile;
    
    // -- Methods --
    
    public void activate() throws Exception
    {
        preRun();
        
        for(Profile current : profileList)
        {
            profile = current;
            run();
        }
        
        postRun();
    }
    
    protected abstract void preRun() throws Exception;
    protected abstract void run() throws Exception;
    protected abstract void postRun() throws Exception;
    
    // -- Getters and setters --

    public List<Profile> getProfileList() {
        return profileList;
    }
    
    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
