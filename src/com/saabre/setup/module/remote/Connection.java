/*

The MIT License (MIT)

Copyright (c) 2015 Saabre

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

 */

package com.saabre.setup.module.remote;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

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
