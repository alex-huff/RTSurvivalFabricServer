package dev.phonis.sharedwaypoints.server.commands.argument;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.phonis.sharedwaypoints.server.waypoints.WaypointManager;

import java.util.concurrent.CompletableFuture;

public
class WaypointCommandArgument extends CommandArgument<String>
{

    public
    WaypointCommandArgument(String name)
    {
        super(name, StringArgumentType.word());
    }

    @Override
    public
    CompletableFuture<Suggestions> getSuggestions(CommandContext context, SuggestionsBuilder builder)
        throws CommandSyntaxException
    {
        WaypointManager.INSTANCE.forEachWaypoint(waypoint ->
        {
            if (waypoint.getName().startsWith(builder.getRemaining()))
            {
                builder.suggest(waypoint.getName());
            }
        });

        return builder.buildFuture();
    }

}
