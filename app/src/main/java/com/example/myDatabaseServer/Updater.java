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
    private static String answer;
    private static int[] v1;
    private static int[] v2;
    private static int maxLength;
    private static String[] version1;
    private static String[] version2;
   
    
  
       
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
            downloadLink = myProperties.getProperty("download_url");
           
            
        } finally {
            connection.disconnect();
        }
        
       } catch(Exception e) {
           JOptionPane.showMessageDialog(null,"Exception: " + e.getMessage());
       }
       
       return downloadLink;
    }
   
   // Function to check if version is older than the current version.
   public int checkVersion() {
       version1 = myCurrentVersion.split("\\.");
       version2 = newVersion.split("\\.");
       maxLength = Math.max(version1.length, version2.length);
     
       
       
       v1 = new int[maxLength];
       v2 = new int[maxLength];
       
       for(int i = 0; i < maxLength; i++) {
           v1[i] = Integer.parseInt(version1[i]);
           v2[i] = Integer.parseInt(version2[i]);
          
       }
       
       for(int i = 0; i < v1.length; i++) {
           if(v1[i] < v2[i]) {
               return 1;
           } else if(v1[i] > v2[i]) {
               return -1;
               
           } 
           
        
       }
      
       return 0;
      
   }
}
     

    
    

