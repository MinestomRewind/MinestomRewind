package net.minestom.server.listener;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.CommandProcessor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.client.play.ClientTabCompletePacket;
import net.minestom.server.network.packet.server.play.TabCompletePacket;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class TabCompleteListener {

    private static final CommandManager COMMAND_MANAGER = MinecraftServer.getCommandManager();

    public static void listener(ClientTabCompletePacket packet, Player player) {
        final String text = packet.text;

        final String[] split = packet.text.split(Pattern.quote(StringUtils.SPACE));

        final String commandName = split[0].replaceFirst(CommandManager.COMMAND_PREFIX, "");

        // Tab complete for CommandProcessor
        final CommandProcessor commandProcessor = COMMAND_MANAGER.getCommandProcessor(commandName);
        if (commandProcessor != null) {
            final String[] matches = commandProcessor.onWrite(player, text);
            if (matches != null && matches.length > 0) {
                sendTabCompletePacket(matches, player);
            }
        } else {
            // Tab complete for Command
            final Command command = COMMAND_MANAGER.getCommand(commandName);
            if (command != null) {
                final String[] matches = command.onDynamicWrite(player, text);
                if (matches != null && matches.length > 0) {
                    sendTabCompletePacket(matches, player);
                }
            }
        }


    }

    private static int findStart(String text, String[] split) {
        final boolean endSpace = text.endsWith(StringUtils.SPACE);
        int start;
        if (endSpace) {
            start = text.length();
        } else {
            final String lastArg = split[split.length - 1];
            start = text.lastIndexOf(lastArg);
        }
        return start;
    }

    private static void sendTabCompletePacket(String[] matches, Player player) {
        TabCompletePacket tabCompletePacket = new TabCompletePacket();
        tabCompletePacket.length = matches.length;
        tabCompletePacket.matches = matches;

        player.getPlayerConnection().sendPacket(tabCompletePacket);
    }


}
