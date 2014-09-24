/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.system.generator;

import com.x5.template.Chunk;
import com.x5.template.Theme;

/**
 *
 * @author Lifaen
 */
public class TemplateBuilder 
{
    // -- Attributes --
    
    protected Theme chunkFactory;
    private String name;
    private String path;
    
    // -- Methods --
    
    protected void loadChunkFactory(String path, String name)
    {
        this.path = path;
        this.name = name;
        chunkFactory = new Theme("data/template", path);
        chunkFactory.setDefaultFileExtension("sh");
    }

    protected Chunk getMainChunk()
    {
        return chunkFactory.makeChunk(name + "#Main");
    }
    
    protected Chunk getChunk(String chunkName)
    {
        return chunkFactory.makeChunk(name + "#" + chunkName);
    }
    
}
