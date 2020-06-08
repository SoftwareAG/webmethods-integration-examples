# webmethods-integration-examples
Collection of examples for using webMethods Integration platform. This is intended to give you starting points for common problems and use cases. webMethods is a multiplatform enterprise integration server that supports integration of diverse services such as mapping data between formats and communication between systems.

## Docker

### Dockerbuilder

Those examples show you how to use dockerbuilder to create you own images using command central templates. The templates are intentionally hold simple in order to show the basic principles and allow you to extend them for your own use cases.

1. [Dockerbuilder example for Integration Server](https://github.com/SoftwareAG/webmethods-integration-examples/tree/master/dockerbuilder-integrationserver)
2. [Dockerbuilder example for Microservice Runtime](https://github.com/SoftwareAG/webmethods-integration-examples/tree/master/dockerbuilder-microserviceruntime)

## Service Designer / Service Development

Those examples show you how to use Service Designer to quickly develop typical services and/or integrations. They are mainly intented to show typical concepts, so they will not be "production ready" as they are! They will work with Service Designer as well as Desginer Workstation.

### Development Patterns

1. [Using a flat file schema to parse a file](https://github.com/SoftwareAG/webmethods-integration-examples/tree/master/servicedesigner-flatfileschemasimple)
This demonstrate how to build a flat file schema using the wizard from a given flat file and use it to convert the file to json or xml.
2. [Dynamic conversion of a flat file to xml or json](https://github.com/SoftwareAG/webmethods-integration-examples/tree/master/servicedesigner-csvconverter)
This demonstrates the usage of service Desginer to build docker containers and exposing Rest APIs. The example will convert a csv dynamically toxmlor json.

### Local Development Toolings

1. [Import Integration Server packages automation](https://github.com/SoftwareAG/webmethods-integration-examples/tree/master/localdev_tools_importPackages2IS) This shows how to import mutliple packages retrieved from version control into the local Integration Server automated with an ANT script.

## Adapters

Those examples show you how to develop, deploy, configure, and use custom coded Integration Server adapters and explain use cases the adapters can be used for.

1. [WxSocketAdapter (source code, eclipse project, installation guide)](https://github.com/SoftwareAG/webmethods-integration-examples/tree/master/integrationserver-wxsocketadapter)
This adapter demonstrates how to connect to low-level resources and devices within the Integration Server. It connects to TCP/IP raw sockets, as well as serial communication devices. It delivers two way communication to the devices and channels (reading/writing).

## Misc

1. [webmethods-hybridintegration](https://github.com/SoftwareAG/webmethodsio-hybridintegration) - step by step how to upload a webMethods Integration Server (IS) workflow to the Webmethods.io Integration (wm.io) cloud
______________________
For more information you can Ask a Question in the [TECHcommunity Forums](http://techcommunity.softwareag.com/home/-/product/name/command-central).

You can find additional information in the [Software AG TECHcommunity](http://tech.forums.softwareag.com/techjforum/forums/list.page?product=command-central).
______________________
These tools are provided as-is and without warranty or support. They do not constitute part of the Software AG product suite. Users are free to use, fork and modify them, subject to the license agreement. While Software AG welcomes contributions, we cannot guarantee to include every contribution in the master project.

Contact us at [TECHcommunity](mailto:technologycommunity@softwareag.com?subject=Github/SoftwareAG) if you have any questions.
