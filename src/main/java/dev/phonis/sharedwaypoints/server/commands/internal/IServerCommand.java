package dev.phonis.sharedwaypoints.server.commands.internal;

import com.mojang.brigadier.context.CommandContext;
import dev.phonis.sharedwaypoints.server.commands.argument.CommandArgument;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;

import java.util.Collection;
import java.util.List;

public interface IServerCommand {

    Collection<IServerCommand> getSubCommands();

    String getName();

    Collection<String> getAliases();

    List<CommandArgument<?>> getArguments();

    int execute(CommandContext<ServerCommandSource> source);

    default String getUsage() {
        return this.getUsage(0);
    }

    private void generateHint(StringBuilder builder) {
        this.getArguments().forEach(commandArgument -> builder.append("<[").append(commandArgument.name).append("]> "));
    }

    private String getUsage(int depth) {
        StringBuilder message = new StringBuilder();

        if (depth > 0)
            message.append('\n').append("   ".repeat(depth));

        message.append(Formatting.RESET).append(Formatting.AQUA);
        message.append(this.getName()).append(' ').append(Formatting.GRAY);
        this.generateHint(message);

        for (IServerCommand subCommand : this.getSubCommands()) {
            message.append(subCommand.getUsage(depth + 1));
        }

        return message.toString();
    }

}
