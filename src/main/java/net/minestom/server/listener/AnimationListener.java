package net.minestom.server.listener;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerHandAnimationEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.client.play.ClientAnimationPacket;

public class AnimationListener {

    public static void animationListener(ClientAnimationPacket packet, Player player) {
        final ItemStack itemStack = player.getItemInHand();
        itemStack.onLeftClick(player);
        PlayerHandAnimationEvent handAnimationEvent = new PlayerHandAnimationEvent(player);
        player.callCancellableEvent(PlayerHandAnimationEvent.class, handAnimationEvent, player::swingMainHand);
    }

}
