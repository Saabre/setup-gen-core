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
        // Configuration --
        
        Map<String, Object> config = (Map<String, Object>) this.config;
        String target = (String) config.get("target");
        List<String> sources = (List<String>) config.get("source");
        
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
