package dev.phonis.sharedwaypoints.server.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.phonis.sharedwaypoints.server.commands.argument.PositionCommandArgument;
import dev.phonis.sharedwaypoints.server.commands.argument.WaypointCommandArgument;
import dev.phonis.sharedwaypoints.server.commands.exception.CommandException;
import dev.phonis.sharedwaypoints.server.commands.internal.OptionalPairServerCommand;
import dev.phonis.sharedwaypoints.server.commands.util.ContextUtil;
import dev.phonis.sharedwaypoints.server.networking.SWNetworkManager;
import dev.phonis.sharedwaypoints.server.networking.protocol.action.SWWaypointUpdateAction;
import dev.phonis.sharedwaypoints.server.waypoints.Waypoint;
import dev.phonis.sharedwaypoints.server.waypoints.WaypointManager;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class CommandWaypointUpdate extends OptionalPairServerCommand<String, PosArgument>
{

    public CommandWaypointUpdate()
    {
        super(
            "update",
            new WaypointCommandArgument("waypoint"),
            new PositionCommandArgument("position")
        );
        this.addAlias("u");
    }

    @Override
    protected void onOptionalCommand(CommandContext<ServerCommandSource> source) throws CommandException
    {
        throw new CommandException("You must provide a waypoint name.");
    }

    @Override
    protected void onOptionalCommand(CommandContext<ServerCommandSource> source, String s, PosArgument posArgument)
        throws CommandException, CommandSyntaxException
    {
        this.onOptionalCommand(
            source, s, posArgument.toAbsolutePos(source.getSource()),
            source.getSource().getPlayer().getWorld().toServerWorld()
        );
    }

    @Override
    protected void onOptionalCommand(CommandContext<ServerCommandSource> source, String s)
        throws CommandException, CommandSyntaxException
    {
        ServerPlayerEntity player = source.getSource().getPlayer();

        this.onOptionalCommand(source, s, player.getPos(), player.getWorld().toServerWorld());
    }

    private void onOptionalCommand(
        CommandContext<ServerCommandSource> source, String s, Vec3d position, ServerWorld world
    ) throws CommandException
    {
        if (!WaypointManager.INSTANCE.hasWaypoint(s))
        {
            throw new CommandException("Invalid waypoint to update.");
        }

        Waypoint waypoint = WaypointManager.INSTANCE.updateWaypoint(s, position, world);

        ContextUtil.sendMessage(
            source,
            Formatting.WHITE +
            "Position of '" + Formatting.AQUA + waypoint.getName() + Formatting.WHITE + "' ➤ " +
            Formatting.AQUA + waypoint.getWorld() + Formatting.WHITE + " ➤ " + Formatting.GRAY +
            (int) waypoint.getX() + ", " +
            (int) waypoint.getY() + ", " +
            (int) waypoint.getZ()
        );
        SWNetworkManager.INSTANCE.sendToSubscribed(source, new SWWaypointUpdateAction(waypoint));
    }

}
