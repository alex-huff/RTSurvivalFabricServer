package dev.phonis.sharedwaypoints.server.commands;

import com.mojang.brigadier.context.CommandContext;
import de.bluecolored.bluemap.api.BlueMapAPI;
import dev.phonis.sharedwaypoints.server.bluemap.BlueMapHelper;
import dev.phonis.sharedwaypoints.server.commands.argument.WaypointCommandArgument;
import dev.phonis.sharedwaypoints.server.commands.exception.CommandException;
import dev.phonis.sharedwaypoints.server.commands.internal.OptionalSingleServerCommand;
import dev.phonis.sharedwaypoints.server.commands.util.ContextUtil;
import dev.phonis.sharedwaypoints.server.networking.SWNetworkManager;
import dev.phonis.sharedwaypoints.server.networking.protocol.action.SWWaypointRemoveAction;
import dev.phonis.sharedwaypoints.server.waypoints.Waypoint;
import dev.phonis.sharedwaypoints.server.waypoints.WaypointManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;

public
class CommandWaypointRemove extends OptionalSingleServerCommand<String>
{

    public
    CommandWaypointRemove()
    {
        super("remove", new WaypointCommandArgument("waypoint"));
        this.addAlias("r");
    }

    @Override
    protected
    void onOptionalCommand(CommandContext<ServerCommandSource> source) throws CommandException
    {
        throw new CommandException("You must provide a waypoint name.");
    }

    @Override
    protected
    void onOptionalCommand(CommandContext<ServerCommandSource> source, String s) throws CommandException
    {
        Waypoint waypoint = WaypointManager.INSTANCE.removeWaypoint(s);

        if (waypoint == null)
        {
            throw new CommandException("Invalid waypoint for removal.");
        }

        ContextUtil.sendMessage(source,
            Formatting.WHITE + "Waypoint '" + Formatting.AQUA + waypoint.getName() + Formatting.WHITE + "' removed.");
        BlueMapAPI.getInstance().flatMap(api -> api.getMap(BlueMapHelper.getMapIDFromWorldID(waypoint.getWorld())))
            .ifPresent((map) -> map.getMarkerSets().get(BlueMapHelper.getMarkerSetIDFromWorldID(waypoint.getWorld()))
                .remove(waypoint.getName()));
        SWNetworkManager.INSTANCE.sendToSubscribed(source, new SWWaypointRemoveAction(waypoint.getName()));
    }

}
