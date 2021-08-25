package dev.phonis.sharedwaypoints.server.commands;

import com.mojang.brigadier.context.CommandContext;
import dev.phonis.sharedwaypoints.server.commands.internal.AbstractServerCommand;
import dev.phonis.sharedwaypoints.server.commands.internal.IntegerCommandArgument;
import dev.phonis.sharedwaypoints.server.commands.internal.OptionalTripleServerCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

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
        source.getSource().getEntity().sendSystemMessage(Text.of("Called with no args"), AbstractServerCommand.systemUUID);
    }

    @Override
    protected void onOptionalCommand(CommandContext<ServerCommandSource> source, Integer a) {
        source.getSource().getEntity().sendSystemMessage(Text.of("Called with one args: " + a), AbstractServerCommand.systemUUID);
    }

    @Override
    protected void onOptionalCommand(CommandContext<ServerCommandSource> source, Integer a, Integer b) {
        source.getSource().getEntity().sendSystemMessage(Text.of("Called with two args: " + a + " " + b), AbstractServerCommand.systemUUID);
    }

    @Override
    protected void onOptionalCommand(CommandContext<ServerCommandSource> source, Integer a, Integer b, Integer c) {
        source.getSource().getEntity().sendSystemMessage(Text.of("Called with three args: " + a + " " + b + " " + c), AbstractServerCommand.systemUUID);
    }

}
