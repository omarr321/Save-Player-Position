# Save Player Position
## About
Save Player Position is a simple plugin that saves a player's position on the server when they leave a world or the server. This plugin will bring them to their last position when they enter the world.
## Features
### Keep Last Known Position
The plugin stores the player's last known location on a per-world basis, so when they re-enter that world, they get teleported back to where they were.
### Blacklist Worlds
You have the ability to blacklist worlds. The plugin will not teleport in this world.
### Group Worlds
You have the ability to group worlds, so the plugin will keep player data on a group basis so when they enter a world in the group. It will teleport them to the last world and position in the group.
### In-Game Commands
You have the ability to run commands that edit the blacklist, groups, and debug.
### Permissions
There is a permission system so you can give the player certain commands.
## List of commands, what they do, and their permission node
| Command                                               | Description                                                    | Permission             |
|-------------------------------------------------------|----------------------------------------------------------------|------------------------|
| /spp                                                  | Shows the help                                                 | \<none\>               |
| /spp help                                             | Shows the help                                                 | \<none\>               |
| /spp version                                          | Shows the plugin version                                       | spp.command.version    |
| /spp reload                                           | Reloads the config                                             | spp.command.reload     |
| /spp clean                                            | Cleans up old data                                             | spp.admin.clean        |
| /spp setdebug \<bool\>                                | Sets the debug value                                           | spp.command.setdebug   |
| /spp blacklist [add/remove] \<world\>                 | Adds/Removes a world from the blacklist                        | spp.admin.blacklist    |
| /spp blacklist list                                   | List all the blacklisted worlds                                | spp.admin.blacklist    |
| /spp group [create/delete] \<group\>                  | Creates/Deletes groups                                         | spp.admin.group.groups |
| /spp group [addWorld/removeWorld] \<group\> \<world\> | Adds/Removes a world from a group                              | spp.admin.group.worlds |
| /spp group list                                       | Lists all the groups and what worlds are in them               | spp.admin.group.groups |
| /spp setOnTeleport \<teleportType\> \<bool\>          | Sets if the plugin should works on different type of teleports | spp.admin.onTeleport   |
| /spp command [add/remove] \<command\>                 | Adds/Removes a command to the whitelist                        | spp.admin.commands     |
| /spp command list                                     | Lists all the command in the whitelist                         | spp.admin.commands     |