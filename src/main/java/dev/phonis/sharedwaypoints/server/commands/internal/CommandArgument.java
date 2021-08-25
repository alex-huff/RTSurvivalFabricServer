package dev.phonis.sharedwaypoints.server.commands.internal;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.server.command.ServerCommandSource;

public abstract class CommandArgument<T> implements SuggestionProvider<ServerCommandSource> {

    public final String name;
    public final ArgumentType<T> type;

    public CommandArgument(String name, ArgumentType<T> type) {
        this.name = name;
        this.type = type;
    }

}
