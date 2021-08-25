package dev.phonis.sharedwaypoints.server.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;

public class IntegerCommandArgument extends NoSuggestionCommandArgument<Integer> {

    public IntegerCommandArgument(String name, int min, int max) {
        super(name, IntegerArgumentType.integer(min, max));
    }

    public IntegerCommandArgument(String name) {
        this(name, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

}
