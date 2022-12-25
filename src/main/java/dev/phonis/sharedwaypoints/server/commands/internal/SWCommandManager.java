package dev.phonis.sharedwaypoints.server.commands.internal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.phonis.sharedwaypoints.server.commands.argument.CommandArgument;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public
class SWCommandManager
{

    private static final List<IServerCommand> commands = new ArrayList<>();

    public static
    void addCommand(IServerCommand command)
    {
        SWCommandManager.commands.add(command);
    }

    public static
    void register()
    {
        CommandRegistrationCallback.EVENT.register(SWCommandManager::registerCommands);
    }

    public static
    void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
                          CommandManager.RegistrationEnvironment env)
    {
        if (env.dedicated)
        {
            for (IServerCommand command : SWCommandManager.commands)
            {
                // register command and its aliases
                SWCommandManager.buildCommand(command).forEach(dispatcher::register);
            }
        }
    }

    private static
    List<LiteralArgumentBuilder<ServerCommandSource>> buildCommand(IServerCommand command)
    {
        LiteralArgumentBuilder<ServerCommandSource> rootCommand = LiteralArgumentBuilder.literal(command.getName());
        List<LiteralArgumentBuilder<ServerCommandSource>> redirects = new LinkedList<>();

        // recurse down the tree and link all nodes at next depth including aliases which have already been redirected
        for (IServerCommand subCommand : command.getSubCommands())
        {
            SWCommandManager.buildCommand(subCommand).forEach(rootCommand::then);
        }

        ArgumentBuilder<ServerCommandSource, ?> previous  = null;
        List<CommandArgument<?>>                arguments = command.getArguments();

        rootCommand.executes(command::execute);

        // needs to be build backwards since ArgumentBuilders' then is depended on the parameter to be already build
        // which happens by nature of the code structure when using the library as intended
        for (int i = arguments.size() - 1; i >= 0; i--)
        {
            CommandArgument<?> commandArgument = arguments.get(i);
            RequiredArgumentBuilder<ServerCommandSource, ?> argumentBuilder = RequiredArgumentBuilder.argument(
                commandArgument.name, commandArgument.type);

            argumentBuilder.executes(commandArgument.getExecutor());
            argumentBuilder.suggests(commandArgument);

            if (previous != null)
            {
                argumentBuilder.then(previous);
            }

            previous = argumentBuilder;
        }

        if (previous != null)
        {
            rootCommand.then(previous);
        }

        redirects.add(rootCommand);

        LiteralCommandNode<ServerCommandSource> commandNode = rootCommand.build();

        for (String alias : command.getAliases())
        {
            LiteralArgumentBuilder<ServerCommandSource> aliasCommand = LiteralArgumentBuilder.literal(alias);

            // Seems as if redirect is only relevant in parsing down the tree, and not redirecting execution
            // That means all aliases need to also set their executor
            aliasCommand.executes(command::execute);
            aliasCommand.redirect(commandNode);
            redirects.add(aliasCommand);
        }

        return redirects;
    }

}
