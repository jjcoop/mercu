# Mercu (microservice example)
A simple example of a RESTful application using Domain Driven Design principles.

Potentially available at: [link](http://54.206.97.89/dash)

## Description

Built from class CSCI318: Software Practices and Principals, Univerity of Wollongong
Tutor: Dr Guoxin Su
Group Members:
- Jacob Cooper
- Damon O'Neil
- Brendon Kortekaas
- Jarred Finch

## Getting Started

### Dependencies
* [npm](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
* [Kafka](https://kafka.apache.org/downloads), version 3.3.1 used in development
* Java 11
* Python
* npm
* maven

### Installing Kafka
* [Kafka Tutorial](https://www.geeksforgeeks.org/how-to-install-and-run-apache-kafka-on-windows/)

### Running the Backend
* Install and successfully run Kafka
* Download and extract the folder
* Open the folder in VS-code
* Navigate through *backend\procurems\src\main\java\mercury\procurems\ProcuremsApplication.java*
* Run the file 
* Repeat this with the other 3 microservices
* If java projects extension is installed in VS-code, micro-services can be launched from dropdown 'Java projects'

### Running the Frontend
* Open a new terminal instance in VS-code
* change directory to *frontend* 
* On first run, use command npm i to install all dependencies
* After this, run *npm run dev*
* Terminal should display address run on (CRTL + click to open in default browser)

### Using Python Simulation File
* Once everything is running, to see a live demonstration of the application,
open a new terminal and run *python simulate.py*

* Answer Y or N for respective objects

## Built With
* React JS
* Node JS
* Material UI
* Springboot

## Other
* To see a more in depth readme, check the frontend readme
