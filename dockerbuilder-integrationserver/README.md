# Command Central Docker Builder Example

This project demonstrates how to build Docker images using Command Central Docker Builder.
Docker Builder allows the creation of docker images based on Command Central Templates.
With templates you can generate automated reproducible installations of webMethods components.

This is a basic example generating a docker image with a minimal Integration Server.
The template is kept simple in order to focus on how a template is used with dockerbuilder.
Once the basic functionality is understood, you should be able to insert your own templates.

## Overview

Command Central Docker Builder is a tool provided by Software AG
on [Docker Store](https://store.docker.com/images/softwareag-commandcentral).



## How it works

* Dockerbuilder will load any (single!) template which resides within the directory of the build context.
* Licenses are expected in a zip file a subdirectory licenses/licenses.zip. This way multiple licneses can be loaded.
* The installation is done by a command central server into the directory /opt/softwareag which is registered in the cc server as node.
* The command central server will be removed using a multistage build, so only the product installation  will remain.
* Build arguments are set in the docker-compose. Variables for the template may be provided in env.properties.

## Requirements

* Docker Engine 17.05 or newer with support for [multi-stage builds](https://docs.docker.com/develop/develop-images/multistage-build/)
* Access to Empower with the right to download software
* A valid license file for Integrationserver
## Preparation

### Get dockerbuilder

Login to Docker Store with your Docker ID, open [Command Central image page](https://store.docker.com/images/softwareag-commandcentral).
Proceed to Checkout and accept license agreement to get access to Command Central images.

Login to Docker with your Docker ID from your console and verify you can download the images:

```bash
docker login
docker pull store/softwareag/commandcentral-builder:10.3
```

### add license file

Create directory licenses in this location
Copy licenses as zip to licenses/licenses.zip

### Set EMPOWER CREDENTIALS

Provide your Empower SDC credentials (email and password):

Linux or Mac:

```bash
export EMPOWER_USR=you@company.com
export EMPOWER_PSW=*****
```

Windows:

```shell
set EMPOWER_USR=you@company.com
set EMPOWER_PSW=*****
```

## Building images

To build the microservice container image run:

```bash
docker-compose build is

...
Successfully tagged softwareag/isbuild:10.3
```

N.B.: This will take some time to run, as it downloads the installationfiles and does a full installation. 
Afterwards you will have a container image which can be started quite fast.

Verify the image by launching the container and running a simple test, e.g.:

```bash
docker run --name isbuild  -p 55555:5555 softwareag/isbuild:10.3
```

Open a browser and go to http://localhost:55555. Login with user: Administrator, password manage
(You need to wait until IS is started up which may take a minute).

## Inspecting the logs

If you want to monitor the startup sequence or any other logs later on

Open a shell inside the container

```bash
docker exec -ti isbuild bash
```

Tail the server.log by

```bash
tail -f /opt/softwareag/IntegrationServer/instances/default/logs/server.log 
```


## Troubleshooting

### Running on Windows host

Your Docker build runs on Windows and fails, ensure that git client uses UNIX crlf instead of Windows.
Change git configuration and re-clone the repository

```bash
git config --global core.autocrlf false
```

_______________
Contact us at [TECHcommunity](mailto:technologycommunity@softwareag.com?subject=Github/SoftwareAG) if you have any questions.
_______________
For more information you can Ask a Question in the [TECHcommunity Forums](http://techcommunity.softwareag.com/home/-/product/name/command-central).

You can find additional information in the [Software AG TECHcommunity](http://tech.forums.softwareag.com/techjforum/forums/list.page?product=command-central).
_______________
DISCLAIMER
These tools are provided as-is and without warranty or support. They do not constitute part of the Software AG product suite. Users are free to use, fork and modify them, subject to the license agreement. While Software AG welcomes contributions, we cannot guarantee to include every contribution in the master project.

