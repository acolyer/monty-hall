Monty Hall Problem (Let's Make a Deal) Spring REST API Sample App
==============================

This sample application implements a REST API for playing the "Let's Make a
Deal" game, famous as the [Monty Hall
problem](http://en.wikipedia.org/wiki/Monty_Hall_problem). 

It uses a gradle based build. You will need to create a gradle.properties
file in the project root directory with the following contents:

	
	# Cloud Foundry
	
	cloudfoundryUserName = your-user-name
	cloudfoundryPassword = your-password
	cloudfoundryAppName  = your-app-name (e.g. monty-hall)
	cloudfoundryTarget   = http://api.cloudfoundry.com

	# Tomcat
	tomcat6 = path to your local tomcat installation

To build and run type 'gradle build cargoRunLocal'.
To deploy to Cloud Foundry 'gradle cf-push'.
