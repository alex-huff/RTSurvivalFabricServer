package dev.phonis.sharedwaypoints.server.commands.argument;

import com.mojang.brigadier.arguments.StringArgumentType;

public class StringCommandArgument extends NoSuggestionCommandArgument<String>
{

    public StringCommandArgument(String name)
    {
        super(name, StringArgumentType.word());
    }

}
