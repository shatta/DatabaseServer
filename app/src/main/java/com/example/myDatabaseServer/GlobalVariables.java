package com.example.myDatabaseServer;


import java.net.*;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;
import java.sql.*;
import java.nio.file.*;
import java.io.*;
import java.lang.Runnable;
import org.springframework.context.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jermaine H. Ramsay
 */


public class GlobalVariables extends JFrame {
    static DatabaseLogin myLoginForm = new DatabaseLogin();
    static AccountActivity myActivity;
    static AddUser myAddUser;
    static DeleteUser myDeleteUser;
    static ModifyUserInfo myModifyUser;
    static DatabaseHostConfig myConfigForm = new DatabaseHostConfig();
    static PharmacySuiteMainMenu myPharmSuite;
    static AdministrativeForm myAdministrator;
    static CalciumCorrectionForm myCalciumCorrection = new CalciumCorrectionForm();
    static IdealBodyWeight myIdealBodyWeight = new IdealBodyWeight();
    static ProgramUpdates myUpdates;
    static CrClCalculator renalCalculator = new CrClCalculator();
    static AdjustedBodyWeight myAdjustedBWCalc = new AdjustedBodyWeight();
    static AccessModify myModifyAccess = new AccessModify();
    static ChangePassword myChangePassword;
    static String UserName;
    static String confirmPassword;
    static String Password;
    static String loginUsername;
    static String loginPassword;
    static String databaseUsername;
    static String userFirstName; 
    static String userLastName;
    static String userAccountLevel;
    static String userBirthday;
    static String userEmailAddress;
    static String newAccountLevel;
    static int rowsInserted;
    static int userAge;
    static final String databaseURLHeader = "jdbc:mysql://";
    static Connection databaseConn;
    static String databaseURL;
    static String databasePassword;
    static String serverName;
    static String databaseHostName;
    static String databaseName;
    static String hashedPassword;
    static String newPassword;
    static final String deleteQuery = "DELETE FROM currentUsers WHERE username = ?";
    static final String loginQuery = "SELECT * FROM currentUsers WHERE username = ?";
    static final String checkQuery = "SELECT * FROM currentUsers WHERE username = ?";
    static final String modifyPassQuery = "UPDATE currentUsers SET Password_Hash = ? WHERE username = ?";
    static final String addQuery = "INSERT INTO currentUsers (Firstname, Lastname, Age, Birthday, EmailAddress, Username, Password_Hash, Active, AccessType) VALUES (?,?,?,?,?,?,?,?,?)";
    static final String modifyQuery = "UPDATE currentUsers SET Firstname = ?, Lastname = ?, Age = ?, Birthday = ?, EmailAddress = ? WHERE username = ?";
    static final String modifyAccessQuery = "UPDATE currentUsers SET AccessType = ? WHERE username = ?";
    static final String modifyActivityQuery = "UPDATE currentUsers SET Active = ? WHERE username = ?";
    static final String myFilePath = "src/main/resources/";
    static final String fileName = "config.properties";
    static final String dbDatabaseNameKey = "db.DatabaseName";
    static final String dbURLKey = "db.URL";
    static final String dbPortKey = "db.Port";
    static final String dbUsernameKey = "db.Username";
    static final String dbPasswordKey = "db.Password";
    static final String dbTableKey = "db.TableName";
    static String changeUsername;
    static String modifyUserAccountType;
    static final String getOS = System.getProperty("os.name").toLowerCase();
    static final double normAlbumin = 4.0;
    static double correctedCalcium;
    static String correctedCalciumString;
    static String appVersion = GlobalVariables.class.getPackage().getImplementationVersion();
    static double serumCalcium;
    static double serumAlbumin;
    static double idealBW;
    static String idealBWString;
    static String patientCrClString;
    static final double maleIBWFactor = 50;
    static final double femaleIBWFactor = 45.5;
    static final double IBWHeightconstant = 2.3;
    static double patientCrCl;
    static double patientSCr;
    static double patientAdjustedBW;
    static double patientWeight;
    static double percentOverUnder;
    static String patientAdjustedBWString;
    static boolean isMale;
    static double patientHeight;
    static final double cmConversion = 2.54;
    static ServerSocket myServerSocket;
    static Socket myClientSocket;
    static int serverPort;
    static int databasePort; 
    static int accountActivity;
    static int newAccountActivity;
    static int patientAge;
    static Path myConfigPath;
    static PreparedStatement myPrepStmt;
    static ResultSet myResultSet;
    static Properties programProperties;
    static final Path filePath = Path.of(myFilePath + fileName);
    static final Path directoryPath = Path.of(myFilePath);
    static Writer fileStream;
    static InputStream fileInputStream;
    static boolean fileExists;
    static boolean loginAllowed = false;
    static boolean isAdministrator = false;
    static final String secureTransport = "?useSSL=true&enabledTLSProtocol=TLSv1.2";
    static int menuOption;
    static String searchUsername;
    static String searchFirstname;
    static String DatabaseuserFirstName;
    static String DatabaseuserLastName;
    static String DatabaseuserBirthday;
    static int DatabaseuserAge;
    static String DatabaseuserEmailAddress;
    static String DatabaseloginUsername;
    static CountDownLatch latch = new CountDownLatch(5);
    static String currentVersion;
    
    //  Declaring and defining Threads.
      
   static final Thread getUser2 = new Thread(new Runnable() {
       @Override
       public void run() {
        latch.countDown();
        try {
         databaseURL = databaseURLHeader + databaseHostName + ":" + databasePort + "/" + databaseName + secureTransport;
         databaseConn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
         if(databaseConn == null) {
           JOptionPane.showMessageDialog(null,"Unable to connect to Database!");
         } else {
             // Checking for user.
             myPrepStmt = databaseConn.prepareStatement(checkQuery);
             myPrepStmt.setString(1, UserName);
             myResultSet = myPrepStmt.executeQuery();
             
             if(myResultSet.next() == true) {
                 changeUsername = myResultSet.getString("Username");
    
             }
         } // End of If-else statement to connect to database.  
      } catch (SQLException e) {
        JOptionPane.showMessageDialog(null,"Excepton is: " + e.getMessage());
      }
     } // End of search for user function.
   });    
  
    static final Thread readConfigThread = new Thread(new Runnable() {
            @Override
            public void run() {
                readPropFile();
            }
        }); // End of readconfig thread.;
    
    static final Thread startSuite = new Thread(new Runnable() {
        @Override
        public void run() {
            myPharmSuite.setVisible(true);
        }
      });  // End of declaration of startSuite Thread. 
  
   static final Thread createUser = new Thread(new Runnable() {
        @Override
        public void run() {
             readPropFile();
             
             // Processing to add user
        try {     
           databaseURL = databaseURLHeader + databaseHostName + ":" + databasePort + "/" + databaseName + secureTransport;
           databaseConn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
          if(databaseConn == null) {
            JOptionPane.showMessageDialog(null,"Unable to connect to Database!");
          } else {
              // checking if username is available.
              myPrepStmt = databaseConn.prepareStatement(checkQuery);
              myPrepStmt.setString(1, UserName);
              myResultSet = myPrepStmt.executeQuery();
              
              if(myResultSet.next()) {
                JOptionPane.showMessageDialog(null,"Please try again! Username: " + UserName + " has been taken!");
              } else {
                 // Adding user to database.
                  myPrepStmt = databaseConn.prepareStatement(addQuery);
                  myPrepStmt.setString(1,userFirstName);
                  myPrepStmt.setString(2,userLastName);
                  myPrepStmt.setString(3,Integer.toString(userAge));
                  myPrepStmt.setString(4,userBirthday);
                  myPrepStmt.setString(5,userEmailAddress);
                  myPrepStmt.setString(6,UserName);
                  myPrepStmt.setString(7,Password);
                  myPrepStmt.setString(8,"1");
                  myPrepStmt.setString(9,userAccountLevel);
                  rowsInserted = myPrepStmt.executeUpdate();
                  
                  if(rowsInserted > 0) {
                      JOptionPane.showMessageDialog(null,"User has been added!");
                  }
              }
            
          } // end of if-else statement. 
      } catch (SQLException exception) {
         JOptionPane.showMessageDialog(null,"Exception is: " + exception.getMessage());
      } // End of try-catch
        }
    });
   
   static final Thread getUser = new Thread(new Runnable() {
       @Override
       public void run() {
                 
             // Processing to obtain user info
     try {     
           databaseURL = databaseURLHeader + databaseHostName + ":" + databasePort + "/" + databaseName + secureTransport;
           databaseConn = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
          if(databaseConn == null) {
            JOptionPane.showMessageDialog(null,"Unable to connect to Database!");
          } else {
             
              // checking if username is available.
              myPrepStmt = databaseConn.prepareStatement(checkQuery);
              myPrepStmt.setString(1, UserName);
              myResultSet = myPrepStmt.executeQuery();
              
              if(myResultSet.next()) {
                 // Getting user info from database.
                 DatabaseuserFirstName = myResultSet.getString("Firstname");
                 DatabaseuserLastName = myResultSet.getString("Lastname");
                 DatabaseuserBirthday = myResultSet.getString("Birthday");
                 DatabaseuserAge = myResultSet.getInt("Age");
                 DatabaseuserEmailAddress = myResultSet.getString("EmailAddress");
                 DatabaseloginUsername = myResultSet.getString("Username");
              } else {
                 JOptionPane.showMessageDialog(null,"User not found!");
              } 
      
         } // End of if else statement to pull information using either firstname or username.  
      } catch (SQLException exception) {
         JOptionPane.showMessageDialog(null,"Exception is: " + exception.getMessage());
      } // End of try-catch
       }
       
   }); // End of Get User Thread
    
   
   // Functions
     // Function to read all the information from the config file.
    public static void readPropFile() {
      try {
          programProperties = new Properties();
          fileInputStream = Files.newInputStream(filePath);
          programProperties.load(fileInputStream);
          
          // Assigning keys to variables.
          databaseHostName= programProperties.getProperty(dbURLKey);
          databasePort = Integer.parseInt(programProperties.getProperty(dbPortKey));
          databaseUsername = programProperties.getProperty(dbUsernameKey);
          databasePassword = programProperties.getProperty(dbPasswordKey);
          databaseName = programProperties.getProperty(dbDatabaseNameKey);
          appVersion = programProperties.getProperty("app.version");
          
      } catch(IOException io) {
          JOptionPane.showMessageDialog(null,"Exception is: " + io.getMessage());
      } // End of Try Catch        
    } // End of ReadPropFile.

}
