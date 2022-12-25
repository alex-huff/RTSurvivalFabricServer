package dev.phonis.sharedwaypoints.server.commands.util;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public
class ContextUtil
{

    public static
    void sendMessage(CommandContext<ServerCommandSource> source, String message)
    {
        Entity entity = source.getSource().getEntity();

        if (entity == null)
        {
            return;
        }

        entity.sendMessage(Text.of(message));
    }

}
