/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup;

import com.saabre.setup.system.controller.MainController;

/**
 *
 * @author Lifaen
 */
public class SetupGen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        try 
        {
            System.out.println(" -- Server Setup Utility --\n");
        
            MainController controller = new MainController();
        
            controller.load();
            controller.run();
            
            System.out.println("\n -- End --\n");
        } 
        catch (Exception ex) 
        {
            System.err.println(ex);
        }
        
        
    }
    
}
