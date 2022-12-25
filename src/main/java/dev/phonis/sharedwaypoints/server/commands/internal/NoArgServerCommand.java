package dev.phonis.sharedwaypoints.server.commands.internal;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.phonis.sharedwaypoints.server.commands.argument.CommandArgument;
import dev.phonis.sharedwaypoints.server.commands.exception.CommandException;
import net.minecraft.server.command.ServerCommandSource;

import java.util.LinkedList;
import java.util.List;

public abstract
class NoArgServerCommand extends AbstractServerCommand
{

    protected final List<CommandArgument<?>> arguments = new LinkedList<>();

    public
    NoArgServerCommand(String name)
    {
        super(name);
    }

    @Override
    public
    List<CommandArgument<?>> getArguments()
    {
        return arguments;
    }

    @Override
    public
    void onCommand(CommandContext<ServerCommandSource> source) throws CommandException, CommandSyntaxException
    {
        this.onOptionalCommand(source);
    }

    protected abstract
    void onOptionalCommand(CommandContext<ServerCommandSource> source) throws CommandException, CommandSyntaxException;

}
