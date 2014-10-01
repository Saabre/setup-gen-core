/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.module.remote;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lifaen
 */
public class RemoteFileGetter {
    
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
    
    public void get(Path remotePath, String localPath) throws Exception
    {
        channelSftp.cd("/");
        
        for(int i = 0; i < remotePath.getNameCount() - 1; i++)
        {
            channelSftp.cd(remotePath.getName(i).toString());
        }
        
        channelSftp.get(remotePath.getFileName().toString(), localPath);
        
        for(Listener l : listenerList) l.onFileGot(new File(localPath));
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
        public void onFileGot(File file);
    }
}
