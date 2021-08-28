package dev.phonis.sharedwaypoints.server.commands;

import com.mojang.brigadier.context.CommandContext;
import dev.phonis.sharedwaypoints.server.commands.argument.StringCommandArgument;
import dev.phonis.sharedwaypoints.server.commands.exception.CommandException;
import dev.phonis.sharedwaypoints.server.commands.internal.OptionalSingleServerCommand;
import dev.phonis.sharedwaypoints.server.waypoints.WaypointManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandWaypointSet extends OptionalSingleServerCommand<String> {

    public CommandWaypointSet() {
        super(
            "set",
            new StringCommandArgument("name")
        );
        this.addAliases("s");
    }

    @Override
    protected void onOptionalCommand(CommandContext<ServerCommandSource> source) throws CommandException {
        throw new CommandException("You must provide a waypoint name.");
    }

    @Override
    protected void onOptionalCommand(CommandContext<ServerCommandSource> source, String s) {
        WaypointManager.INSTANCE.addWaypoint(source, s);
    }

}
