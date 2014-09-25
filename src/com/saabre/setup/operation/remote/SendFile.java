/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.operation.remote;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
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
    public void run() throws Exception 
    {
         // -- Open SFTP channel --
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;
        
        Map<String, Object> config = (Map<String, Object>) this.config;
        String target = (String) config.get("target");
        List<String> sources = (List<String>) config.get("source");

        // -- Open Folder --
        channelSftp.cd(target);
        
        for(String source : sources)
        {
            // -- Send file --
            File f = new File(source);
            channelSftp.put(new FileInputStream(f), f.getName());
        }
        
        channel.disconnect();
    }
    
}
