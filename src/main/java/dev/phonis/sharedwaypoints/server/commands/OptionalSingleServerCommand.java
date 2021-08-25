package dev.phonis.sharedwaypoints.server.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.phonis.sharedwaypoints.server.commands.util.Single;
import net.minecraft.server.command.ServerCommandSource;

import java.util.LinkedList;
import java.util.List;

public abstract class OptionalSingleServerCommand<A> extends AbstractServerCommand {

    private final CommandArgument<A> a;

    public OptionalSingleServerCommand(String name, CommandArgument<A> a) {
        super(name);

        this.a = a;
    }

    @Override
    public List<CommandArgument<?>> getArguments() {
        List<CommandArgument<?>> arguments = new LinkedList<>();

        arguments.add(this.a);

        return arguments;
    }

    protected boolean constructArgs(CommandContext<ServerCommandSource> source, Single<A> single) throws CommandException, CommandSyntaxException {
        try {
            A aArg = (A) source.getArgument(this.a.name, Object.class);

            single.setA(aArg);

            return true;
        } catch (IllegalArgumentException ignored) {
            this.onOptionalCommand(source);
        }

        return false;
    }

    @Override
    public void onCommand(CommandContext<ServerCommandSource> source) throws CommandException, CommandSyntaxException {
        Single<A> single = new Single<>();

        if (this.constructArgs(source, single)) this.onOptionalCommand(source, single.getA());
    }

    protected abstract void onOptionalCommand(CommandContext<ServerCommandSource> source) throws CommandException, CommandSyntaxException;
    protected abstract void onOptionalCommand(CommandContext<ServerCommandSource> source, A a) throws CommandException, CommandSyntaxException;

}
