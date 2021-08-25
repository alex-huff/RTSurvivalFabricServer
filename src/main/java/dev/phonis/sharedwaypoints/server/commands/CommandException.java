package dev.phonis.sharedwaypoints.server.commands;

import net.minecraft.util.Formatting;

public class CommandException extends Exception {

    private static final String prefix = Formatting.RED + "Command usage error " + Formatting.GRAY + "➤ " + Formatting.WHITE;

    public CommandException(String error) {
        super(CommandException.prefix + error);
    }

}
