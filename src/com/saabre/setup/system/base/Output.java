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
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // -- Internal classes --
    public class Channel {
        public void print(String str)    { System.out.print(str); }
        public void append(String str)   { System.out.print(str); }
        public void println(String str)  { print(str+"\n"); }
    }
    
    public class Info extends Channel 
    {
        @Override
        public void print(String str)  { super.print(ANSI_RESET + "  "+ str);  }
    }
    
    public class Operation extends Channel 
    {
        @Override
        public void print(String str)  { super.print(ANSI_RESET + "   > "+ str);  }
    }
    
    public class Data extends Channel 
    {
        @Override
        public void print(String str)  { super.print(ANSI_RESET + "   $ "+ str);  }
    }
    
    public class Error extends Channel 
    {
        @Override
        public void print(String str)  { super.print(ANSI_RED + "   * "+ str);  }
    }
    
    public class Title extends Channel 
    {
        @Override
        public void println(String str)  
        { 
            super.print(ANSI_RED + "/* -- "+ str +" -- */\n\n");  
        }
    }
}
