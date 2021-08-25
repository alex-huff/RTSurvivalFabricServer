package dev.phonis.sharedwaypoints.server.commands.util;

import com.mojang.brigadier.context.CommandContext;
import dev.phonis.sharedwaypoints.server.commands.internal.AbstractServerCommand;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.UUID;

public class ContextUtil {

    private static final UUID systemUUID = new UUID(0, 0);

    public static void sendMessage(CommandContext<ServerCommandSource> source, String message) {
        Entity entity = source.getSource().getEntity();

        if (entity == null) return;

        entity.sendSystemMessage(Text.of(message), ContextUtil.systemUUID);
    }

}
