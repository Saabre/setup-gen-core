/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.saabre.setup.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Lifaen
 */
public class FileHelper {
    
    private static String rootFolder = "data/";
    
    // -- Getters and setters --
    
    public static void setRootFolder(String path)
    {
        rootFolder = path;
    }
    
    public static String getRootFolder() { return rootFolder; }
    public static String getScriptOutputFolder() { return rootFolder + "output/script/"; }
    public static String getTemplateFolder() { return rootFolder + "template/"; }
    
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
