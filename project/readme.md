# Group gr1922 repository

This is currently a messaging application. With the use of REST, it can send messages over the internet if you have a server to spare. 

About the repository: The project is in /project. The project folder has different modules for all the different part of the code. The modules are:
* project_core
* project_fxui
* project_restapi
* project_restserver

This link takes you to some mockups of the app: [Mockups](https://gitlab.stud.idi.ntnu.no/it1901/gr1922/gr1922/tree/master/project/Illustrations)

## Building with Gradle

Because we are working with multiple modules the settings.gradle in /project is configured to include the other modules. Each of the modules got their own build.gradle because they are their own gradle project, hence we only need to import the dependencies if they are needed in a project. 


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
		
Step 2: Go to /project in any Terminal and write the command. 

```
gradle run
```

Step 3: Use the pre-registered user to login. Email: eksempel@outlook.com | Password: eksempel

Other accounts you can also test with are: <br>
Email: lukasnt@ntnu.no | Password: 123 <br>
Email: test@123.no | Password: abc <br>

Or of course make your own account with the create-account menu. (Account name doesn't have to be on email format at all).


## Running the tests

Step 1: Go to /project in CMD and write the command. For example:

```
gradle test
```

Step 2: If you want to generate test reports with jacoco write the following command:

```
gradle jacocoTestReport
```

They will be generated in each module (except api tests are in server) and can be found in /build/jacocoHtml or /build/reports/jacoco/test/html folder relative to the submodule.


## Built With

* [Gradle](https://docs.gradle.org/current/userguide/userguide.html) - Dependency Management

## Project Status

We have completed the main functionality of the service, as described in the user story, and made the application restful.

Features:
* Sending messages
* Recieving messages in inbox
* Creating a user
* Logging in
* Deleting messages in inbox


## Authors

* **Lukas** - *Gradle and IO* - [lukasnt](https://gitlab.stud.idi.ntnu.no/lukasnt)
* **Brage** - *Gradle, JUNIT tests* - [bragesc](https://gitlab.stud.idi.ntnu.no/bragesc)
* **Aleksander** - *Domainlogic* - [aleksawk](https://gitlab.stud.idi.ntnu.no/aleksawk)
* **Erlend** - *FXML og App-controller* - [erlenmom](https://gitlab.stud.idi.ntnu.no/erlenmom)

## User Stories  

Nr. 1: "As a user, i want to read messages from other people."

Nr. 2: "As a user I want to have a contacts list that automaticly keeps track of everyone I have communicated with, so that I can easly contact them"


```plantuml

component core {
	package project_core.core
	package project_core.io
	package project_core.json
}

component jackson {
}

project_core.json ..> jackson

component fxui {
	package project_fxui.ui
}

project_fxui.ui ..> project_core.core
project_fxui.ui ..> project_core.json

component restapi {
	package project_restapi.restapi
}

component javafx {
	component fxml {
	}
}

project_fxui ..> javafx
project_fxui ..> fxml

project_restapi.restapi ..> project_core.core
project_restapi.restapi ..> project_core.json

component jaxrs {
}

project_restapi ..> jaxrs

component restserver {
	package project_restserver.restserver
}

project_restserver.restserver ..> project_core.core
project_restserver.restserver ..> project_restapi.restapi

component grizzly2 {
}

project_restserver ..> grizzly2
```