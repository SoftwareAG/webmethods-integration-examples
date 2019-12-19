# Build a microservice converting csv flat file to xml or json using Service Designer 

This example shows you how to use service designer to

* create a flat file schema via wizard to parse a file with a fixed structure.
* Put and get input and output via ftp.
* Put and get input and output from/to a loca ldirectory via filepolling.

A descirption hwo to build the complete example yourself can be found in [this article in our TECHCommunity](http://techcommunity.softwareag.com/pwiki/-/wiki/Main/Simple%20flat%20file%20parsing%20with%20the%20webMethods%20flat%20file%20adapter).

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

```shell
cd C:\wMServiceDesigner\IntegrationServer\packages\Demo_File_Converter\resources
ftp
open localhost 8021
cd /ns/DemoFileConverter/ftp/bankStatementFileToJson
put DemoBankStatement01.csv file_01.csv;application:x-wmflatfile
get file_01.csv
```

for xml you need to cd to the xml processing service:
```shell
cd /ns/DemoFileConverter/ftp/bankStatementFileToXML
put DemoBankStatement01.csv file_02.csv;application:x-wmflatfile
get file_02.csv
```

Inspect the output files in your local directory.

#### Test it using filepoller

The package contains a filepoller using the following local directories:

C:\FilePoller\Json\in
C:\FilePoller\Json\work
C:\FilePoller\Json\done
C:\FilePoller\Json\error

You need to create those directories in order to use the example. If you want or need to place them somewhere else (e.g. because you do not have write access to C:/, you need to change the poller. In order to do so go to the IS admin page (http://localhost:5555/), go to Security - Ports and click the port C:\FilePoller\Json\in. Click Edit File Polling Configuration, confirm, that the port will be disabled for the configuration and adjust the fields 
* Monitoring Directory
* Working Directory (optional) 	
* Completion Directory (optional)
* Error Directory (optional) 	
make also sure to set Enable back to Yes.

The called service will write the result back to the working directory. For security reasons local writes of Integration Server / Microservice Runtime need to be authorised. This is done in the file fileAccessControl.cnf in the directory config of the WmPublic package, e.g. in C:\Programs\ServiceDesigner\IntegrationServer\packages\WmPublic\config. Add the work directoy to the allowedWritePaths of this file, if you left th paths as in my example, the file should look like

```shell
allowedWritePaths=C:\\FilePoller\\Json\\work
allowedReadPaths=
allowedDeletePaths=
```

Copy the sample file DemoBankStatement01 from C:\Programs\ServiceDesigner\IntegrationServer\packages\Demo_File_Converter\resources to C:\FilePoller\Json\in (or use the according directories in your installation). After the polling interval (5 seconds in the example) the poller will pick up the file and the processing service will write the ouput to the work directory (e.g. C:\FilePoller\Json\work), with the extension .json and the prefix FilePolling and a timestamp. The processed input file will be moved to the completion directory (e.g. C:\FilePoller\Json\done).


### Diagnosis



______________________
For more information you can Ask a Question in the [TECHcommunity Forums](http://techcommunity.softwareag.com/home/-/product/name/command-central).

You can find additional information in the [Software AG TECHcommunity](http://tech.forums.softwareag.com/techjforum/forums/list.page?product=command-central).
______________________
These tools are provided as-is and without warranty or support. They do not constitute part of the Software AG product suite. Users are free to use, fork and modify them, subject to the license agreement. While Software AG welcomes contributions, we cannot guarantee to include every contribution in the master project.

Contact us at [TECHcommunity](mailto:technologycommunity@softwareag.com?subject=Github/SoftwareAG) if you have any questions.
