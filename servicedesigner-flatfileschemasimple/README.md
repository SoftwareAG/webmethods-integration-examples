# Create a flatfile schema for converting csv flat file to xml or json using Service Designer 

This example shows you how to use service designer to

* create a flat file schema via wizard to parse a file with a fixed structure.
* Put and get input and output via ftp.

A description hwo to build the complete example yourself can be found in [this article in our TECHCommunity](http://techcommunity.softwareag.com/pwiki/-/wiki/Main/Simple%20flat%20file%20parsing%20with%20the%20webMethods%20flat%20file%20adapter).

webMethods Service Designer is a lightweight integrated design and development tool that includes:

* webMethods Service Development for designing and developing services for the webMethods Microservices Runtime and managing them under version control.
* Unit Test Framework for creating unit tests for the services that you develop and include in Continuous Integration for build verification.
* webMethods Microservices Runtime (referred to as Local Development Server) for deploying and testing the assets.

You can use/build the example as well using an Integration Server or Microserviceruntime with SoftwareAG Designer.

## Requirements

* Windows 10 or Windows 2019 Server

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

If you want to get the package properly into your workspace using the git integration of designer, please have a look at [my article about getting packages from git or github into Service Designer](http://techcommunity.softwareag.com/pwiki/-/wiki/Main/Get%20a%20webmethods%20package%20from%20github%20into%20your%20service%20designer).

If you want to have it quick and easy, 
* Copy the directory Demo_File_Converter (located inside ISPackages ) inside C:\Programs\wMServiceDesigner\IntegrationServer\packages (This is just for demo purposes and not a recommended way to deploy packages to a Microservice Runtime or Inetgration Server).
* Go to it's admin page (http://localhost:5555/)
* On the left side expande the Packages section, click Packages-Management
* Click Activate Inactive Packages
* Select Demo_File_Converter.
* Click Activate Packages


### Run the example

#### Test it using ftp

Make sure, the FTP port is enabled. The example below it is on port 8021. 
An example file can be found in the Ressources dirctory of this package, if you may need to adjust the local path according to the location of your Installation:

cd C:\wMServiceDesigner\IntegrationServer\packages\Demo_File_Converter\resources
ftp
open localhost 8021
cd /ns/DemoFileConverter/ftp/bankStatementFileToJson
put DemoBankStatement01.csv file_01.csv;application:x-wmflatfile
get file_01.csv

cd /ns/DemoFileConverter/ftp/bankStatementFileToXML
put DemoBankStatement01.csv file_02.csv;application:x-wmflatfile
get file_02.csv

Inspect the output files in your local directory.


### Diagnosis



______________________
For more information you can Ask a Question in the [TECHcommunity Forums](http://techcommunity.softwareag.com/home/-/product/name/command-central).

You can find additional information in the [Software AG TECHcommunity](http://tech.forums.softwareag.com/techjforum/forums/list.page?product=command-central).
______________________
These tools are provided as-is and without warranty or support. They do not constitute part of the Software AG product suite. Users are free to use, fork and modify them, subject to the license agreement. While Software AG welcomes contributions, we cannot guarantee to include every contribution in the master project.

Contact us at [TECHcommunity](mailto:technologycommunity@softwareag.com?subject=Github/SoftwareAG) if you have any questions.
