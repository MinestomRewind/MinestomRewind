package demo.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.SkullMeta;

public class HeadCommand extends Command {
    public HeadCommand() {
        super("head");
        setCondition(this::condition);
        setDefaultExecutor(this::onSelfHeadCommand);
        var usernameArg = ArgumentType.Word("username");
        addSyntax(this::onHeadCommand, usernameArg);
    }

    private boolean condition(CommandSender sender, String commandString) {
        if (!sender.isPlayer()) {
            sender.sendMessage("The command is only available for player");
            return false;
        }
        return true;
    }

    private void defaultExecutor(CommandSender sender, Arguments args) {
        sender.sendMessage("Correct usage: head [username]");
    }

    private void onSelfHeadCommand(CommandSender sender, Arguments args) {
        var player = (Player) sender;

        var stack = new ItemStack(Material.SKULL, (byte) 1, 3);
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        if (!meta.setOwningPlayer(player)) {
            player.sendMessage(Component.text("Missing skin", NamedTextColor.RED));
            return;
        }

        player.getInventory().addItemStack(stack);
    }

    private void onHeadCommand(CommandSender sender, Arguments args) {
        var player = (Player) sender;

        String username = args.get("username");

        var stack = new ItemStack(Material.SKULL, (byte) 1, 3);
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        meta.setSkullOwner(username);
        meta.setPlayerSkin(PlayerSkin.fromUsername(username));

        player.getInventory().addItemStack(stack);
    }
}
