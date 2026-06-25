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
   
    
    public static void checkUpdate() {
       myProperties = new Properties();
       try {
          myURL = URI.create(gitURL).toURL();
          try(InputStream myInputStream = myURL.openStream()) {
              // Load the properties
              myProperties.load(myInputStream);
              
           }
          } catch (Exception e) {
              JOptionPane.showMessageDialog(null,"Exception is: " + e.getMessage());
          }
       }
             
    }
 

    
    

