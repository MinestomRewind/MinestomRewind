package demo.commands;

import com.google.gson.JsonParseException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minestom.server.MinecraftServer;
import net.minestom.server.chat.Adventure;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;

import java.time.Duration;

public class TitleCommand extends Command {
    private final Argument<String> contentArgument = ArgumentType.String("content");

    public TitleCommand() {
        super("title");
        setDefaultExecutor((source, args) -> {
            source.sendMessage("Unknown syntax (note: title must be quoted)");
        });

        addSyntax(this::handleTitle, contentArgument);
    }

    private void handleTitle(CommandSender source, Arguments args) {
        if (!source.isPlayer()) {
            source.sendMessage("Only players can run this command!");
            return;
        }

        Player player = source.asPlayer();
        String titleContent = args.get(contentArgument);

        Component title;
        try {
            title = Adventure.COMPONENT_SERIALIZER.deserialize(titleContent);
        } catch (JsonParseException | IllegalStateException ignored) {
            title = Component.text(titleContent);
        }

        Title.Times times = Title.Times.of(Duration.ofMillis(10 * MinecraftServer.TICK_MS), Duration.ofMillis(10 * MinecraftServer.TICK_MS), Duration.ofMillis(10 * MinecraftServer.TICK_MS));
        player.showTitle(Title.title(title, Component.text(""), times));
    }
}
