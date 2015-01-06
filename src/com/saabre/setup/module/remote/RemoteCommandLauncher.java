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

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

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
