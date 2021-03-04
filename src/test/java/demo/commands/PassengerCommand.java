package demo.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Arguments;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.time.TimeUnit;

public class PassengerCommand extends Command {

    public PassengerCommand() {
        super("passenger");

        setCondition(this::condition);

        setDefaultExecutor(this::onPassengerCommand);
    }

    private boolean condition(CommandSender sender, String commandString) {
        if (!sender.isPlayer()) {
            sender.sendMessage("The command is only available for player");
            return false;
        }
        return true;
    }

    private void onPassengerCommand(CommandSender sender, Arguments args) {
        final Player player = (Player) sender;

        var spider = new EntityCreature(EntityType.SPIDER);
        spider.setInstance(player.getInstance(), player.getPosition());

        var skeleton = new EntityCreature(EntityType.SKELETON);
        skeleton.setInstance(player.getInstance(), player.getPosition());

        spider.setPassenger(skeleton);

        MinecraftServer.getSchedulerManager().buildTask(() -> spider.setPassenger(null)).delay(10, TimeUnit.SECOND).schedule();
    }

}