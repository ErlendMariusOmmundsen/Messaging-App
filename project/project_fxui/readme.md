# UI module

This sub project contains the ui-related files in our project. Among them are the app launcher file, the app controller file, the fxml file, IO files and data access files.

## User interface

The user interface module contains all files that have a direct connection to how the user sees our app and the data shown in the app.
First, the user is taken to a login where the user can either login with an already existing account, or press the
 "Create account" button which will take them to the "Create account" page. From there the user can either return or create an account.
 When the user logs in, their inbox(List of received messages) will appear on the left side and all fields required to send/read 
 messages will appear on the right. 
 
The user interface is made with JavaFX and FXML. The java files are located in project\_fxui/src/main/java/project\_fxui,
while the fxml file are located in project\_fxui/src/resources/project\_fxui. The IO files are in project\_fxui/src/resources/IO.




## Gradle

Our Gradle build uses several plugins. Among them are "application", which is used to create an executable JVM application,
and "org.openjfx.javafxplugin", which simplifies working with JavaFX 11+ in Gradle projects. 

To make sure our code is up to standards we use three code quality analysis tools:
* [jacoco](https://github.com/jacoco/jacoco) with **[jacoco](https://docs.gradle.org/current/userguide/jacoco_plugin.html)**
* [spotbugs](https://spotbugs.github.io) with **[com.github.spotbugs](https://spotbugs.readthedocs.io/en/latest/gradle.html)**
* [checkstyle](https://checkstyle.sourceforge.io) with **[checkstyle](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)**

All three generate HTML reports with pointers to what can be improved. 