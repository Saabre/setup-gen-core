/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.module.remote;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.saabre.setup.system.base.Module;
import com.saabre.setup.system.base.Operation;
import com.saabre.setup.system.base.Profile;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Lifaen
 */
public class RemoteModule extends Module
{    
    // -- Attributes --
    
    private String      host;
    private int         port;
    private String      user;
    private String      pass;
    private String      path;
    
    private Session     session;
    private Channel     channel;
    private ChannelSftp channelSftp;    
    
    // -- Methods --

    @Override
    public void preRun() throws Exception 
    {
        // Check if config is not null --
        if(config == null)
            throw new Exception("no remote config found");
        
        // Load the configuration --
        Map<String, Object> remote = (Map<String, Object>) config.get("remote");
        
        host = (String) remote.get("host");
        port = (int)    remote.get("port");
        user = (String) remote.get("user");
        pass = (String) remote.get("pass");
        path = (String) remote.get("path");
        
        if(listener != null) listener.onTryingConnection();
        
        // -- Initialisation --
        JSch jsch = new JSch();
        session = jsch.getSession(user,host,port);
        session.setPassword(pass);

        // -- Configuration --
        Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        // -- Open SSH session --
        session.connect();
        if(listener != null) listener.onConnected();
        
    }
    
    @Override
    public void run() throws Exception
    {        
        if(listener != null) listener.onProfileStart(profile);
        List<RemoteOperation> operationList = profile.getRemoteOperationList();
        
        for(RemoteOperation operation : operationList)
        {            
            if(listener != null) listener.onOperationStart(operation);
            operation.setSession(session);
            operation.activate();
            
            if(listener != null) listener.onOperationEnd();
        }
        
        if(listener != null) listener.onProfileEnd();
    }

    @Override
    public void postRun() throws Exception 
    {
        session.disconnect();
        if(listener != null) listener.onDisconnected();
    }
    
    // -- Getters and setters --

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getPath() {
        return path;
    }

    public Session getSession() {
        return session;
    }

    public Channel getChannel() {
        return channel;
    }

    public ChannelSftp getChannelSftp() {
        return channelSftp;
    }
    
    // -- Listener --
    
    private Listener listener;
    public void setListener(Listener l) { this.listener = l; }
    
    public static interface Listener 
    {
        public void onTryingConnection();
        public void onConnected();
        public void onDisconnected();
        
        public void onProfileStart(Profile profile);
        public void onProfileEnd();

        public void onOperationStart(RemoteOperation operation);
        public void onOperationEnd();

    }
}
