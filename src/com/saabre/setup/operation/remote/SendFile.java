/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.operation.remote;

import com.saabre.setup.helper.FileHelper;
import com.saabre.setup.module.remote.RemoteFileSender;
import com.saabre.setup.module.remote.RemoteOperation;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lifaen
 */
public class SendFile extends RemoteOperation {
    
    // -- Attributes --
    
    protected String target;
    protected List<String> sources;
    
    // -- Overrided Methods --
      
    @Override
    public String getTitle() throws Exception {
        return "";
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
        // Send files --
        RemoteFileSender sender = new RemoteFileSender();
        
        sender.addListener(new RemoteFileSender.Listener() {

            @Override
            public void onFileSent(File file) {
                if(listener != null) listener.onFileSent(file);
            }
        });
        sender.connect(session);
        sender.cd(target);
        
        for(String source : sources)
        {
            if(source.matches("data://.*"))
                source = source.replaceFirst("^data://", FileHelper.getRootFolder());
                
            sender.send(new File(source));
        }
        
        sender.disconnect();
    }
    
    // -- Listener --
    
    private Listener listener;
    public void setListener(Listener l) { this.listener = l; }
    
    public static interface Listener 
    {   
        public void onFileSent(File file);
    }
    
}
