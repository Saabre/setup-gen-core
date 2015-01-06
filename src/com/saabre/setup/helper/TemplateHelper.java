/*

The MIT License (MIT)

Copyright (c) 2015 Saabre

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

 */

package com.saabre.setup.helper;

import com.x5.template.Chunk;
import com.x5.template.Theme;

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
