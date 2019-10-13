# Group gr1922 repository

This is currently a messaging application. With the use of REST, it can send messages over the internet if you have a server to spare. 

About the repository: The project is in /project. The project folder has different modules for all the different part of the code. The modules are:
* project_core
* project_fxui
* project_restapi
* project_restserver

## Building with Gradle

Because we are working with multiple modules the settings.gradle in /project is configured to include the other modules. Each of the modules got their own build.gradle because they are their own gradle project, hence we only need to import the dependencies if they are needed in a project. 


This is currently an offline messaging app for users of the same computer. 
This link takes you to some mockups of the app: [Mockups](https://gitlab.stud.idi.ntnu.no/it1901/gr1922/gr1922/tree/master/project/Illustrations)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
Gradle
Java

```

### Installing

Step 1: Clone the repository at: [Gitlab](https://gitlab.stud.idi.ntnu.no/it1901/gr1922/gr1922.git):
		
Step 2: Go to /project in CMD and write the command. 
``` 
gradle run

```
Step 3: Use the pre-registered user to login. Email: eksempel@outlook.com | Password: eksempel


## Running the tests

Step 1: Go to /project in CMD and write the command. For example:
``` 
gradle test

```

## Built With

* [Gradle](https://docs.gradle.org/current/userguide/userguide.html) - Dependency Management

## Project Status

We have completed the main functionality of the service, as described in the user story, and made the application restfull.

Features:
* Sending messages
* Recieving messages in inbox
* Creating a user
* Logging in
* Deleting messages in inbox


## Authors

* **Lukas** - *Gradle and IO  * - [lukasnt](https://gitlab.stud.idi.ntnu.no/lukasnt)
* **Brage** - *Gradle, JUNIT tests* - [bragesc](https://gitlab.stud.idi.ntnu.no/bragesc)
* **Aleksander** - *Domainlogic* - [aleksawk](https://gitlab.stud.idi.ntnu.no/aleksawk)
* **Erlend** - *FXML og App-controller* - [erlenmom](https://gitlab.stud.idi.ntnu.no/erlenmom)

## User Stories  

Nr. 1: "As a user, i want to read messages from other people."


