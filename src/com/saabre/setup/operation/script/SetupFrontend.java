/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.operation.script;

import com.saabre.setup.system.script.Operation;
import com.x5.template.Chunk;

/**
 *
 * @author Lifaen
 */
public class SetupFrontend extends Operation {

    @Override
    public void run() 
    {
        Chunk html = getMainChunk();

        builder.append(html.toString());
    }
}
