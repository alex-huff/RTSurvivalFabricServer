package dev.phonis.sharedwaypoints.server.commands.argument;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.server.command.ServerCommandSource;

public abstract class CommandArgument<T> implements SuggestionProvider<ServerCommandSource> {

    public final String name;
    public final ArgumentType<T> type;
    public Command<ServerCommandSource> executor; // set by command

    public CommandArgument(String name, ArgumentType<T> type) {
        this.name = name;
        this.type = type;
    }

    public CommandArgument<T> setExecutor(Command<ServerCommandSource> executor) {
        this.executor = executor;

        return this;
    }

}
