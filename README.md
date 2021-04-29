# SUUCampus
This is my CS4800 Capstone Project for the semester of Spring 2021 at Southern Utah University
Plugin.yml: This file has all registered commands for the plugin, including a description for each command, a template for how to use each command, permission data for each command, and a default error permission message if you do not have permission to use that command.
Build.xml: This file contains the code to automatically generate a new SuuCampus.jar file anytime a file is saved in the SUUCampus folder, other than this one of course.
src/xyz/Christopher/SuuCampus/MagicPath.java: This file contains the code for all related A* pathfinding.
src/xyz/Christopher/SuuCampus/Main.java: This file contains the structure for the Minecraft plugin, and all command structures.
src/xyz/Christopher/SuuCampus/Pathfinder.java: This file contains the first generation of my custom pathfinding algorithm.
src/xyz/Christopher/SuuCampus/Waypoint.java: This file contains the Waypoint object that is used for teleportation, and pathfinding location data.
src/xyz/Christopher/SuuCampus/sql/MySQL.java: This file contains the SQL object that interfaces between the Minecraft plugin code and the communication with the database that holds the information for Waypoints, Classrooms, Professors, and Departments.
src/xyz/Christopher/SuuCampus/sql/SQLGrabber.java: This file contains the code for all SQL commands, that get and send information, and this file interacts with the MySQL.java file to communicate with the database.
target/SuuCampus.jar: This is the main plugin jar file that is used to install the plugin into the Minecraft server. Anytime a file is saved in the SUUCampus folder it will use the build.xml file to build a new version of this file.
