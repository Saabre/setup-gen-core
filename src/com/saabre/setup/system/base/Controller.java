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
    
    
    // -- Profile methods --
    
    
    // -- Print Methods --
    
    protected void print(Object obj)
    {
        System.out.print(obj);
    }
    
    protected void println(Object obj)
    {
        System.out.println(obj);
    }
    
    protected void printError(Object obj)
    {
        System.err.print(obj);
    }
    
    protected void printlnError(Object obj)
    {
        System.err.println(obj);
    }
    
}
