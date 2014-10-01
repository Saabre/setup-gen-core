/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author Lifaen
 */
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
