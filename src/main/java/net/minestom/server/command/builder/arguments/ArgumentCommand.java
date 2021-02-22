package net.minestom.server.command.builder.arguments;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.CommandDispatcher;
import net.minestom.server.command.builder.CommandResult;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import org.jetbrains.annotations.NotNull;

public class ArgumentCommand extends Argument<CommandResult> {

    public static final int INVALID_COMMAND_ERROR = 1;

    public ArgumentCommand(@NotNull String id) {
        super(id, true, true);
    }

    @NotNull
    @Override
    public CommandResult parse(@NotNull String input) throws ArgumentSyntaxException {
        CommandDispatcher dispatcher = MinecraftServer.getCommandManager().getDispatcher();
        CommandResult result = dispatcher.parse(input);

        if (result.getType() != CommandResult.Type.SUCCESS)
            throw new ArgumentSyntaxException("Invalid command", input, INVALID_COMMAND_ERROR);

        return result;
    }

}
