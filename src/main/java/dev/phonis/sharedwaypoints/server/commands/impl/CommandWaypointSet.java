package dev.phonis.sharedwaypoints.server.commands.impl;

import com.mojang.brigadier.context.CommandContext;
import dev.phonis.sharedwaypoints.server.commands.AbstractServerCommand;
import dev.phonis.sharedwaypoints.server.commands.CommandArgument;
import dev.phonis.sharedwaypoints.server.commands.IntegerCommandArgument;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class CommandWaypointSet extends AbstractServerCommand {

    private static final List<CommandArgument<?>> arguments = new ArrayList<>();

    static {
        arguments.add(new IntegerCommandArgument("x"));
        arguments.add(new IntegerCommandArgument("y"));
        arguments.add(new IntegerCommandArgument("z"));
    }

    public CommandWaypointSet() {
        super("set");
        this.addAliases("s");
    }

    @Override
    public void onCommand(CommandContext<ServerCommandSource> source) {
        int x = 0;
        int y = 0;
        int z = 0;

        try {
            x = source.getArgument("x", Integer.class);
            y = source.getArgument("y", Integer.class);
            z = source.getArgument("z", Integer.class);
        } catch (IllegalArgumentException ignored) { }

        source.getSource().getEntity().sendSystemMessage(Text.of("x:" + x), AbstractServerCommand.systemUUID);
        source.getSource().getEntity().sendSystemMessage(Text.of("y:" + y), AbstractServerCommand.systemUUID);
        source.getSource().getEntity().sendSystemMessage(Text.of("z:" + z), AbstractServerCommand.systemUUID);
    }

    @Override
    public List<CommandArgument<?>> getArguments() {
        return CommandWaypointSet.arguments;
    }

}
