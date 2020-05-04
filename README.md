# Chatbot

A Chatbot designed for Digital Finance Institute. It aims at answering public queries regarding Financial Technology.


Server runs on localhost:8080.


## Login
In order to login to the Admin Dashboard use the following username and password
- username: admin
- password: admin

## Features
-
-
-

## Demonstration Video
https://www.youtube.com/watch?v=j8gyTp7vBXI&feature=youtu.be

## Overview

### Documents
Team description and process taken in this project

### Product
Summary of the project including potential users and scenarios, the draft of UI/UX, personas, competition and user stories

### sprints
All Burn Down Chart and Task Board (Start, Middle and End) screenshots from Jira for Sprint 2 to 8.

### frontend
- Front-end application using React

### src
- Back-end application using Java Spring Boot

### First Time Running
```sh
mvn clean install
```

### Development Environment
- JDK 1.8
- Maven 3.2 up
- PostgreSQL 12.2

```sh
cp src/main/resources/localhost-application.properties src/main/resources/application.properties
mvn spring-boot:run
```

### Docker

```sh
cp src/main/resources/docker-application.properties src/main/resources/application.properties
docker-compose up
```

### Production

```sh
mvn package && java -jar target/chatbot-0.1.0.jar
```
