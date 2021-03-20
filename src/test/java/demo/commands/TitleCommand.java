package demo.commands;

import com.google.gson.JsonParseException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minestom.server.MinecraftServer;
import net.minestom.server.chat.Adventure;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;

import java.time.Duration;

public class TitleCommand extends Command {
    public TitleCommand() {
        super("title");
        setDefaultExecutor((source, args) -> {
            source.sendMessage("Unknown syntax (note: title must be quoted)");
        });

        var content = ArgumentType.String("content");

        addSyntax(this::handleTitle, content);
    }

    private void handleTitle(CommandSender source, CommandContext context) {
        if (!source.isPlayer()) {
            source.sendMessage("Only players can run this command!");
            return;
        }

        Player player = source.asPlayer();
        String titleContent = context.get("content");

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
