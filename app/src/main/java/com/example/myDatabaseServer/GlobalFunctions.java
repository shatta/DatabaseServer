/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.myDatabaseServer;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.net.*;
import java.nio.file.*;
import javax.swing.*;
import java.security.*;
import org.springframework.context.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 *
 * @author Jermaine H. Ramsay
 */
public class GlobalFunctions extends GlobalVariables {
    
    // Function to check for Properties file.
    public static void checkPropFile() {
      try {
       if(Files.isDirectory(directoryPath)) {
        if(!Files.exists(filePath)) {
            JOptionPane.showMessageDialog(null,"Config file will be created.");
            fileExists = false;
        } else {
            fileExists = true;
        }
      } else {
         Files.createDirectories(directoryPath);
      }
     } catch (IOException e) {
         JOptionPane.showMessageDialog(null,"Exception: " + e.getMessage());
     }
    } // end of checkFile class.
    
    // Function to login to database to access tables.
    public static void loginDatabase() {
        
        try {
          databaseURL = databaseURLHeader + databaseHostName + ":" + databasePort + "/" + databaseName + secureTransport;
          databaseConn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
          if(databaseConn == null) {
              JOptionPane.showMessageDialog(null,"Database Login was Unsuccessful!  Please confirm properties in Configuration!");
          } else {
              myPrepStmt = databaseConn.prepareStatement(loginQuery);
              myPrepStmt.setString(1, UserName);
              myResultSet = myPrepStmt.executeQuery();
              
              if(myResultSet.next()) {
                  loginUsername = myResultSet.getString("Username");
                  loginPassword = myResultSet.getString("Password_Hash");
                  accountActivity = myResultSet.getInt("Active");
                  userFirstName = myResultSet.getString("Firstname");
                  userAccountLevel = myResultSet.getString("AccessType");
                 
                 
              } // End of if function to obtain required login information. 
              
              if(UserName.equalsIgnoreCase(loginUsername)) {
                  if(Password.equals(loginPassword)) {
                      if(accountActivity == 1) {
                        JOptionPane.showMessageDialog(null,"Login Successful! \nWelcome to Ramil Systems " + userFirstName);
                        loginAllowed = true;
                          if(userAccountLevel.equals("Administrator") == true) {
                              isAdministrator = true;
                          }
                      } else if(accountActivity == 0) {
                        JOptionPane.showMessageDialog(null,"Account is currently inactive.  Please reach out to the Administrator! Thank you.");
                      } else { 
                        JOptionPane.showMessageDialog(null,"Please try again to login or reach out to System Administrator for more information. Thank you.");
                      }
                  } else {
                      JOptionPane.showMessageDialog(null,"Password does not match! Please try again. Thank you.");
                  }
              } else {
                  JOptionPane.showMessageDialog(null,"Username not found!  Please try again.  Thank you.");
              }    
              
              databaseConn.close();
          }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    } // end of loginDatabase function.
    
    
    // Function to write all the information to config file.
    public static void createPropFile() {
        // Processing.
        programProperties = new Properties();
        programProperties.put(dbURLKey, databaseHostName);
        programProperties.put(dbPortKey, Integer.toString(databasePort));
        programProperties.put(dbUsernameKey, databaseUsername);
        programProperties.put(dbPasswordKey, databasePassword);
        programProperties.put(dbDatabaseNameKey, databaseName);
        programProperties.put("app.version", appVersion);
        
        try {
            fileStream = Files.newBufferedWriter(filePath);
            programProperties.store(fileStream, "Application Configuration");
            JOptionPane.showMessageDialog(null,"Config File saved!");
            
            // Clearing variables.
            databaseHostName = "";
            databasePort=0;
            databaseUsername="";
            hashedPassword="";
            databasePassword=""; 
            
            
        } catch (Exception io) {
            JOptionPane.showMessageDialog(null,io.getMessage());
        }
    } // End of function to create Properties file. 
   
   
    
    // Function to secure logout.
    public static void logout() {
        loginUsername = "";
        loginPassword = "";
        userFirstName = "";
        accountActivity = 2;
        userAccountLevel = ""; 
        isAdministrator = false;
        loginAllowed = false;
    }
    
    // Clinical calculation functions.
    
     // Function to calculate corrected calcium
    public static void correctedCalcium() {
        correctedCalcium = serumCalcium + (0.8 * (normAlbumin - serumAlbumin));
    }  // end of function to calculate corrected Calcium
    
    // Function to calculate ideal bodyweight
    public static void calculateIdealBW() {
      if(patientHeight >= 60) {
        if(isMale == true) {
            idealBW = maleIBWFactor + (IBWHeightconstant * (patientHeight - 60));
            idealBW = Math.round(idealBW);
            idealBWString = String.format("%.1f", idealBW);
        } else if(isMale == false) {
            idealBW = femaleIBWFactor + (IBWHeightconstant * (patientHeight - 60));
            idealBW = Math.round(idealBW);
            idealBWString = String.format("%.1f", idealBW);
        } // end of if-else statement
      } else{
         JOptionPane.showMessageDialog(null, "Unable to calculate IBW due to patient being under the height requirement.  Please reach out to the System Admi and Clinical Specialist.  Thank you.");
      } // end of if-else for patient's height requirement.
    }  // End of Function to calculate IBW.
    
    // Function to calculate adjusted bodyweight.
    public static void calculateAdjustedBW() {
        calculateIdealBW();
        patientAdjustedBW = idealBW + (0.4 * (patientWeight - idealBW));
        patientAdjustedBW = Math.round(patientAdjustedBW);
        patientAdjustedBWString = String.format("%.1f", patientAdjustedBW);
    } // end of function to calcuate adjusted body weight.
    
    // Function to calculate Creatinine Clearance
    public static void calculateCrCl() {
        calculateAdjustedBW();
        percentOverUnder = (patientWeight / idealBW)*100;
        
       if(isMale == true) {
        if(patientWeight < idealBW) {
            patientCrCl = ((140 - patientAge) * patientWeight) / (72 * patientSCr);
        } else if(patientWeight > idealBW && percentOverUnder < 130) {
           patientCrCl = ((140 - patientAge) * idealBW) / (72 * patientSCr);
        } else if(patientWeight > idealBW && percentOverUnder > 130) {
           patientCrCl = ((140 - patientAge) * patientAdjustedBW) / (72 * patientSCr);
        }
       } else if(isMale == false) {
         if(patientWeight < idealBW) {
            patientCrCl = (((140 - patientAge) * patientWeight) / (72 * patientSCr)) * 0.85;
        } else if(patientWeight > idealBW && percentOverUnder < 130) {
           patientCrCl = (((140 - patientAge) * idealBW) / (72 * patientSCr)) * 0.85;
        } else if(patientWeight > idealBW && percentOverUnder > 130) {
           patientCrCl = (((140 - patientAge) * patientAdjustedBW) / (72 * patientSCr)) * 0.85;
        }  
       } // End of if statement to calculate patientCrCl.
       
       patientCrCl = Math.round(patientCrCl);
       patientCrClString = String.format("%.2f",patientCrCl);
    }
 
    
    // Administrative Functions.
  public static void DeleteInfo() {
            
     // Processing to obtain user info
     try {     
           databaseURL = databaseURLHeader + databaseHostName + ":" + databasePort + "/" + databaseName + secureTransport;
           databaseConn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
          if(databaseConn == null) {
            JOptionPane.showMessageDialog(null,"Unable to connect to Database!");
          } else {
             
              // checking if username is available.
              myPrepStmt = databaseConn.prepareStatement(deleteQuery);
              myPrepStmt.setString(1, UserName);
             
              
              int rowsDeleted = myPrepStmt.executeUpdate();
              if(rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null,"User succesfully Deleted!");
              } else {
                JOptionPane.showMessageDialog(null,"User not found!");
              }
      
         } // End of if else statement to pull information using either firstname or username.  
      } catch (SQLException exception) {
         JOptionPane.showMessageDialog(null,"Exception is: " + exception.getMessage());
      } // End of try-catch
      
  } // End of function to delete UserInfo  


 
     
}
