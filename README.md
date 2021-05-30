[![Logo](src/pictures_logo.svg)](http://www.pictures-game.cc)

[![Deploy Project](https://github.com/sopra-fs21-group-26/server/actions/workflows/deploy.yml/badge.svg)](https://github.com/sopra-fs21-group-26/server/actions/workflows/deploy.yml) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=sopra-fs21-group-26_server&metric=coverage)](https://sonarcloud.io/dashboard?id=sopra-fs21-group-26_server) [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=sopra-fs21-group-26_server&metric=ncloc)](https://sonarcloud.io/dashboard?id=sopra-fs21-group-26_server) 

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/dashboard?id=sopra-fs21-group-26_server)

## Introduction

In Pictures, each player simultaneously tries to copy a picture with one of five different materials: building blocks, sticks and stones, icon cards, color cubes, or a drawable string.
<br/><br/>
This project was developed as part of a software development course @ the University of Zurich.

## Technologies Used

The backend was fully developed using Java, Springboot & JPA.

## High Level Components

* **userService:** has all the logic of players, responsible for registration, login, editing of users etc.
* **lobbyService:** logic of creating/joining lobbies, ready system, anything that happens in between the menu screen and the start of the game
* **gameService:** finally here we have all the game logic, hence this is our main component

**Correlation:** All users that get registered etc. in the userService class are part of a lobby in the lobbyService class.  
Finally, after we start a game, we make a transition from the lobby service to the gameService. After pressing start the lobby entity converts to a game entity (with new attributes etc.). Hence this is one of our main functions. Without it, no game gets started/created.  
<br/>
Main class/function: Our main class is the gameService class because all of the game logic is implemented there.  
The main functions are:
* ch.uzh.ifi.hase.soprafs21.service.GameService#checkIfGuessCorrect
* ch.uzh.ifi.hase.soprafs21.service.LobbyService#startGame  

Without these two, a game would be unplayable. No game would be started or no points would be correctly distributed, which would make the whole game not worth playing.

## Launch & Deployment

You can use the local Gradle Wrapper to build the application.

Plattform-Prefix:

-   MAC OS X: `./gradlew`
-   Linux: `./gradlew`
-   Windows: `./gradlew.bat`

More Information about [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) and [Gradle](https://gradle.org/docs/).

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

### Test

```bash
./gradlew test
```

### Development Mode

You can start the backend in development mode, this will automatically trigger a new build and reload the application
once the content of a file has been changed and you save the file.

Start two terminal windows and run:

`./gradlew build --continuous`

and in the other one:

`./gradlew bootRun`

If you want to avoid running all tests with every change, use the following command instead:

`./gradlew build --continuous -xtest`

## API Endpoint Testing

### Postman

-   We highly recommend to use [Postman](https://www.getpostman.com) in order to test API Endpoints.

## Debugging

If something is not working and/or you don't know what is going on. We highly recommend that you use a debugger and step
through the process step-by-step.

To configure a debugger for SpringBoot's Tomcat servlet (i.e. the process you start with `./gradlew bootRun` command),
do the following:

1. Open Tab: **Run**/Edit Configurations
2. Add a new Remote Configuration and name it properly
3. Start the Server in Debug mode: `./gradlew bootRun --debug-jvm`
4. Press `Shift + F9` or the use **Run**/Debug"Name of your task"
5. Set breakpoints in the application where you need it
6. Step through the process one step at a time

## Roadmap

If you want to contribute to the project, you can find a selection of missing features below:

* Functioning ELO scoring system
* New kinds of sets
* Other game modes with time limits
* Currently, the leaderboard needs at least 5 players to be registered in order to function correctly. This is due to hardcoding and should be fixed soon.

## Authors and Acknowledgment

**Project Lead & Frontend:** Maximilian Jonescu (https://github.com/maxi1123)  
**Frontend & Game Flow:** Arjun Villanthanam (https://github.com/arjvillan)  
**Backend Lead & Testing:** Max Zehnder (https://github.com/mzehnde)  
**Backend Support:** Jakob Schmid (https://github.com/InfoYak)  
<br/>
Special thanks to our supervisor, Remy Egloff, for always lending a supportive hand: https://github.com/regloff

## License

![license.png](license.png)

Creative Commons Attribution-NonCommercial 4.0 International (CC BY-NC 4.0)
<br/>
<br/>
Please find the license agreement by following the link below:  
https://creativecommons.org/licenses/by-nc/4.0/legalcode
