/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.operation.remote;

import com.saabre.setup.module.remote.RemoteCommandLauncher;
import com.saabre.setup.module.remote.RemoteOperation;

/**
 *
 * @author Lifaen
 */
public class RunCommand extends RemoteOperation {

    // -- Attributes --
    protected String command;

    // -- Overrided Methods --
    
    @Override
    public String getTitle() throws Exception {
        return config.toString();
    }

    @Override
    public void loadConfig() throws Exception 
    {
        command = config.toString();
    }
    
    @Override
    public void run() throws Exception 
    {
        RemoteCommandLauncher launcher = new RemoteCommandLauncher();
        
        launcher.addListener(new RemoteCommandLauncher.Listener() {

            @Override
            public void onNewLine(String str) {
                if(listener != null) listener.onOutputNewLine(str);
            }

            @Override
            public void onExit(int exitStatus) {
                if(listener != null) listener.onExit(exitStatus);
            }
        });
        
        launcher.connect(session);
        launcher.run(command);
        launcher.waitForOutput();
    }
    
    // -- Listener --
    
    private Listener listener;
    public void setListener(Listener l) { this.listener = l; }

    public static interface Listener 
    {   
        public void onOutputNewLine(String line);
        public void onExit(int exitStatus);
    }
}
