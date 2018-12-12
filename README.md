# CarPoolGui

A Java FX application that allows users to request carpooling services. This program allows for schedule matching to provide efficiency for users requesting a ride and users requesting to drive. A new user can schedule a ride at their desired pickup location and time. For a user to request to drive, they must submit a background checking by providing their driver ID, vehicle registration, insurance provider and policy number. Users will also be able to rate their experience with other users.
![gcihvrfvts](https://user-images.githubusercontent.com/35510316/49245619-e67d5f00-f3e0-11e8-83a0-e4e5188f5d38.gif)

## JavaDoc

[JavaDoc](https://rmmcguire7633.github.io/CarPoolGui/)

## Components To Connect To Database

This is to connect to a Derby Database(local host)

Connection connection = DriverManager.getConnection("jdbc:derby:YourDirectoryHere", "jdbc.username", "jdbc.password"); 

## GUI Design Principals

This program adheres to GUI design principals by keeping the user in mind. In scenes with multiple text fields, the user can tab through to the next text field in order. Buttons that require a user input are set to default, so the user does not have to click the button after entering information into the text fields. Buttons are named so the user can easily know what they do. There are labels with instructions on how to operate a function. The program does not allow a user to enter invalid information and will alert the user of the invalid information.

## Getting Started

To get started clone or download code from this repository and place into your 
preferred java IDE or run from the command line.

## Prerequisites

* **Windows 10 or higher*
* **Java JDK version 8 or higher**
* **Integrated development environment (IDE)** 
  * IntelliJ IDEA that can be found at https://www.jetbrains.com/
  * or Eclipse that can be found at https://www.eclipse.org/downloads/
* **7-zip** file management that can be found at https://ninite.com/.
* IntelliJ was used to run this program but any IDE should be able to run the program.
* **External Libraries Used**
  * controlsfx (version 8.40.14) found at http://fxexperience.com/controlsfx/
  * derby database found at https://db.apache.org/derby/derby_downloads.html
  * fontawesomefx (version 8.9) found at https://bitbucket.org/Jerady/fontawesomefx/downloads/
  * jfoenix (version 8.0.7) found at https://github.com/jfoenixadmin/JFoenix

## Installing

##### Using zip file
1. If downloading zip file.
2. Unzip file with 7-zip file management.
3. Drag and drop src folder into a project folder inside your preferred IDE.
4. Drag and drop the lib folder into a project folder inside your preferred IDE.
5. Add all dependencies to the project (dependencies are the JAR files located in the lib directory). To do this in IntelliJ, go to file -> Project Structure -> Modules -> Dependencies -> click the add sign located at the top right of the table -> JARs or Directories -> navigate to your project folder then the lib directory and add controlsfx.JAR, derby.JAR, derbyclient.JAR, fontawesomefx.JAR, jfoenix.JAR.
6. Set Run configurations to run from main.MainLogin class.

##### Using clone repository
1. If cloning repository.
2. Copy link.
3. Inside your IDE go to file -> new -> Project from version cotrol -> git
4. Paste link inside URL
5. All dependencies should be added atomically but if not add them (see step 5 for using zip file).
6. Set Run configurations to run from main.MainLogin Class.

## Built with
* IntelliJ IDEA

## Version
* Version 1.0
  * Countians the dark css file.
* Version 1.1
  * Schedule scene, main menu scene, newuser scene, login scene. Most buttons have functionality.
* Version 1.2
  * Database created and is working with the login scene. Added validation for the create new user scene.
* Version 1.3
  * Table view in all scenes pull from the database tables and work accordingly.
* Version 2.0
  * Program is operational and all functionality is working.

## Authors
* Ryan McGuire
* James Hood

## Acknowledgments
* Scott Vanselow for helping with the development of code.
* Afsal Villan for contributing the primary and secondary color along with the css for the table view.
* https://emailregex.com/ for the email regex format.
* Dr. Koufakou for providing guidance and suggestions on how the final product could be.

