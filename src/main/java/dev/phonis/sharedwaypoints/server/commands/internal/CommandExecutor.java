package dev.phonis.sharedwaypoints.server.commands.internal;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.phonis.sharedwaypoints.server.commands.exception.CommandException;

@FunctionalInterface
public
interface CommandExecutor<S>
{

    void accept(CommandContext<S> commandContext) throws CommandException, CommandSyntaxException;

}
