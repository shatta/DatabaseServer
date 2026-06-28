/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.myDatabaseServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URI;
import java.util.Properties;
import javax.swing.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jermaine H. Ramsay
 */
public class Updater extends GlobalVariables {
    // Variables.
    private static final String Owner = "Shatta";
    private static final String Repo = "DatabaseServer";
    private static final String gitURL = "https://github.com/shatta/DatabaseServer/blob/master/app/src/main/resources/config.properties";
    private static String myCurrentVersion = currentVersion;
    private static String newVersion;
    private static Properties myProperties;
    private static URL myURL;
   
    
  
       
      // Functions.
      // Function get appVersion.
    public static String getVersion() {
         Properties properties = new Properties();
        // Load the file from the classpath
        try (InputStream input = GlobalVariables.class.getResourceAsStream("https://github.com/shatta/DatabaseServer/blob/master/app/src/main/resources/config.properties")) {
            
            if (input == null) {
                return "Unknown (properties missing)";
            }
            
            properties.load(input);
            return properties.getProperty("app.version", "Unknown");
            
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Unknown (error loading)";
        }
      }  // End of function to getUpdatedVersion.
    }
 

    
    

