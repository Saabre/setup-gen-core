/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.helper;

import com.saabre.setup.system.module.script.ScriptOperation;
import com.saabre.setup.system.base.Profile;

/**
 *
 * @author Lifaen
 */
public class ClassHelper {
    
    public static Object newInstance(String className) throws Exception
    {
        // -- Load the class --
        ClassLoader classLoader = ClassHelper.class.getClassLoader();
        Class aClass;
        
        try {
            aClass = classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new Exception("class "+ className + " not found");
        }
        
        // -- Load the object --
        return aClass.newInstance();
    }
    
}
