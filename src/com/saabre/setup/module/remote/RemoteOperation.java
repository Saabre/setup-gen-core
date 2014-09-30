/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.module.remote;

import com.jcraft.jsch.Session;
import com.saabre.setup.system.base.Operation;

/**
 *
 * @author Lifaen
 */
public abstract class RemoteOperation extends Operation {
    
    // -- Attribute --
    
    protected Session session;
    
    // -- Methods --
    
    public abstract String getTitle() throws Exception;

    public void setSession(Session session) {
        this.session = session;
    }    
}
