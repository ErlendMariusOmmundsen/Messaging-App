# Group gr1922 repository

This is currently a messaging application where registered users can send messages to each other if they are on the same computer. With the use of REST, it can send messages over the Internet if you have a server to spare. 

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


Step 3 (optional): If you want to test, generate test reports and generate checkstyle and spotbugs reports all in one, write the following command:

```
gradle check
```

All the reports can be found in each module in the realtive path /build/reports.
(click [here](codeQualityReadme.md) to see how we have handled checkstyle and spotbugs warnings)

## Built With

* [Gradle](https://docs.gradle.org/current/userguide/userguide.html) - Dependency Management

## Project Status

We have completed the main functionality of the service as planned and added the contacts feature, as described in the user stories, and made the application restful.

Features:
* Sending messages
* Receive messages in inbox
* Creating a user
* Logging in
* Deleting messages in inbox
* Having contacts


## Authors

* **Lukas** - *Domainlogic, JSON, testing, RESTserver, documentation* - [lukasnt](https://gitlab.stud.idi.ntnu.no/lukasnt)
* **Brage** - *Gradle/Java setup, JUNIT test* - [bragesc](https://gitlab.stud.idi.ntnu.no/bragesc)
* **Aleksander** - *Domainlogic, JSON, REST-api, documentation* - [aleksawk](https://gitlab.stud.idi.ntnu.no/aleksawk)
* **Erlend** - *Client-side, UI/frontend, testing, documentation* - [erlenmom](https://gitlab.stud.idi.ntnu.no/erlenmom)

## User Stories  

"As a user, i want to read messages from other people."

"As a user, i want to create an account."

"As a user, i want to log in."

"As a user, i want to log out."

"As a user, i want to delete messages."

"As a user, i want to send messages."

"As a sender, I want other users i have received messages from to be easy to contact again."

## Package diagram of the project

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

fxui ..> javafx
fxui ..> fxml

project_restapi.restapi ..> project_core.core
project_restapi.restapi ..> project_core.json

component jaxrs {
}

restapi ..> jaxrs

component restserver {
	package project_restserver.restserver
}

project_restserver.restserver ..> project_core.core
project_restserver.restserver ..> project_restapi.restapi

component grizzly2 {
}

component jersey {
}


restserver ..> grizzly2
restserver ..> jersey
```

## Sequence diagram 

This diagram shows what happens during a successful account creation. 

```plantuml

actor User



User -> appController: *Request account*
appController -> RestAccountDataAccess: createAccount(Account a)
RestAccountDataAccess -> AccountService: createAccount(Account a)
AccountService -> Account: createAccount(Account a)
Account -> AccountIO: newAccount(this)
database users.txt
AccountIO -> users.txt: println(String mail + "\t" + String password)
note right of AccountService: If no error happens in core the creation was successful and the API returns "true". 
AccountService -> RestAccountDataAccess: true
RestAccountDataAccess -> appController: true
appController -> User: loginVisibility()


```
