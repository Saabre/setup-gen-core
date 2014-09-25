/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.base;

import java.util.List;

/**
 *
 * @author Lifaen
 */
public class Controller {
    
    // -- Attributes --
    
    protected ControllerOutput output = new ControllerOutput();
    
    // -- Output --
    
    public static class ControllerOutput extends Output
    {
        public Channel op = new Info();
        public Channel subOp = new Operation();
        public Channel title = new Title();
    }
    
    // -- Profile methods --
}
