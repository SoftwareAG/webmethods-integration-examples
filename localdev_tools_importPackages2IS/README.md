# Import Integration Server packages automation

## Overview

This project demonstrates how to get multiple packages Integration Server packages from a git repository into your local workspace with the provided ant script. The intent ist to automate the task "Move package to IS", especially for projects with many packages, when Designer Workstation or service Designeris etup the first time or reinstalled.


## How it works

Import of existing IS packages into a local workspace

To get a versioned IS package into the local Integration Server as a local service development package, 2 things have to be done:

* Import the package directory as project into Designer with Import project from Git perspective (This can be done for multiple packages at once).
* Create symbolic links in the package directory of Integration Server pointing to the package directories in the git directory.
** mklink /j <targetLocation> <sourceLocation>  [for window]
** ln -s <sourceLocation>  <targetLocation> [for mac and linux]

Afterwards either a restart of IS or an activation and reload of the packages is necessary.

The provided ant sript automates the creation of the links for a windows environment, as this is the most common os for developers (can be easilyadjusted to linux), and activates the packages afterwards, so only the refresh of the development server view in Designer remains.

The original script was written by Biswa Bhusan Dalai (Software AG).

## Requirements

Designer Workstation 10.3 or higher.

Please be aware that support for windows junctions have been intrduced as a fix to 10.3 ,so you will need at least:

* Local Version Control Integration Plug-in to Designer 10.3 Fix 14 or higher
* Service Development Plug-in to Designer 10.3 Fix 16 or higher


## Preparation


## Get package to local environment

1. Set up your local Workspace. If you alreday have a local workspace holding you packages and tools project, continue with _3. Import the packages_
   * Clone the repository holding you packages to a local directory. If you did the clone ouside Deisgner, add the local GIT repository to the repository list of Deisgner.
   * Import the package folders using the Import Projects menu entry.
2. Only if not alreday done by somebody else for the current repository: Create the tools project andplace the ant script
   * Create a project for holding the ant file and it properties.
   * Copy the ant script import_IS_packages.xml and property file build.properties to the project directory.
3. Import the packages
* Adjust the properties in build.properties to your local settings. The default target of the ant script is import-multiple-packages which will import the packages listed in the properties file in the given order.
* Check the path to the package directory inside you installation, which is set in the ant sript. As Microservice Runtime starting with 10.5 does not have instanced anymore, there is a difference for IS and MSR:
  * For Integration Server it needs to be 	${webMethods.home}\IntegrationServer\instances\default\packages"
  * For Microservice Runtime >= 10.5 it needs to be  ${webMethods.home}\IntegrationServer\packages
* Open the ANT script with the ant editor in Designer.
* Run the Ant script
* Refresh the Server in the Service Development perspective.

```bash

```

_______________
Contact us at [TECHcommunity](mailto:technologycommunity@softwareag.com?subject=Github/SoftwareAG) if you have any questions.
_______________
For more information you can Ask a Question in the [TECHcommunity Forums](http://techcommunity.softwareag.com/home/-/product/name/command-central).

You can find additional information in the [Software AG TECHcommunity](http://tech.forums.softwareag.com/techjforum/forums/list.page?product=command-central).
_______________
DISCLAIMER
These tools are provided as-is and without warranty or support. They do not constitute part of the Software AG product suite. Users are free to use, fork and modify them, subject to the license agreement. While Software AG welcomes contributions, we cannot guarantee to include every contribution in the master project.

