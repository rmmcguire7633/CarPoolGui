# CarPoolGui

A Java FX application that allows users to request carpooling services. This program allows for schedule matching to provide efficency for users requesting a ride and users requesting to drive. A new user can schedule a ride at their desired pickup location and time. In order for a user to request to drive, they must submit a background checking by providing their driver ID, vehicle registration, insrance provider and policy number. Users will also be able to rate their experience with other users.


## Getting Started

To get started clone or download code from this repository and place into your 
preferred java IDE or run from the command line.

## Prerequisites

* **Windows 10 or macOs X**
* **Java JDK version 8 or higher**
* **Integrated development environment (IDE)** 
  * ItelliJ IDEA that can be found at https://www.jetbrains.com/
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
2. Unzip file with 7-zip file mangement.
3. Drag and drop src folder into a project folder inside your perfered IDE.
4. Drag and drop the lib folder into a project folder insde your pperfered IDE.
5. Add all depencies to the project (depencies are the jar files located in the lib directory). To do this in IntelliJ, go to file -> Project Structure -> Modules -> Depencies -> click the add sign located at the top right of the table -> JARs or Directories -> navigate to your project folder then the lib directory and add controlsfx.jar, derby.jar, derbyclient.jar, fontawesomefx.jar, jfoenix.jar.
6. Set Run configurations to run from main.MainLogin class.

##### Using clone respository
1. If cloneing respository.
2. Copy link.
3. Inside your IDE go to file -> new -> Project from version cotrol -> git
4. Paste link inside URL
5. All depencies should be added automically but if not add them (see step 5 for using zip file).
6. Set Run configurations to run from main.MainLogin Class.

## Built with
* IntelliJ IDEA

## Version
* Version 1.0
  * Countains the dark css file.
* Version 1.1
  * Schedule scene, main menu scene, newuser scene, login scene. Most buttons have functionality.
* Version 1.2
  * Database created and is working with the login scene. Added validation for the create new user scene.
* Version 1.3
  * Table view in all scenes pull from the database tables and work accordinaly.
* Version 2.0
  * Program is obertional and all functionalty is working.

## Authors
* Ryan McGuire
* James Hood

## Acknowledgments
* Scott Vanselow for helping with the development of code.
* Afsal Villan for contributing the primary and seacondary color along with the css for the table view.
* https://emailregex.com/ for the email regex format.

