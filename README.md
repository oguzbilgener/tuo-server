# The Ultimate Organizer Server Application
This project has passed the build test on a Debian 7.2 (Linux 3.2) 64-bit server with Play! Framework 2.2.2, JDK version 1.7.0_25 and Scala version 2.9.2.
	
# Requirements
- A UNIX-like operating system
- Play! Framework 2.2.2+
- A symbolic link to **tuo-core** source (co.uberdev.ultimateorganizer.core) in app/co/uberdev/ultimateorganizer/ directory to allow Play! Framework to recognize the core module. 
- PostgreSQL 9.3 database server

## Build Instructions
After you have provided all the requirements, open a new database and a user for this database in PostgreSQL. Then open `conf/application.conf` to enter your new database credentials. run SQL commands in `conf/sql` folder to make sure you have created the neccesary database structure. 

Open a new Terminal window and `cd` into tuo-server. Then use `play run` command to start the server application in `localhost:9000`. If the program builds and runs without any problems, you can send HTTTP requests to the server. 
**Warning:** Our current server address (api.ultimateapp.co) is hardcoded into the source of Android project. If you want to try your own server, open APIRequest.java in client package in tuo-android project to change it. Make sure your Android device can access to your server program using HTTP.

If you are having any problems with building the project, please contact `oguz@bilgener.me`.