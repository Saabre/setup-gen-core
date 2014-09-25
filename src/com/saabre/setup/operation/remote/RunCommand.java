/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.operation.remote;

import com.jcraft.jsch.ChannelExec;
import com.saabre.setup.system.module.remote.RemoteOperation;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Lifaen
 */
public class RunCommand extends RemoteOperation {

    @Override
    public void run() throws Exception 
    {
        // Initialisation --
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        InputStream in = channelExec.getInputStream();
        
        channelExec.setCommand(config.toString());
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        int index = 0;

        while ((line = reader.readLine()) != null)
        {
            System.out.println(" $ " + line);
        }

        int exitStatus = channelExec.getExitStatus();
        channelExec.disconnect();
        session.disconnect();
        if(exitStatus < 0){
            System.out.println(" $ Done, but exit status not set!");
        }
        else if(exitStatus > 0){
            System.out.println(" $ Done, but with error!");
        }
        else{
            System.out.println(" $ Done!");
        }
    }
    
}
