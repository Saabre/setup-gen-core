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
