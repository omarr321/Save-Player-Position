# Save Player Position
## About
Save Player Position will be a simple plugin that saves a player's position on the server when they leave a world or the server. When they enter the world, this plugin will bring them to their last position.
## 1.0 - Features
### 1.1 - Keep Last Known Position
The plugin will store the last known location of the player on a per-world basis, so when they re-enter that world, they get teleported back to where they were.
### 1.2 - Blacklist Worlds
The ability in the config file to blacklist worlds. If a world is blacklisted, the plugin will not keep data on that world or work in that world.
### 1.3 - Group Worlds
The ability to group worlds in the config file, so the plugin will keep player data on a group basis so when they enter a world in the group. It will teleport them to the last world and position in the group. If the world is not part of the group, it will work like 1.1 and teleport to the last position.
### 1.4 - In-Game Commands and Permissions
The ability to run commands in-game to edit the config and data with proper permissions setup and in place.
## 2.0 - Functions
### 2.1 - Config File
The addition of a config file will save the config of the plugin. This can also be edited and reloaded by the plugin.
### 2.2 - Player Info Save System
The addition of folder/file/files that will store player data about the worlds they've been in.