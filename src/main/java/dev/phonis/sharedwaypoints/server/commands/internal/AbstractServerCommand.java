package dev.phonis.sharedwaypoints.server.commands.internal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.phonis.sharedwaypoints.server.commands.exception.CommandException;
import dev.phonis.sharedwaypoints.server.commands.util.ContextUtil;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractServerCommand implements IServerCommand
{

    private final String               name;
    private final List<String>         aliases     = new LinkedList<>();
    private final List<IServerCommand> subCommands = new LinkedList<>();

    public AbstractServerCommand(String name)
    {
        this.name = name;
    }

    protected void addAlias(String alias)
    {
        this.aliases.add(alias);
    }

    protected void addSubCommand(IServerCommand subCommand)
    {
        this.subCommands.add(subCommand);
    }

    @Override
    public Collection<String> getAliases()
    {
        return this.aliases;
    }

    @Override
    public Collection<IServerCommand> getSubCommands()
    {
        return this.subCommands;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public int execute(CommandContext<ServerCommandSource> source)
    {
        return this.execute(source, this::onCommand);
    }

    protected int execute(CommandContext<ServerCommandSource> source, CommandExecutor<ServerCommandSource> executor)
    {
        try
        {
            executor.accept(source);
        }
        catch (CommandException | CommandSyntaxException e)
        {
            Entity entity = source.getSource().getEntity();

            if (entity != null)
                ContextUtil.sendMessage(source, e.getMessage());
        }

        return Command.SINGLE_SUCCESS;
    }

    public abstract void onCommand(CommandContext<ServerCommandSource> source)
        throws CommandException, CommandSyntaxException;

}
