package dev.phonis.sharedwaypoints.server.commands.util;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ContextUtil
{

    public static void sendMessage(CommandContext<ServerCommandSource> context, String message)
    {
        context.getSource().sendMessage(Text.of(message));
    }

}
