# HOW TO RUN THE APP

## Pre-requirements

1. Install gradle
1. Install docker

## STEPS

Run the following commands in the project root directory

1. Build project with `gradle build`
1. Create docker image with `docker build -t api-cleanuper .`
1. Run docker container with `docker run -p 8080:8080 api-cleanuper`
1. Go to `http://localhost:8080/appointments` and check it out
