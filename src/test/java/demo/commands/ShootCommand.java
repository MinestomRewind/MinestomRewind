package demo.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.type.projectile.EntityProjectile;

public class ShootCommand extends Command {

    public ShootCommand() {
        super("shoot");
        setCondition(this::condition);
        setDefaultExecutor(this::onShootCommand);
    }

    private boolean condition(CommandSender sender, String commandString) {
        if (!sender.isPlayer()) {
            sender.sendMessage("The command is only available for player");
            return false;
        }
        return true;
    }

    private void onShootCommand(CommandSender sender, Arguments args) {
        Player     player = (Player) sender;
        var        pos    = player.getPosition().clone().add(0D, player.getEyeHeight(), 0D);
        EntityProjectile projectile = new EntityProjectile(player, EntityType.ARROW);
        projectile.setInstance(player.getInstance(), pos);
        var dir = pos.getDirection().multiply(30D);
        pos = pos.clone().add(dir.getX(), dir.getY(), dir.getZ());
        projectile.shoot(pos, 1D, 0D);
    }
}
