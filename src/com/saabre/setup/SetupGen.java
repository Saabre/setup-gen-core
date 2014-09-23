/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup;

import com.saabre.setup.system.Controller;
import com.saabre.setup.system.remote.Connection;

/**
 *
 * @author Lifaen
 */
public class SetupGen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println(" -- Server Setup Utility --\n");
        
        Controller controller = new Controller();
        
        try {
            controller.run();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
        Connection connection = new Connection();
        connection.accessServer();
    }
    
}
