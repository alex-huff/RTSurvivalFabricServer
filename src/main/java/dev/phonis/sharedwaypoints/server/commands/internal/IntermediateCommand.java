package dev.phonis.sharedwaypoints.server.commands.internal;

import com.mojang.brigadier.context.CommandContext;
import dev.phonis.sharedwaypoints.server.commands.argument.CommandArgument;
import dev.phonis.sharedwaypoints.server.commands.util.ContextUtil;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Collections;
import java.util.List;

public abstract class IntermediateCommand extends AbstractServerCommand
{

    public IntermediateCommand(String name)
    {
        super(name);
    }

    @Override
    public List<CommandArgument<?>> getArguments()
    {
        return Collections.emptyList();
    }

    @Override
    public void onCommand(CommandContext<ServerCommandSource> source)
    {
        ContextUtil.sendMessage(source, this.getUsage());
    }

}
