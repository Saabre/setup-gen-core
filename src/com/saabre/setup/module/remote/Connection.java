/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.module.remote;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author Lifaen
 */
public class Connection {
    
    public void accessServer()
    {
        
        String remoteHost = "108.61.208.56";
        int    remotePort = 22;
        String remoteUser = "root";
        String remotePass = "kszqCx1AJ5z139qJeT";
        String remotePath = "/opt/script";
 
        Session     session     = null;
        Channel     channel     = null;
        ChannelSftp channelSftp = null;
        
        System.out.println("Trying to connect to " + remoteHost + ":" + remotePort + "...");       
 
        try 
        {
            // -- Initialisation --
            JSch jsch = new JSch();
            session = jsch.getSession(remoteUser,remoteHost,remotePort);
            session.setPassword(remotePass);
            
            // -- Configuration --
            Properties config = new java.util.Properties();
            
            config.put("StrictHostKeyChecking", "no");
            
            session.setConfig(config);
            
            // -- Open SSH session --
            session.connect();
            
            /* Input
            InputStream in = new PipedInputStream();
            PipedOutputStream pin = new PipedOutputStream((PipedInputStream) in);
            channel.setInputStream(in);
            channel.connect();
            
            pin.write(myScript.getBytes());

            // Output
            PipedInputStream pout = new PipedInputStream((PipedOutputStream) out);
          
            BufferedReader consoleOutput = new BufferedReader(new InputStreamReader(pout));
            consoleOutput.readLine();
            //*/
            
            // -- Open SFTP channel --
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            
            // -- Send file --
            channelSftp.cd(remotePath);
            File f = new File("data/output/fullSetup.sh");
            channelSftp.put(new FileInputStream(f), f.getName());
            
            System.out.println("File successfully sent !");
            
            channel.disconnect();
            session.disconnect();
        } 
        catch(Exception ex)
        {   
            ex.printStackTrace();
        }
        
        System.out.println("Connection finished !");  
    }
}
