/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.module.remote;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lifaen
 */
public class RemoteFileSender {
    
    // -- Attributes --
    
    private Channel channel;
    private ChannelSftp channelSftp;
    private List<Listener> listenerList = new LinkedList<>();
    
    // -- Methods --
    
    public void connect(Session session) throws Exception
    {
         // -- Open SFTP channel --
        channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp) channel;
    }
    
    public void cd(String path) throws Exception
    {
       channelSftp.cd(path);
    }
    
    public void send(File f) throws Exception
    {
        if(f.isDirectory())
            sendFolder(f);
        else
            sendFile(f);
    }
    
    public void sendFile(File f)throws Exception
    {
        channelSftp.put(new FileInputStream(f), f.getName());
        
        for(Listener l : listenerList) l.onFileSent(f);
    }
    
    private void sendFolder(File file)throws Exception
    {
        File[] children = file.listFiles();
        if (children == null) 
            return; 
        
        for (File child : children) 
        {
            if(child.isFile())
            {
                sendFile(child);
            }
        }
    }
    
    public void disconnect()
    {
        channel.disconnect();
    }
    
    // -- Events --
    
    public void addListener(Listener listener)
    {
        listenerList.add(listener);
    }
    public interface Listener
    {
        public void onFileSent(File file);
    }
}
