package dev.phonis.sharedwaypoints.server.commands.internal;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.phonis.sharedwaypoints.server.commands.util.Triple;
import net.minecraft.server.command.ServerCommandSource;

import java.util.List;

public abstract class OptionalTripleServerCommand<A, B, C> extends OptionalPairServerCommand<A, B> {

    private final CommandArgument<C> c;

    public OptionalTripleServerCommand(String name, CommandArgument<A> a, CommandArgument<B> b, CommandArgument<C> c) {
        super(name, a, b);

        this.c = c;
    }

    @Override
    public List<CommandArgument<?>> getArguments() {
        List<CommandArgument<?>> arguments = super.getArguments();

        arguments.add(this.c);

        return arguments;
    }

    protected boolean constructArgs(CommandContext<ServerCommandSource> source, Triple<A, B, C> triple) throws CommandException, CommandSyntaxException {
        if (!super.constructArgs(source, triple)) return false;

        try {
            C cArg = (C) source.getArgument(this.c.name, Object.class);

            triple.setC(cArg);

            return true;
        } catch (IllegalArgumentException ignored) {
            this.onOptionalCommand(source, triple.getA(), triple.getB());
        }

        return false;
    }

    @Override
    public void onCommand(CommandContext<ServerCommandSource> source) throws CommandException, CommandSyntaxException {
        Triple<A, B, C> triple = new Triple<>();

        if (this.constructArgs(source, triple)) this.onOptionalCommand(source, triple.getA(), triple.getB(), triple.getC());
    }

    protected abstract void onOptionalCommand(CommandContext<ServerCommandSource> source, A a, B b, C c) throws CommandException, CommandSyntaxException;

}
