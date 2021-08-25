package dev.phonis.sharedwaypoints.server.commands;

import dev.phonis.sharedwaypoints.server.commands.internal.IntermediateCommand;

public class CommandWaypoint extends IntermediateCommand {

    public CommandWaypoint() {
        super("waypoint");
        this.addAliases("wp");
        this.addSubCommand(new CommandWaypointSet());
    }

}
