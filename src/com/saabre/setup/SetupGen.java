/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup;

import com.saabre.setup.system.base.Controller;
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
        Controller.ControllerOutput output = new Controller.ControllerOutput();
            
        try 
        {
            output.title.println("  [ Server Setup Utility ]  ");
        
            MainController controller = new MainController();
        
            controller.load();
            controller.run();
            
            output.title.println("  [ End ]  ");
        } 
        catch (Exception ex) 
        {
            output.error.println(ex.toString());
            ex.printStackTrace();
        }
        
        
    }
    
}
