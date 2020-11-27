# EmpireEconomy
A spigot plugin for me and my friends to use on our Minecraft server. For a list of commands, use in-game /help.

# Contribution Guide
Create a branch for your contribution, and when you're done, create a PR into the develop branch. All changes in the develop branch will be merged into master after thorough testing along with a version increment.

The main class is EmpireEconomy.java. This class is used to initialize commands and listeners.

The data folder contains data objects. All objects in this folder are serialized and saved to the configuration files.

The cmd folder contains commands and helpers for commands. Inside this folder is the conversations folder, which contains objects that inherit from the Conversations API.

The events folder contains event listeners. These are executed every time the given event occurs, with the exception of zoneEntryListener, which runs every five seconds.

The utils folder contains various utility singletons that can be accessed from anywhere.
