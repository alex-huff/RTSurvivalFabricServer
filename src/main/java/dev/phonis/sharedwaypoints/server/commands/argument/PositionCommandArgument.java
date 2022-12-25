package dev.phonis.sharedwaypoints.server.commands.argument;

import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.PosArgument;

public
class PositionCommandArgument extends NoSuggestionCommandArgument<PosArgument>
{

    public
    PositionCommandArgument(String name)
    {
        super(name, BlockPosArgumentType.blockPos());
    }

}
