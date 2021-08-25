package dev.phonis.sharedwaypoints.server.commands;

import com.mojang.brigadier.context.CommandContext;
import dev.phonis.sharedwaypoints.server.commands.argument.IntegerCommandArgument;
import dev.phonis.sharedwaypoints.server.commands.internal.OptionalTripleServerCommand;
import dev.phonis.sharedwaypoints.server.commands.util.ContextUtil;
import net.minecraft.server.command.ServerCommandSource;

public class CommandWaypointSet extends OptionalTripleServerCommand<Integer, Integer, Integer> {

    public CommandWaypointSet() {
        super(
            "set",
            new IntegerCommandArgument("x"),
            new IntegerCommandArgument("y"),
            new IntegerCommandArgument("z")
        );
        this.addAliases("s");
    }

    @Override
    protected void onOptionalCommand(CommandContext<ServerCommandSource> source) {
        ContextUtil.sendMessage(source, "Called with no args");
    }

    @Override
    protected void onOptionalCommand(CommandContext<ServerCommandSource> source, Integer a) {
        ContextUtil.sendMessage(source, "Called with one args: " + a);
    }

    @Override
    protected void onOptionalCommand(CommandContext<ServerCommandSource> source, Integer a, Integer b) {
        ContextUtil.sendMessage(source, "Called with two args: " + a + " " + b);
    }

    @Override
    protected void onOptionalCommand(CommandContext<ServerCommandSource> source, Integer a, Integer b, Integer c) {
        ContextUtil.sendMessage(source, "Called with three args: " + a + " " + b + " " + c);
    }

}
