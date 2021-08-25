package dev.phonis.sharedwaypoints.server.commands;

public class CommandException extends Exception {

    private static final String prefix = "Command usage error ➤ ";

    public CommandException(String error) {
        super(CommandException.prefix + error);
    }

}
