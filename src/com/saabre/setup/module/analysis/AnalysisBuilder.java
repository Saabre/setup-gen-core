/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saabre.setup.module.analysis;

import org.rosuda.JRI.Rengine;

/**
 *
 * @author Lifaen
 */
public class AnalysisBuilder {
    
    // -- Attributes --
    
    private Rengine re;
    
    // -- Methods --
    
    public void load() throws Exception
    {
        re = new Rengine (new String [] {"--vanilla"}, false, null);
        if (!re.waitForR())
            throw new Exception("Cannot load R");
    }
    
    public void cd(String path)
    {
        re.eval("setwd(\""+ path +"\")");
    }
    
    public void render(String file)
    {
        re.eval("rmarkdown::render(\""+ file +".Rmd\")");
    }
    
    public void close()
    {
        re.end();
    }
    
}
