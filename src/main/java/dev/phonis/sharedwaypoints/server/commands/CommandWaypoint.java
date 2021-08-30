package dev.phonis.sharedwaypoints.server.commands;

import dev.phonis.sharedwaypoints.server.commands.internal.IntermediateCommand;

public class CommandWaypoint extends IntermediateCommand {

    public CommandWaypoint() {
        super("waypoint");
        this.addAlias("wp");
        this.addSubCommand(new CommandWaypointSet());
        this.addSubCommand(new CommandWaypointRemove());
        this.addSubCommand(new CommandWaypointUpdate());
        this.addSubCommand(new CommandWaypointList());
    }

}
