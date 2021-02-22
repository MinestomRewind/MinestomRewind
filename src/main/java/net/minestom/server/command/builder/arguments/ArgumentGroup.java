package net.minestom.server.command.builder.arguments;

import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.command.builder.parser.CommandParser;
import net.minestom.server.command.builder.parser.ValidSyntaxHolder;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArgumentGroup extends Argument<Arguments> {

    public static final int INVALID_ARGUMENTS_ERROR = 1;

    private final Argument<?>[] group;

    public ArgumentGroup(@NotNull String id, @NotNull Argument<?>... group) {
        super(id, true, false);
        this.group = group;
    }

    @NotNull
    @Override
    public Arguments parse(@NotNull String input) throws ArgumentSyntaxException {
        List<ValidSyntaxHolder> validSyntaxes = new ArrayList<>();
        CommandParser.parse(null, group, input.split(StringUtils.SPACE), validSyntaxes, null);

        Arguments arguments = new Arguments();
        CommandParser.findMostCorrectSyntax(validSyntaxes, arguments);
        if (validSyntaxes.isEmpty()) {
            throw new ArgumentSyntaxException("Invalid arguments", input, INVALID_ARGUMENTS_ERROR);
        }

        return arguments;
    }

}
