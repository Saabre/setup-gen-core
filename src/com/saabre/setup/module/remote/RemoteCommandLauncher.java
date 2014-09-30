/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.module.remote;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lifaen
 */
public class RemoteCommandLauncher {
    
    // -- Attributes --
    
    private ChannelExec channelExec;
    private InputStream in;
    private List<Listener> listenerList = new LinkedList<>();
    
    // -- Methods --
    
    public void connect(Session session) throws Exception
    {
        // Initialisation --
        channelExec = (ChannelExec) session.openChannel("exec");
        in = channelExec.getInputStream();
    }
    
    public void run(String command) throws Exception
    {
        if(channelExec == null)
            throw new Exception("RemoteCommandLauncher not connected");
        
        channelExec.setCommand(command);
        channelExec.connect();
    }
    
    public void waitForOutput() throws Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        while ((line = reader.readLine()) != null)
        {
            for(Listener l : listenerList) l.onNewLine(line);
        }
        
        disconnect();
    }
    
    public int disconnect()
    {
        int status = channelExec.getExitStatus();
        for(Listener l : listenerList) l.onExit(status);
        channelExec.disconnect();
        return status;
    }
    
    // -- Events --
    
    public void addListener(Listener listener)
    {
        listenerList.add(listener);
    }
    public interface Listener
    {
        public void onNewLine(String str);

        public void onExit(int exitStatus);
    }
}
