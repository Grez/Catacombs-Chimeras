# Catacombs-Chimeras D&D Troops project [![Build Status](https://travis-ci.org/Grez/Catacombs-Chimeras.svg?branch=master)](https://travis-ci.org/Grez/Catacombs-Chimeras)

Web application for managing troops, heroes and hero roles.

## Run at localhost

### Build using maven
```
mvn clean install
```
### Run spring-boot app
change current directory to ```catacombs-chimeras-app``` and run:

```
mvn spring-boot:run
```

application will start booting and after you see message like this: 

```
started Application in 20.119 seconds (JVM running for 29.462)
```

there should be ready web interface on ```localhost:8080```

## REST API 
after running the spring-boot app there should be available REST API for manipulating heroes
### List all heroes
REQUEST METHOD: *GET*

REQUEST URI: *pa165/rest/heroes*

REQUEST BODY: *NONE*

RESPONSE BODY: *LIST OF HEROES*

### Get hero entity
REQUEST METHOD: *GET*

REQUEST URI: *pa165/rest/heroes/{id}*

REQUEST BODY: *NONE*

RESPONSE BODY: *HERO ENTITY*

### Create hero entity

REQUEST METHOD: *GET*

REQUEST URI: *pa165/rest/heroes/{id}*

REQUEST BODY: *HERO ENTITY*  (example ```{"name":"petr","experience":80}```)

RESPONSE BODY: *HERO ENTITY*

## Wiki
more info about this project can be found on this project's wiki:
https://github.com/Grez/Catacombs-Chimeras/wiki
