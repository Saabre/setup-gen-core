/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.base;

/**
 *
 * @author Lifaen
 */
public class Output {
    
    // -- Internal classes --
    public class Channel {
        public void print(String str)    { System.out.print(str); }
        public void append(String str)   { System.out.print(str); }
        public void println(String str)  { print(str+"\n"); }
    }
    
    public class Info extends Channel 
    {
        @Override
        public void print(String str)  { super.print("  "+ str);  }
    }
    
    public class Operation extends Channel 
    {
        @Override
        public void print(String str)  { super.print("   > "+ str);  }
    }
    
    public class Data extends Channel 
    {
        @Override
        public void print(String str)  { super.print("   $ "+ str);  }
    }
    
    public class Error extends Channel 
    {
        @Override
        public void print(String str)  { super.print("   * "+ str);  }
    }
    
    public class Title extends Channel 
    {
        @Override
        public void println(String str)  { super.print("/* -- "+ str +" -- */\n\n");  }
    }
}
