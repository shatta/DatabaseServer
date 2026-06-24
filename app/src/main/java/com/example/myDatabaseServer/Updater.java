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
    private static final String gitURL = "https://www.github.com" + Owner + "/" + Repo + "/" + "releases/latest";
    private static String myCurrentVersion = currentVersion;
   
    
    public String checkUpdate() {
          Properties properties = new Properties();
          try {
            String latestVersion = fetchLatestVersion();
            if (latestVersion != null) {
                System.out.println("Current Version: " + myCurrentVersion);
                System.out.println("Latest Version on GitHub: " + latestVersion);

                if (isNewerVersion(myCurrentVersion, latestVersion)) {
                    System.out.println("An update is available! Please download the new version.");
                } else {
                    System.out.println("Your application is up to date.");
                }
            } else {
                System.out.println("Could not retrieve version information.");
            }
        } catch (Exception e) {
            System.err.println("Error checking for updates: " + e.getMessage());
        }
       return "Hello";     
    }
 

    
    
}
