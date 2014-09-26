/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.helper;

import com.x5.template.Chunk;
import com.x5.template.Theme;

/**
 *
 * @author Lifaen
 */
public class TemplateHelper {
    
     public static Chunk getScriptChunk(String file, String name)
    {
        return getChunk("script", file, name);
    }
    
    public static Chunk getSystemChunk(String file, String name)
    {
        return getChunk("system", file, name);
    }
    
    public static Chunk getChunk(String path, String file, String name)
    {
        Theme chunkFactory = new Theme(FileHelper.getTemplateFolder(), path);
        chunkFactory.setDefaultFileExtension("sh");
        
        return chunkFactory.makeChunk(file + "#"+ name);
    }
    
}
