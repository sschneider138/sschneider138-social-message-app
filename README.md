# Project Summary

This application is a messaging client that includes all the core functionalies of Twitter in a single, instantly deployable package. It is suitable for internal use as an intra-organizational messaging application or for hosting private, password protected chatrooms governed by an administrator. This application will include functionality such as:

* global message board (MVP)
* authentication (MVP)
* authorization (MVP)
* likes (MVP)
* dislikes (MVP)
* page pagination (MVP)
* hashtag/topic grouping (MVP)
* basic hashtag/topic search (MVP)
* accounts (MVP)
* news feed from randomized API (MVP)
* private chat rooms (stretch goal)
* images (stretch goal)
* email (stretch goal)
* multifactor login (stretch goal)
* OAuth (stretch goal)

# Stack

* Backend:
  * Java and Kotlin Spring Boot microservices
  * MySQL database with phpMyAdmin for service databases
  * RestClient HTTP client for inter-container communication
  * Eureka for service discovery
  * Resiliance4j as curcuit breaker/fault tolerance mechanism

* Frontend:
  * NextJS with TypeScript
  * DaisyUI component library

* DevOps:
  * Docker for containerization
  * Kubernetes for container orchestration

* Documentation:
  * Swagger

# Installation

Installation is managed by a sinlge `bash` script. To deploy locally or remotely to a server (on premise or cloud), just run `start.sh`.

# Getting Started

An account is only required if users wish to post messages or join specific chat rooms. Unauthenticated users may browse and search global posts freely.

To create an account, register using your email, or login using your Google, Facebook, Instagram, or Twitter[1] account

# License

The MIT License (MIT)

Copyright (c) Samuel Schneider 2024

# User Stories

* As a user, I want to monitor my posts' popularity by checking the likes and dislikes it receives from others (MVP).
* As a regretful user, I want to be able to delete my posts so that I can avoid embarassment when I sober up (MVP).
* As an unregistered user, I want to freely browse and searchglobal posts and feeds so that I can experience this app without registering (MVP).
* As a new user, I want to authenticate my account so that others cannot post under my name (MVP).
* As a poster, I want to group my posts together so that they are tied with others of a similar interest (MVP)
* As a user, I want to get randomized news in my feed so that I can make informed decisions with my life and finances (MVP).

* As a chat room administrator, I want to delete chats from my chatroom to maintain the character of the chat community (stretch goal).
* As a user, I want to be able to upload images so that I can share pet pictures (stretch goal).
* As a chat room administrator, I want to only allow approved users into my chat so that privacy is maintained (stretch goal).
* As a user, I want to be able to set pagination controls to cater to my preferences (stretch goal)

# Diagrams

![User Interface Diagram] (./images/uiLayout.png)

![Models and Service Diagram] (./images/modelsAndServices.png)

# Footnotes

[1] Note that these are stretch goals
