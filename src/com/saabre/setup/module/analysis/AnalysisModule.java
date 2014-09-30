/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.module.analysis;

import com.saabre.setup.operation.analysis.BuildReport;
import com.saabre.setup.system.base.Module;
import com.saabre.setup.system.base.Profile;

/**
 *
 * @author Lifaen
 */
public class AnalysisModule extends Module {

    @Override
    protected void preRun() throws Exception 
    {
        
    }

    @Override
    protected void run() throws Exception 
    {
        if(listener != null) listener.onProfileStart(profile);
            
        // Operation list --
        for(AnalysisOperation operation : profile.getAnalysisOperationList())
        {
            operation.activate();
            
            if(operation instanceof BuildReport) listener.onReportBuilt(operation);
        }

        if(listener != null) listener.onProfileEnd();
    }

    @Override
    protected void postRun() throws Exception 
    {
        
    }
    
    // -- Listener --
    
    private Listener listener;
    public void setListener(Listener l) { this.listener = l; }
    
    public static interface Listener 
    {        
        public void onProfileStart(Profile profile);
        public void onProfileEnd();
        
        public void onReportBuilt(AnalysisOperation operation);
    }
    
}
