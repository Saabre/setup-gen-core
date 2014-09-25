/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.operation.remote;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.saabre.setup.system.module.remote.RemoteFileSender;
import com.saabre.setup.system.module.remote.RemoteOperation;
import java.io.File;
import java.io.FileInputStream;
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
    public void loadConfig() throws Exception {
        Map<String, Object> config = (Map<String, Object>) this.config;
        target = (String) config.get("target");
        sources = (List<String>) config.get("source");
    }
    
    @Override
    public void printBefore() {
        super.printBefore();
        output.op.append("\n");
    }
    
    @Override
    public void printAfter() { }
    
    @Override
    public void run() throws Exception 
    {
        // Send files --
        RemoteFileSender sender = new RemoteFileSender();
        
        sender.addListener(new RemoteFileSender.Listener() {

            @Override
            public void onFileSent(File file) {
                output.data.println(file.getName() +" sent");
            }
        });
        sender.connect(session);
        sender.cd(target);
        
        for(String source : sources)
        {
            sender.send(new File(source));
        }
        
        sender.disconnect();
    }
    
}
