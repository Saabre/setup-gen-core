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

package com.saabre.setup.operation.remote;

import com.saabre.setup.helper.FileHelper;
import com.saabre.setup.module.remote.RemoteFileGetter;
import com.saabre.setup.module.remote.RemoteOperation;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class GetFile extends RemoteOperation
{
    // -- Attributes --
    
    private String target;
    private List<String> sources;
    
    // -- Methods --
    
    @Override
    public String getTitle() throws Exception {
        return "" ;
    }

    @Override
    public void loadConfig() throws Exception {
        Map<String, Object> config = (Map<String, Object>) this.config;
        target = (String) config.get("target");
        sources = (List<String>) config.get("source");
    }

    @Override
    public void run() throws Exception 
    {
        // Get files --
        RemoteFileGetter getter = new RemoteFileGetter();
        
        getter.addListener(new RemoteFileGetter.Listener() {

            @Override
            public void onFileGot(File file) {
                if(listener != null) listener.onFileGot(file);
            }
            
        });
        getter.connect(session);
        
        if(target.matches("data://.*"))
            target = target.replaceFirst("^data://", FileHelper.getRootFolder());
        else if(target.matches("log://.*"))
            target = target.replaceFirst("^log://", FileHelper.getAnalyisOutputFolder());
        
        for(String source : sources)
        {
            Path remotePath = Paths.get(source);
            getter.get(remotePath, target + "/" + remotePath.getFileName());
        }
        
        getter.disconnect();
    }
    
    // -- Listener --
    
    private Listener listener;
    public void setListener(Listener l) { this.listener = l; }
    
    public static interface Listener 
    {   
        public void onFileGot(File file);
    }
    
}
