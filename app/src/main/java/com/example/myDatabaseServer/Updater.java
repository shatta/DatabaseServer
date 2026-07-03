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
import java.net.HttpURLConnection;
import java.util.Properties;
import javax.swing.*;
import java.net.http.*;
import java.net.URLConnection;
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
    private static final String gitURL = "https://raw.githubusercontent.com/shatta/DatabaseServer/refs/heads/master/update.properties";
    private static String myCurrentVersion = currentVersion;
    private static String newVersion;
    private static Properties myProperties;
    private static URL myURL;
    private static HttpURLConnection connection;
    private static int respondCode;
    private static String downloadLink;
   
    
  
       
      // Functions.
      // Function get appVersion.
   public static String getVersion() {
       try {
        myURL = new URI(gitURL).toURL();
        connection = (HttpURLConnection)myURL.openConnection();
        connection.setRequestMethod("GET");
        
        // Optional set timeout to prevent thread from hanging.
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        respondCode = connection.getResponseCode();
        if(respondCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP Error Code: " + respondCode);
        } // end of if statement for respond code.
        
        myProperties = new Properties();
        try (InputStream myInputStream = connection.getInputStream()) {
            myProperties.load(myInputStream);
            newVersion = myProperties.getProperty("current.version");
           
            
        } finally {
            connection.disconnect();
        }
        
       } catch(Exception e) {
           JOptionPane.showMessageDialog(null,"Exception: " + e.getMessage());
       }
       
       return newVersion;
    }


   public static String getDownloadLink() {
    try {
        myURL = new URI(gitURL).toURL();
        connection = (HttpURLConnection)myURL.openConnection();
        connection.setRequestMethod("GET");
        
        // Optional set timeout to prevent thread from hanging.
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        
        respondCode = connection.getResponseCode();
        if(respondCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP Error Code: " + respondCode);
        } // end of if statement for respond code.
        
        myProperties = new Properties();
        try (InputStream myInputStream = connection.getInputStream()) {
            myProperties.load(myInputStream);
            downloadLink = myProperties.getProperty("current.version");
           
            
        } finally {
            connection.disconnect();
        }
        
       } catch(Exception e) {
           JOptionPane.showMessageDialog(null,"Exception: " + e.getMessage());
       }
       
       return downloadLink;
    }
}
     

    
    

