package dev.phonis.sharedwaypoints.server.commands.impl;

import dev.phonis.sharedwaypoints.server.commands.IntermediateCommand;

public class CommandWaypoint extends IntermediateCommand {

    public CommandWaypoint() {
        super("waypoint");
        this.addAliases("wp");
        this.addSubCommand(new CommandWaypointSet());
    }

}
