# Chatbot

A Chatbot designed for Digital Finance Institute. It aims at answering public queries regarding Financial Technology,
with the help of IBM Watson Assistant and Discovery. Through file uploading and website crawling, Watson Discovery 
will be trained and more accurate answers will be provided. IBM Watson Text-To-Speech is also used to convert Chatbot's
answers into audio. As an alternative, the file uploaded and website crawled can also be indexed and queried using
Lucene text search engine, acting as an extra answer on top of the IBM answer.

Server runs on localhost:8080.

## Extra Features
- Text-to-speech
- Highlight previous question-answer pair
- Option to hide the chatbot
- Full-size chatbot when browser size is small
- Rate each answer good/bad 
- Analytics of user queries
- Give Feedback
- Drag and drop file upload

## Login
In order to login to the Admin Dashboard use the following username and password
- username: admin
- password: admin

## Demonstration Video
https://www.youtube.com/watch?v=j8gyTp7vBXI&feature=youtu.be

## Overview

### Documents
- Team description 
- Process taken in this project

### Product
- Summary of the project including potential users and scenarios
- Draft of UI/UX
- Personas
- Competition 
- User Stories

### sprints
- Burn Down Chart and Task Board (Start, Middle and End) screenshots from Jira for Sprint 2 to 8.

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
