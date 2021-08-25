package dev.phonis.sharedwaypoints.server.commands;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Collections;
import java.util.List;

public abstract class IntermediateCommand extends AbstractServerCommand {

    public IntermediateCommand(String name) {
        super(name);
    }

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Collections.emptyList();
    }

    @Override
    public void onCommand(CommandContext<ServerCommandSource> source) {
        Entity entity = source.getSource().getEntity();

        if (entity == null) return;

        entity.sendSystemMessage(this.getUsage(), AbstractServerCommand.systemUUID);
    }

}
