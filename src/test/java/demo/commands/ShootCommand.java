package demo.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.type.projectile.EntityProjectile;

public class ShootCommand extends Command {

    public ShootCommand() {
        super("shoot");
        setCondition(this::condition);
        setDefaultExecutor(this::defaultExecutor);
        addSyntax(this::onShootCommand);
    }

    private boolean condition(CommandSender sender, String commandString) {
        if (!sender.isPlayer()) {
            sender.sendMessage("The command is only available for player");
            return false;
        }
        return true;
    }

    private void defaultExecutor(CommandSender sender, CommandContext context) {
        sender.sendMessage("Correct usage: shoot [default/spectral/colored]");
    }

    private void onTypeError(CommandSender sender, ArgumentSyntaxException exception) {
        sender.sendMessage("SYNTAX ERROR: '" + exception.getInput() + "' should be replaced by 'default', 'spectral' or 'colored'");
    }

    private void onShootCommand(CommandSender sender, CommandContext context) {
        Player player = (Player) sender;
        EntityProjectile projectile = new EntityProjectile(player, EntityType.ARROW);
        var pos = player.getPosition().clone().add(0D, player.getEyeHeight(), 0D);
        projectile.setInstance(player.getInstance(), pos);
        var dir = pos.getDirection().multiply(30D);
        pos = pos.clone().add(dir.getX(), dir.getY(), dir.getZ());
        projectile.shoot(pos, 1D, 0D);
    }
}