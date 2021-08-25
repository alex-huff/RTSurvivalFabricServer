package dev.phonis.sharedwaypoints.server.commands.internal;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.phonis.sharedwaypoints.server.commands.util.Pair;
import net.minecraft.server.command.ServerCommandSource;

public abstract class OptionalPairServerCommand<A, B> extends OptionalSingleServerCommand<A> {

    private final CommandArgument<B> b;

    public OptionalPairServerCommand(String name, CommandArgument<A> a, CommandArgument<B> b) {
        super(name, a);

        this.b = b;

        this.arguments.add(this.b);
    }

    protected boolean constructArgs(CommandContext<ServerCommandSource> source, Pair<A, B> pair) throws CommandException, CommandSyntaxException {
        if (!super.constructArgs(source, pair)) return false;

        try {
            B bArg = (B) source.getArgument(this.b.name, Object.class);

            pair.setB(bArg);

            return true;
        } catch (IllegalArgumentException ignored) {
            this.onOptionalCommand(source, pair.getA());
        }

        return false;
    }

    @Override
    public void onCommand(CommandContext<ServerCommandSource> source) throws CommandException, CommandSyntaxException {
        Pair<A, B> pair = new Pair<>();

        if (this.constructArgs(source, pair)) this.onOptionalCommand(source, pair.getA(), pair.getB());
    }

    protected abstract void onOptionalCommand(CommandContext<ServerCommandSource> source, A a, B b) throws CommandException, CommandSyntaxException;

}
