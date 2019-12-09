# Build a microservice converting csv flat file to xml or json using Service Designer 

This example shows you how to use service designer to

* create services for transforing an arbitrary CSV file to xml or json
* expose the services as Rest API.
* build a docker image hosting those services

webMethods Service Designer is a lightweight integrated design and development tool that includes:

* webMethods Service Development for designing and developing services for the webMethods Microservices Runtime and managing them under version control.
* Unit Test Framework for creating unit tests for the services that you develop and include in Continuous Integration for build verification.
* webMethods Microservices Runtime (referred to as Local Development Server) for deploying and testing the assets.

A more extensive description, especially a tutorial how to build the code yourself can be found in the Techcommunity [article] (http://techcommunity.softwareag.com/pwiki/-/wiki/Main/Build%20a%20microservice%20converting%20csv%20flat%20file%20to%20xml%20or%20json%20using%20Service%20Designer)

## Requirements

* Windows 10 or Windows 2019 Server
* Docker for Windows with Engine 19.03 or newer  

## Preparation

### Get ServiceDesigner

Download ServiceDesigner.
Unpack it to a local directory (I'm assuming C:\Programs\wMServiceDesigner in the course of this howto).

http://techcommunity.softwareag.com/ecosystem/communities/public/webmethods/contents/downloads/webmethods-service-designer/index.html

## Build (or import) the package

* Start Service Designer by clicking ServiceDesigner.exe.
* When asked if you want to start the Local Development Server click yes. Otherwise click the menu item Server-Start in Designer.
* Wait until the server is started. You can either check if the admin page is reachable (http://localhost:5555/) or looking for

```shell
ISSERVER|| 2019-11-14 14:03:47 CET [ISS.0014.0002I] Initialization completed in 51 seconds. 
ISSERVER|| 2019-11-14 14:03:47 CET [ISS.0025.0016I] Config File Directory Saved 
```

in the console window.

The sample code is explained in this article in techcommunity:

Import the sample code 

* Copy the directory Demo_CSV_Converter (located inside ISPackages ) inside C:\Programs\wMServiceDesigner\IntegrationServer\packages (This is just for demo purposes and not a recommended way to deploy packages to a Microservice Runtime or Inetgration Server).
* Go to it's admin page (http://localhost:5555/)
* On the left side expande the Packages section, click Packages-Management
* Click Activate Inactive Packages
* Select Demo_CSV_Converter.
* Click Activate Packages


## Build the Dockerimage

### Preparations

Switch windows docker desktop to windows containers.

Open a command prompt. You may need to start cmd as administrator to have all needed write permissions.

Login to docker to be able to pull the windows base image.

```shell
docker login -u youruser -p ****
```

Switch into the docker directory of your installation. If you placed the Service Deisgner into C:\Programs\wMServiceDesigner, it will be:

```shell
cd C:\Programs\wMServiceDesigner\IntegrationServer\docker
```

### Process of building images

Building an image consits of 2 phases with 2 steps each. The phases are

* Build a Microservice reference image. This contains the Microservice Runtime and in a real life environment would be used as base for multiple different service images. This image will not be startable.
* Build the service image. THis will use the base image and add your specific logic to it.

The steps in each phase are

* Create a dockerfile
* Build the image

### Build & run the example

Create the dockerfile for the base image for Microservice Runtime. The following command will create a dockerfile islean in the directory C:\Programs\wMServiceDesigner

```shell
is_container.bat createLeanDockerfile -Dfile.name=islean –Dimage.name=mcr.microsoft.com/windows/servercore:ltsc2019
```

Build the base image

```shell
is_container.bat build -Dfile.name=islean -Dimage.name=islean:1.0
```

Create the dockerfile for the service image. The following command will create a dockerfile CSVConverter in the directory C:\Programs\wMServiceDesigner\IntegrationServer\packages

```shell
is_container.bat createPackageDockerfile -Dimage.name=islean:1.0 -Dfile.name=CSVConverter -Dpackage.list=Demo_CSV_Converter
```

Build the service image.

```shell
is_container.bat buildPackage -Dfile.name=CSVConverter -Dimage.name=csvconverter:1.0
```

Run the service image

```shell
docker run --name csvconverter -d -p 15555:5555 -p 19999:9999 csvconverter:1.0
```

#### Test it using html

If the container is running, issue one of the following lines in a webbrowser

http://localhost:15555/invoke/DemoCSVConverter.Services:CSV2Json?inCSV=a,b,c;1,2,3;10,20,30&lineDelimiter=;&fieldDelimiter=,

http://localhost:15555/invoke/DemoCSVConverter.Services:CSV2XML?inCSV=a,b,c;1,2,3;10,20,30&lineDelimiter=;&fieldDelimiter=,

where we assume

* the file is a,b,c;1,2,3;10,20,30
* the lines are delimited by ; 
* and the fields by ,

this is kept simple so it fits into the adress line of the browser, when calling with other clients you may pass real files.

#### Test it via Rest

You get the swagger for the Rest API via the following links

* Json http://localhost:15555/rad/DemoCSVConverter.Rest:CSV2JsonConverter_API?swagger.json
* Yaml: http://localhost:15555/rad/DemoCSVConverter.Rest:CSV2JsonConverter_API?swagger.yaml

Just import them into your Rest client of choice and call it with the parameters like

* inCSV: a,b,c;1,2,3;10,20,30
* lineDelimiter: ; 
* fieldDelimiter ,

### Diagnosis

If you want to introspect the running container, you may do so by issuing

```shell
docker exec -ti csvconverter cmd
```

______________________
For more information you can Ask a Question in the [TECHcommunity Forums](http://techcommunity.softwareag.com/home/-/product/name/command-central).

You can find additional information in the [Software AG TECHcommunity](http://tech.forums.softwareag.com/techjforum/forums/list.page?product=command-central).
______________________
These tools are provided as-is and without warranty or support. They do not constitute part of the Software AG product suite. Users are free to use, fork and modify them, subject to the license agreement. While Software AG welcomes contributions, we cannot guarantee to include every contribution in the master project.

Contact us at [TECHcommunity](mailto:technologycommunity@softwareag.com?subject=Github/SoftwareAG) if you have any questions.
