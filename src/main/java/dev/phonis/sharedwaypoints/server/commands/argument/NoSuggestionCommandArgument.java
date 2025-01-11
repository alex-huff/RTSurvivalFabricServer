package dev.phonis.sharedwaypoints.server.commands.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.concurrent.CompletableFuture;

public class NoSuggestionCommandArgument<T> extends CommandArgument<T>
{

    public NoSuggestionCommandArgument(String name, ArgumentType<T> type)
    {
        super(name, type);
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context,
                                                         SuggestionsBuilder builder) throws CommandSyntaxException
    {
        return Suggestions.empty();
    }

}
