# Import Integration Server packages automation

## Overview

This project demonstrates how to get multiple packages Integration Server packages from a git repository into your local workspace with the provided ant script. The intent ist to automate the task "Move package to IS", especially for projects with many packages, when Designer Workstation or service Designer is setup the first time or reinstalled.

A more elaborate description can be found in [this article in oru TechCommunity](http://techcommunity.softwareag.com/pwiki/-/wiki/Main/How%20to%20automate%20package%20import%20to%20a%20local%20Integration%20Server#ScriptCreation).

## How it works

Import of existing IS packages into a local workspace

To get a versioned IS package into the local Integration Server as a local service development package, 2 things have to be done:

* Import the package directory as project into Designer with Import project from Git perspective (This can be done for multiple packages at once).
* Create symbolic links in the package directory of Integration Server pointing to the package directories in the git directory. This is usually done by the "Move package to IS" contect menu entry, but can be as well done by script:
   * `mklink /j <targetLocation> <sourceLocation>`  [for window]
   * `ln -s <sourceLocation>  <targetLocation>` [for mac and linux]

Please be aware that support for windows junctions have been introduced to 10.3 with Fix 14, so junction will not be recognisedby earlier versions.
   
Afterwards either a restart of IS or an activation and reload of the packages is necessary.

The provided ant sript automates the creation of the links for a windows environment, as this is the most common os for developers (can be easilyadjusted to linux), and activates the packages afterwards, so only the refresh of the development server view in Designer remains.

The original script was written by Biswa Bhusan Dalai (Software AG).

## Requirements

Designer Workstation 10.3 or higher.

Please be aware that support for windows junctions have been introduced to version 10.3 with Fix 14, so you will need at least:

* Local Version Control Integration Plug-in to Designer 10.3 Fix 14 or higher
* Service Development Plug-in to Designer 10.3 Fix 16 or higher


## Concept

The batch file/ant properties should be created centrally for every project. This ensures that the proper loading order is defined.

As every package in IS is associated with a separate eclipse project, one way is to use a dedicated tools project in every git repository to host the ant script and it's properties file, so every developer can just start the ant script when setting up/recreating his/hers local development environment. This means, the ant script is replicated for every project, but also gives the flexibility for adjusting for specific needs. 

The whole process works fine as well with multiple git repositories in the same Designer workspace.

### Preparation (dedicated team for every project responsible)

* Copy/Adjust the ant script into the tools directory of every project
* Set specific build properties for every project

### When setting up local Dev Environment

* First time setup only: Import all eclipse project folders (package dir / tools dir) into eclipse workspace
* Optional:Adjust property file, if local setup differs from central configuration (e.g. path to users local GIT directory).
* Run the ant script from within eclipse


## The actual process

1. Set up your local Workspace. If you alreday have a local workspace holding your packages and tools project, continue with (3).
   * Clone the repository holding you packages to a local directory. If you did not do the clone with Designer GIT perspective, add the local GIT repository to the repository list of Designer.
   * Import the package folders using the Import Projects menu entry as Designer/Eclipse projects.
   * If the tools project, the ant script and the proper properties alreday have been setup by somebody else, continue with (3).
2. Set up the ant script and it's configurations.
   * Create a project for holding the ant file and it properties.
   * Copy the ant script import_IS_packages.xml and property file build.properties to the project directory.
   * Adjust the build.properties to the correct common settings for the project (package list, standard install location, standard port). The default target of the ant script is import-multiple-packages which will import the packages listed in the properties file in the given order.
   * Check the path to the package directory inside you installation, which is set in the ant sript. As Microservice Runtime starting with 10.5 does not have instanced anymore, there is a difference for IS and MSR:
     * For Integration Server it needs to be `${webMethods.home}\IntegrationServer\instances\default\packages`
     * For Microservice Runtime >= 10.5 it needs to be `${webMethods.home}\IntegrationServer\packages`
3. Import the packages
   * Optional: Adjust the properties in build.properties to specific local settings (e.g. different local git directory). 
   * Open the ANT script with the ant editor in Designer.
   * Run the Ant script (with the target import-multiple-packages).
   * Refresh the Server in the Service Development perspective.

## Alternatives targets 

### import-packages-from-repo

Imports all packages located in a specific repo. This assumes, all folders in the location are packages and the load order is of no importance.

   
_______________
Contact us at [TECHcommunity](mailto:technologycommunity@softwareag.com?subject=Github/SoftwareAG) if you have any questions.
_______________
For more information you can Ask a Question in the [TECHcommunity Forums](http://techcommunity.softwareag.com/home/-/product/name/command-central).

You can find additional information in the [Software AG TECHcommunity](http://tech.forums.softwareag.com/techjforum/forums/list.page?product=command-central).
_______________
DISCLAIMER
These tools are provided as-is and without warranty or support. They do not constitute part of the Software AG product suite. Users are free to use, fork and modify them, subject to the license agreement. While Software AG welcomes contributions, we cannot guarantee to include every contribution in the master project.

