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
import com.saabre.setup.system.module.remote.RemoteOperation;
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
            throw new Exception("Error : no remote config found");
        
        // Load the configuration --
        Map<String, Object> remote = (Map<String, Object>) config.get("remote");
        
        host = (String) remote.get("host");
        port = (int)    remote.get("port");
        user = (String) remote.get("user");
        pass = (String) remote.get("pass");
        path = (String) remote.get("path");
        
        println("Trying to connect to " + host + ":" + port + "..."); 
        
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
        println("Connected.\n"); 
        
    }
    
    @Override
    public void run() throws Exception
    {
        List<RemoteOperation> operationList = profile.getRemoteOperationList();
        
        for(RemoteOperation operation : operationList)
        {
            operation.setSession(session);
            operation.activate();
            
        }
    }

    @Override
    public void postRun() throws Exception 
    {
        session.disconnect();
        println("\nDisconnected.");
    }
}
