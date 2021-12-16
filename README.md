# Workshop for deploying classic simple application

Small project SpringBoot/Angular just for testing purpose

## Goal

Deploy this small project into a Kubernetes cluster

## Technologies ?

Choose what you want as long as you can go quickly (nodejs, java, golang, whatever)

## Features expected

### Backend

Your app need the following features for the rest of our workshop:

* can read a configuration where we can set up the following parameter: the welcome message content, application name
  and the version. WARNING: those parameters should be able to read from a file and/or from environment variables.
* can get the host where the app is deployed
* expose those REST endpoints:
  1) /info: a message with a payload containing the host name alongside with the version (aka: vDev running on
     localhost)
  2) /welcome: a message with a payload containing the welcome message content (ex: Hello world!)
     Optional:
  3) /whoami: a message with a payload containing only the host name (ex: localhost)
  4) /build: a message with a payload containing only the version (ex: vDev)

### Frontend

Your app need the following features for the rest of our workshop:

* read a configuration file where we can set up
  1) application version,
  2) api rest endpoint,
  3) environment name
  4) featre flip a polling to our backend rest endpoint /info.

* a webpage displaying the main content:
  1) welcome message from our backend (/welcome)
  2) backend information (/info). When polling disabled, only display once /info otherwise display every 5s /info

## How

1. Create backend and frontend
3. Create docker image for `backend` and `frontend`
4. Push to a docker registry
5. Write kubernetes descriptor for `backend` and `frontend`
6. Apply descriptors

## Correction

You can use this repository code from step 1 to step 7 (use tags 😉)

### Other tutorial / Resources

1) [Developing and pacakging nodejs docker](https://learnk8s.io/developing-and-packaging-nodejs-docker)
2) [Deploying Node.js apps in a local Kubernetes cluster](https://learnk8s.io/deploying-nodejs-kubernetes)
3) [Scaling Node.js apps on Kubernetes](https://learnk8s.io/scaling-nodejs-kubernetes)
4) [Deploying Node.js apps in EKS](https://learnk8s.io/deploying-nodejs-kubernetes-eks)
