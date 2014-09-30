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
        return getChunk("script", file, name, "sh");
    }
    
    public static Chunk getSystemChunk(String file, String name)
    {
        return getChunk("system", file, name, "sh");
    }
    
    public static Chunk getAnalysisChunk(String file, String name) 
    {
        return getChunk("analysis", file, name, "Rmd");
    }
    
    public static Chunk getChunk(String path, String file, String name, String ext)
    {
        Theme chunkFactory = new Theme(FileHelper.getTemplateFolder(), path);
        chunkFactory.setDefaultFileExtension(ext);
        
        return chunkFactory.makeChunk(file + "#"+ name);
    }
}
