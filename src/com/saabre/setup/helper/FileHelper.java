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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.Yaml;

public class FileHelper {
    
    private static String rootFolder = "data/";
    private static String absoluteRootFolder = "data/";
    private static String analysisOutputFolder = "output/log/";
    private static String currentAnalysisFolder = null;
    
    // -- Getters and setters --
    
    public static void setRootFolder(String path) { rootFolder = path; }
    public static void setAbsoluteRootFolder(String path) { absoluteRootFolder = path; }
    
    public static String getRootFolder() { return rootFolder; }
    public static String getScriptOutputFolder() { return rootFolder + "output/script/"; }
    public static String getTemplateFolder() { return rootFolder + "template/"; }
    
    public static String getAnalyisOutputFolder() 
    {
        loadAnalysisOutputFolder();
        return rootFolder + analysisOutputFolder + currentAnalysisFolder ; 
    }
    
    public static String getAbsoluteAnalyisOutputFolder() 
    {
        loadAnalysisOutputFolder();
        return absoluteRootFolder + analysisOutputFolder + currentAnalysisFolder ; 
    }
    
    public static void loadAnalysisOutputFolder()
    {
        if(currentAnalysisFolder == null)
        {
            String name = NameHelper.getFileDate();
            File folder = new File(rootFolder + analysisOutputFolder + name);
            folder.mkdir();
            currentAnalysisFolder = name + "/";
        }
    }
    
    // -- Methods --
    
    public static Map<String, Object> loadFile(String path) throws Exception
    {
        // Get configuration --
        Yaml yaml = new Yaml();
        InputStream input = readConfigFile(path);
        return (Map<String, Object>) yaml.load(input);
    }
    
    public static InputStream readConfigFile(String name) throws Exception
    {
        InputStream input = null;
        
        // Load base config file --
        input = new FileInputStream(new File(rootFolder +"config/"+ name +".yaml"));
        
        return input;
    }
    
    public static void write(String path, String str) throws Exception 
    {
        write(path, str, "UTF-8");
    }

    public static void write(String path, String str, String encoding) throws Exception {
        PrintWriter writer = new PrintWriter(path, encoding);
        writer.print(str);
        writer.close();
    }
    
    
    /* From http://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java */
    public static String byteCountToReadable(long size, boolean si)
    {
        int unit = si ? 1000 : 1024;
        if (size < unit) return size + " o";
        
        int     exp = (int) (Math.log(size) / Math.log(unit));
        String  pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %so", size / Math.pow(unit, exp), pre);
    }
    
    public static long readableToByteCount(String size)
    {
        size = size.replaceAll(" ", "");
        size = size.replaceAll(",", ".");
        
        Pattern pattern = Pattern.compile("([0-9]+\\.?[0-9]*)([KMGTPE])?(i)?[oO]");
        Matcher matcher = pattern.matcher(size);
        
        if(matcher.find())
        {            
            boolean si = matcher.group(3) == null;
            int unit = si ? 1000 : 1024;
            int exp = ("KMGTPE").indexOf(matcher.group(2)) + 1;
            double nb = Double.parseDouble(matcher.group(1));
            double unitSize = Math.pow(unit, exp);
            double result = (long) (nb * unitSize);
            
            return (long) result;
        }
        
        return 0;
    }
}
